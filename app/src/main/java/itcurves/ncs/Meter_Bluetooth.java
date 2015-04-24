package itcurves.ncs;

import itcurves.ncs.taximeter.messages.ByteArray;
import itcurves.ncs.taximeter.messages.CreditCardDataMessage;
import itcurves.ncs.taximeter.messages.CustomMessage;
import itcurves.ncs.taximeter.messages.InvalidMeterMessageException;
import itcurves.ncs.taximeter.messages.MessageId;
import itcurves.ncs.taximeter.messages.MeterBusyNotBusy;
import itcurves.ncs.taximeter.messages.MeterMessage;
import itcurves.ncs.taximeter.messages.MeterMessageReader;
import itcurves.ncs.taximeter.messages.MeterStateChangeMessage;
import itcurves.ncs.taximeter.messages.MeterTripData;
import itcurves.ncs.taximeter.messages.PrinterMessage;
import itcurves.ncs.taximeter.messages.VeriFonePaymentData;
import itcurves.ncs.TaxiPlexer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/*
 * |-------------------| | BlueTooth Connection Class |
 * |--------------------------------------------------|
 */

public class Meter_Bluetooth extends Thread {
	private final BluetoothSocket mmSocket;
	private static BluetoothDevice mmDevice = null;
	private InputStream mmInStream;
	private OutputStream mmOutStream;
	private String address;
	public static boolean isreceive = true;
	public static boolean isVerifone = false;
	public static long lastVerifoneTripMeterTripDataTime = 0;
	private static boolean isConnectionAlive_exception = false;
	public static boolean flaggerStartedFromMeter_Verifone = false; // Sometime VF device is not sending fare information due to dis-connectivity
	/*---------------------------------------------------------------connectToBluetooth-------------------------------------------------------------------*/
	public Meter_Bluetooth(BluetoothDevice device) {

		this.setName("Meter_Bluetooth");
		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final
		BluetoothSocket tmp = null;
		mmDevice = device;

		// Get a BluetoothSocket to connect with the given BluetoothDevice
		try {
			// mmDevice.getBluetoothClass() MY_UUID is the app's UUID string, also used by the server code
			tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		} catch (IOException e) {
			try {
				Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
				tmp = (BluetoothSocket) m.invoke(mmDevice, 1);
			} catch (NoSuchMethodException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in Meter_Bluetooth in meter_bluetooth][Meter_Bluetooth][" + e.getLocalizedMessage() + "] NoSuchMethodException");
			} catch (IllegalArgumentException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in Meter_Bluetooth in meter_bluetooth][Meter_Bluetooth][" + e.getLocalizedMessage() + "] IllegalArgumentException");
			} catch (IllegalAccessException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in Meter_Bluetooth in meter_bluetooth][Meter_Bluetooth][" + e.getLocalizedMessage() + "] IllegalAccessException");
			} catch (InvocationTargetException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in Meter_Bluetooth in meter_bluetooth][Meter_Bluetooth][" + e.getLocalizedMessage() + "] InvocationTargetException");
			} catch (Exception e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in Meter_Bluetooth in meter_bluetooth][Meter_Bluetooth][" + e.getLocalizedMessage() + "]");
			}
		}
		mmSocket = tmp;
	}

	/*---------------------------------------------------------------connect-------------------------------------------------------------------*/
	public boolean connect() {
		// Cancel discovery because it will slow down the connection
		try {
			BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
			isreceive = true;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connect in meter_bluetooth][connect][" + e.getLocalizedMessage() + "]");
		}
		int connectTimes = 0;

		try {
			// if (!AVL_Service.pref.getBoolean("VeriFoneDevice", false)) {
			// if (mmDevice.getBondState() == BluetoothDevice.BOND_NONE)
			// pairDevice(mmDevice);
			//
			// while ((mmDevice.getBondState() == BluetoothDevice.BOND_NONE || mmDevice.getBondState() == BluetoothDevice.BOND_BONDING) && connectTimes <= 10)
			// synchronized (currentThread()) {
			// currentThread().wait(3000);
			// }
			// }

			try {
				if (mmDevice.getBondState() == BluetoothDevice.BOND_NONE) {
					pairDevice();
					TaxiPlexer.pairingrequest = true;
					return true;
				} else {
					mmSocket.connect();
					mmInStream = mmSocket.getInputStream();
					mmOutStream = mmSocket.getOutputStream();
					address = mmDevice.getAddress();
					mmInStream.available();
					TaxiPlexer.countForUnpair = 0;

						unlockMeter();

					TaxiPlexer.pairingrequest = false;
					return true;
				}

			} catch (IOException ioe) {
				connectTimes++;
			}

			if (AVL_Service.pref.getBoolean("VeriFoneDevice", false)) {
				TaxiPlexer.countForUnpair++;
				if (TaxiPlexer.countForUnpair >= 2) {
					TaxiPlexer.countForUnpair = 0;
					if (!AVL_Service.pref.getString("MeterAddress", "").equalsIgnoreCase(""))
						TaxiPlexer.VMeterAddress = AVL_Service.pref.getString("MeterAddress", "");
					AVL_Service.pref.edit().putString("MeterAddress", "").commit();

					unpairDevice(mmDevice);
					// try {
					// Method method = mmDevice.getClass().getMethod("removeBond", (Class[]) null);
					// method.invoke(mmDevice, (Object[]) null);
					//
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	/*---------------------------------------------------------------run----------------------------------------------------------------------------------*/
	@Override
	public void run() {

		byte[] bytes;
		MeterMessageReader reader = null;
		try {
			currentThread().setName("Taxi_Meter");
			reader = new MeterMessageReader(mmInStream);
		} catch (Exception e) {
			LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "]");
		}

		// Keep listening to the InputStream until an exception occurs
		while (isreceive) {
			try {
				// Read from the InputStream
				bytes = reader.getMessage();
				if (bytes != null) {
					Log.w(getClass().getSimpleName(), "Bytes from Meter: " + ByteArray.byteArrayToHexString(bytes));
					Log.w(getClass().getSimpleName(), "Bytes from Meter: " + new String(bytes, "utf-8"));
					// byte[] msgBytes = new byte[bytes.length];
					// System.arraycopy(bytes, 0, msgBytes, 0, bytes.length);

					if (bytes[1] != MessageId.ACKNOWLEDGE_COMMAND_VALUE) {
						MeterMessage meterMessage = null;

						if (bytes[1] == MessageId.CREDIT_CARD_DATA_VALUE) {
							// Get the Trip Data Details
							meterMessage = new CreditCardDataMessage(bytes);
							Log.w(getClass().getSimpleName(), "Parsed Credit Card Message: " + meterMessage.toString());
						} else if (bytes[1] == MessageId.METER_ON_OFF_STATE_CHANGE_VALUE) {
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run]" + "Meter_Bluetooth: MeterOnOffState : 1 " + str);
							meterMessage = new MeterStateChangeMessage(bytes);
							if (Character.toString(((MeterStateChangeMessage) meterMessage).getState()).equalsIgnoreCase("1"))
								// if (isVerifone)
								// mmOutStream.write(MessageId.ACKNOWLEDGEMENT);
								LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: MeterOnOffState : 2 " + str);

						} else if (bytes[1] == MessageId.REPORT_METER_TRIP_DATA_VALUE) {
							meterMessage = new MeterTripData(bytes);
						} else if (bytes[1] == MessageId.METER_BUSY_NOT_BUSY_VALUE) {
							meterMessage = new MeterBusyNotBusy(bytes);
						} else if (bytes[1] == MessageId.SET_EXTRAS_AMOUNT_VALUE) { // CMD:'p' for Verifone
							mmOutStream.write(MessageId.UNSUPPORTED_COMMAND);
						} else if (bytes[1] == MessageId.VERIFONE_CASH) {
							// if (isVerifone)
							// mmOutStream.write(MessageId.ACKNOWLEDGEMENT);
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CASH : 1 " + str);
							meterMessage = new VeriFonePaymentData(bytes, "3");
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CASH : 2 " + str);

						} else if (bytes[1] == MessageId.VERIFONE_CREDIT_CARD) {
							// if (isVerifone)
							// mmOutStream.write(MessageId.ACKNOWLEDGEMENT);
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CREDIT_CARD : 1 " + str);
							meterMessage = new VeriFonePaymentData(bytes, "4");
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CREDIT_CARD : 2 " + str);
						} else if (bytes[1] == MessageId.VERIFONE_CMD1_ACK) {
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD1_ACK : 1 " + str);
							meterMessage = new VeriFonePaymentData(bytes, "7");
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD1_ACK : 2 " + str);
						} else if (bytes[1] == MessageId.VERIFONE_CMD8_NACK) {
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD8_NACK : 1 " + str);
							meterMessage = new VeriFonePaymentData(bytes, "8");
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD8_NACK : 2 " + str);
						} else if (bytes[1] == MessageId.VERIFONE_CMD2_ACK) {
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD2_ACK : 1 " + str);
							meterMessage = new VeriFonePaymentData(bytes, "9");
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD2_ACK : 2 " + str);
						} else if (bytes[1] == MessageId.VERIFONE_CMD10) {
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD10 : 1 " + str);
							meterMessage = new VeriFonePaymentData(bytes, "10");
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: VERIFONE_CMD10 : 2 " + str);
						} else {
							String str = new String(bytes);
							LogExceptionMSG("[run in meter_bluetooth][run][" + str + "]" + "Meter_Bluetooth: Else :  " + str);
						}

						if (meterMessage != null) {
							for (IMessageListener list : AVL_Service.msg_listeners.values())
								list.receivedMeterMessage(meterMessage);
						}

						synchronized (currentThread()) {
							currentThread().wait(500);
						}
						// if (!isVerifone)
						mmOutStream.write(MessageId.ACKNOWLEDGEMENT);
						Log.w(getClass().getSimpleName(), "Ack sent to Meter: " + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));

						if (!isVerifone && ((bytes[1] == MessageId.CREDIT_CARD_DATA_VALUE) || (bytes[1] == MessageId.METER_ON_OFF_STATE_CHANGE_VALUE))) {
							queryMeterTripData();
						}
						try {
							if ((isVerifone && (bytes[1] == MessageId.METER_ON_OFF_STATE_CHANGE_VALUE))) {
								if ((Character.toString(((MeterStateChangeMessage) meterMessage).getState()).equalsIgnoreCase("2"))) {
									if (((System.currentTimeMillis() - lastVerifoneTripMeterTripDataTime) > 10000)) {
										lastVerifoneTripMeterTripDataTime = System.currentTimeMillis();
										queryMeterTripData();
										LogExceptionMSG("[run in meter_bluetooth][run]" + "Meter_Bluetooth: queryMeterTripData  ");
									}
								} else if ((Character.toString(((MeterStateChangeMessage) meterMessage).getState()).equalsIgnoreCase("1"))) {
									flaggerStartedFromMeter_Verifone = true;
								}
							}
						} catch (Exception ex) {
						}
					}

				}// if bytes!=null
			} catch (IOException e) {
				LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "]");
			} catch (InvalidMeterMessageException e) {
				LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "] InvalidMeterMessageException");
			} catch (InterruptedException e) {
				LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "] InterruptedException");
				e.printStackTrace();
			} catch (NullPointerException e) {
				LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "] NullPointerException");
			} catch (RuntimeException e) {
				LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "] RuntimeException");
			} catch (Exception e) {
				LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "]");
			}

		}// while

		try {
			if (mmSocket != null) {
				mmSocket.close();
			}
			isreceive = false;
		} catch (IOException e) {
			LogExceptionMSG("[exception in run in meter_bluetooth][run][" + e.getLocalizedMessage() + "] IOException");
		} catch (Exception re) {
			LogExceptionMSG("[exception in run in meter_bluetooth][run][" + re.getLocalizedMessage() + "] ");
		}

	}// run

	/*----------------------------------------------------------------LogExceptionMSG---------------------------------------------------------------------------*/
	public void LogExceptionMSG(String MSG) {
		for (IMessageListener list : AVL_Service.msg_listeners.values()) {
			list.LogException(MSG);
		}
	}

	/*----------------------------------------------------------------queryMeterTripData---------------------------------------------------------------------------*/
	public void queryMeterTripData() {
		try {
			byte[] b = {0x02, MessageId.REQUEST_METER_TRIP_DATA_VALUE, 0x00};
			byte bcc = (byte) calculateBlockChecksum(b);
			byte[] requestMeterTripData = {0x02, MessageId.REQUEST_METER_TRIP_DATA_VALUE, 0x00, bcc, 0x03};
			mmOutStream.write(requestMeterTripData);
			mmOutStream.flush();
			Log.w(getClass().getSimpleName(), "Bluetooth - Sending Bytes: " + ByteArray.byteArrayToHexString(requestMeterTripData));
		} catch (IOException e) {
			LogExceptionMSG("[exception in queryMeterTripData in meter_bluetooth][queryMeterTripData][" + e.getLocalizedMessage() + "] IOException");
		} catch (Exception e) {
			LogExceptionMSG("[exception in queryMeterTripData in meter_bluetooth][queryMeterTripData][" + e.getLocalizedMessage() + "]");
		}
	}
	/*----------------------------------------------------------------isConnectedToMeter---------------------------------------------------------------------------*/
	public boolean isConnectionAlive() {

		try {
			Log.w(getClass().getSimpleName(), " isConnectionAlive() " + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			// if (!isVerifone)
			mmOutStream.write(MessageId.ACKNOWLEDGEMENT);

			if (isConnectionAlive_exception) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[isConnectionAlive in meter_bluetooth][queryMeterTripData]");
				isConnectionAlive_exception = false;
			}

			return true;
		} catch (NullPointerException e) {
			if (isConnectionAlive_exception) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exceptionToast("[exception in isConnectionAlive in meter_bluetooth][isConnectionAlive][" + e.getLocalizedMessage() + "] NullPointerException");
			} else if (!isConnectionAlive_exception) {
				LogExceptionMSG("[exception in isConnectionAlive in meter_bluetooth][isConnectionAlive][" + e.getLocalizedMessage() + "] NullPointerException");
				isConnectionAlive_exception = true;
			}
			Log.e(getClass().getSimpleName(), " Meter OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			if (AVL_Service.loggedIn)
				isreceive = false;
			return false;
		} catch (IOException io) {
			Log.e(getClass().getSimpleName(), " Meter OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			if (AVL_Service.loggedIn)
				isreceive = false;
			return false;
		} catch (Exception io) {
			Log.e(getClass().getSimpleName(), " Meter OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			if (AVL_Service.loggedIn)
				isreceive = false;
			return false;
		}
	}

	/*---------------------------------------------------------------connectToBluetooth-------------------------------------------------------------------*/
	public Boolean write(PrinterMessage msg) {

		try {
			mmOutStream.write(msg.toByteArray());
			// mmOutStream.write(MessageId.REQUEST_METER_CONFIGURATION);
			Log.w(getClass().getSimpleName(), "Sending Bytes to Meter Printer: " + ByteArray.byteArrayToHexString(msg.toByteArray()));
			return true;
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in PrinterMessage in meter_bluetooth][PrinterMessage][" + e.getLocalizedMessage() + "]");
			Log.e(getClass().getSimpleName(), " Printer OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			return false;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in PrinterMessage in meter_bluetooth][PrinterMessage][" + e.getLocalizedMessage() + "]");
			Log.e(getClass().getSimpleName(), " Printer OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			return false;
		}

	}

	/*---------------------------------------------------------------writeCustom-------------------------------------------------------------------*/
	public Boolean writeCustom(CustomMessage msg) {

		try {
			mmOutStream.write(msg.toByteArray());
			// mmOutStream.write(MessageId.REQUEST_METER_CONFIGURATION);
			Log.w(getClass().getSimpleName(), "Sending Bytes to Meter Printer: " + ByteArray.byteArrayToHexString(msg.toByteArray()));
			return true;
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in writeCustom in meter_bluetooth][writeCustom][" + e.getLocalizedMessage() + "]");
			Log.e(getClass().getSimpleName(), " Printer OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			return false;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in writeCustom in meter_bluetooth][writeCustom][" + e.getLocalizedMessage() + "]");
			Log.e(getClass().getSimpleName(), " Printer OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			return false;
		}

	}

	/*---------------------------------------------------------------FlushPrinterDataFromBuffer-------------------------------------------------------------------*/

	public Boolean FlushPrinterDataFromBuffer() {

		try {
			mmOutStream.write(MessageId.FLUSH_DATA_FROM_BUFFER);
			// mmOutStream.write(MessageId.REQUEST_METER_CONFIGURATION);
			return true;
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in FlushPrinterDataFromBuffer in meter_bluetooth][FlushPrinterDataFromBuffer][" + e.getLocalizedMessage() + "]");
			Log.e(getClass().getSimpleName(), " Printer OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			return false;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in FlushPrinterDataFromBuffer in meter_bluetooth][FlushPrinterDataFromBuffer][" + e.getLocalizedMessage() + "]");
			Log.e(getClass().getSimpleName(), " Printer OutPutStream is closed" + ByteArray.byteArrayToHexString(MessageId.ACKNOWLEDGEMENT));
			return false;
		}

	}

	/*--------------------------------------------------------------cancel--------------------------------------------------------------------------------*/
	public void cancel() {

		isreceive = false;

	}// Cancel

	/*---------------------------------------------------------------getAddress-------------------------------------------------------------------*/
	public String getAddress() {

		return address;

	}
	/*---------------------------------------------------------------unlockMeter-------------------------------------------------------------------*/
	public Boolean unlockMeter() {
		try {
			mmOutStream.write(MessageId.UNLOCK_METER);
			Log.w(getClass().getSimpleName(), "Bluetooth - Sending Bytes: " + ByteArray.byteArrayToHexString(MessageId.UNLOCK_METER));
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in unlockMeter in meter_bluetooth][unlockMeter][" + e.getLocalizedMessage() + "]");
			return false;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in unlockMeter in meter_bluetooth][unlockMeter][" + e.getLocalizedMessage() + "]");
			return false;
		}
		return true;
	}

	public Boolean lockMeter() {
		try {
			mmOutStream.write(MessageId.LOCK_METER);
			Log.w(getClass().getSimpleName(), "Bluetooth - Sending Bytes: " + ByteArray.byteArrayToHexString(MessageId.LOCK_METER));
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("Meter_Bluetooth: lockMeter(): IOException: " + e.getLocalizedMessage() + "|");
			return false;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in lockMeter in meter_bluetooth][lockMeter][" + e.getLocalizedMessage() + "]");
			return false;
		}
		return true;
	}

	/*---------------------------------------------------------------calculateBlockChecksum-------------------------------------------------------------------*/
	protected int calculateBlockChecksum(byte[] messageIn) {

		int bcc = 0;
		try {
			byte[] bccData = messageIn;

			Log.w(getClass().getSimpleName(), "Calculating - Block Checksum Data: " + ByteArray.byteArrayToHexString(bccData));

			for (int i = 0; i < bccData.length; i++) {
				bcc = bcc ^ bccData[i];
			}
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in calculateBlockChecksum in meter_bluetooth][calculateBlockChecksum][" + e.getLocalizedMessage() + "]");
		}
		return bcc;

	}

	/*--------------------------------------------------------pairDevice--------------------------------------------------------------------*/
	// public boolean pairDevice(BluetoothDevice btDevice) throws Exception {
	// Class<?> class1 = Class.forName("android.bluetooth.BluetoothDevice");
	// Method createBondMethod = class1.getMethod("createBond");
	// Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
	// return returnValue.booleanValue();
	// }

	public static boolean pairDevice() {
		try {
			Class<?> btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());
			Method createBondMethod = btDeviceInstance.getMethod("createBond");
			Boolean returnValue = (Boolean) createBondMethod.invoke(mmDevice);

			return returnValue.booleanValue();
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public static void setpin(String passkey) {
		Class<?> btDeviceInstance;
		try {
			btDeviceInstance = Class.forName(BluetoothDevice.class.getCanonicalName());

			Method convert = btDeviceInstance.getMethod("convertPinToBytes", String.class);
			byte[] pin = (byte[]) convert.invoke(mmDevice, passkey);

			Method setPin = btDeviceInstance.getMethod("setPin", byte[].class);
			setPin.invoke(mmDevice, pin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*-------------------------------------------------------unpairDevice-------------------------------------------------------------------*/
	protected void unpairDevice(BluetoothDevice device) {
		try {
			Method m = device.getClass().getMethod("removeBond", (Class[]) null);
			m.invoke(device, (Object[]) null);
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in unpairDevice in meter_bluetooth][unpairDevice][" + e.getLocalizedMessage() + "]");
		}
	}
}// Class Bluetooth
