package itcurves.ncs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.Vector;
import itcurves.ncs.bluebamboo.BlueBambooStates;
import itcurves.ncs.bluebamboo.Command;
import itcurves.ncs.bluebamboo.FontDefine;
import itcurves.ncs.bluebamboo.NumberUtil;
import itcurves.ncs.bluebamboo.PocketPos;
import itcurves.ncs.bluebamboo.StringUtil;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/*
 * |-------------------| | BlueTooth Connection Class |
 * |--------------------------------------------------|
 */

public class BlueBamboo_Bluetooth {
	private BluetoothSocket mmSocket;
	private BluetoothDevice mmDevice;
	private InputStream mmInStream;
	private OutputStream mmOutStream;
	private String address;
	private final BlueBambooStates state = new BlueBambooStates();
	public Vector<Byte> packdata = new Vector<Byte>(2048);
	private Thread msgReciverThread;
	public static boolean isreceive = true;
	private boolean isPrinted = false;

	/*---------------------------------------------------------------connectToBluetooth-------------------------------------------------------------------*/
	public Boolean connectToBluetooth(BluetoothDevice device) {
		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final
		BluetoothSocket tmp = null;
		mmDevice = device;
		try {

			tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			mmSocket = tmp;
			mmSocket.connect();
			address = mmDevice.getAddress();
			isreceive = true;

			msgReciverThread = new Thread(null, reciver_Runnable, "msg_reciver_thread");

		} catch (IOException e) {
			try {
				if (mmSocket != null) {
					mmSocket.close();
					mmSocket = null;
				}
				Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
				mmSocket = (BluetoothSocket) m.invoke(mmDevice, 1);
				mmSocket.connect();
				address = mmDevice.getAddress();
				isreceive = true;
				msgReciverThread = new Thread(null, reciver_Runnable, "msg_reciver_thread");
			} catch (NoSuchMethodException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] NoSuchMethodException");
				return false;
			} catch (IllegalAccessException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IllegalAccessException");
				return false;
			} catch (InvocationTargetException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] InvocationTargetException");
				return false;
			} catch (IOException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IOException");
				return false;
			}
			// return true;
		} catch (SecurityException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] SecurityException");
			return false;
		} catch (IllegalArgumentException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IllegalArgumentException");
			return false;
		}

		try {
			mmInStream = mmSocket.getInputStream();
			mmOutStream = mmSocket.getOutputStream();
			mmInStream.available();
			return true;
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "]");
			mmInStream = null;
			mmOutStream = null;
			return false;
		}
	}

	/*---------------------------------------------------------------write-------------------------------------------------------------------*/
	public Boolean print(String msg) {

		try {
			if (msg != null && msg.length() > 0) {
				byte[] line1 = printfont(msg + "\n\n\n", FontDefine.FONT_32PX, FontDefine.Align_CENTER, (byte) 0x1A, PocketPos.LANGUAGE_CHINESE);
				byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, line1, 0, line1.length);
				synchronized (state) {
					isPrinted = false;
					sendSocketMsg(senddata);
					state.wait(10000);
				}
				return isPrinted;
			} else
				return false;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in print in bluebamboo_bluetooth][print][" + e.getLocalizedMessage() + "]");
			return false;
		}
	}
	/*-----------------------------------------------------------sendSocketMsg--------------------------------------------------------------------------------*/
	public void sendSocketMsg(byte[] msg) {
		if (mmSocket != null) {
			try {
				mmOutStream.write(msg);
				mmOutStream.flush();

			} catch (Exception e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in sendSocketMsg in bluebamboo_bluetooth][sendSocketMsg][" + e.getLocalizedMessage() + "]");
			}
		}
	}

	/*---------------------------------------------------------------getAddress-------------------------------------------------------------------*/
	public String getAddress() {

		return address;

	}

	/*---------------------------------------------------------------reciver_Runnable----------------------------------------------------------------*/
	private final Runnable reciver_Runnable = new Runnable() {
		public void run() {
			Thread.currentThread().setName("BlueBamboo");
			try {
				while (isreceive) {
					if ((mmInStream != null) && (mmInStream.available() > 0)) {
						byte[] temp = new byte[2048];// 2k

						do
							if (mmInStream.available() > 0)
								mmInStream.read(temp, 0, 1);
						while ((temp[0] != Command.START_FRAME) && (isreceive));

						int tempIndex = 0;
						do {
							if (mmInStream.available() > 0) {
								++tempIndex;
								mmInStream.read(temp, tempIndex, 1);
							}
						} while ((temp[tempIndex] != Command.END_FRAME) && (isreceive));

						byte[] btBuf = new byte[++tempIndex];

						System.arraycopy(temp, 0, btBuf, 0, btBuf.length);

						for (int i = 0; i < btBuf.length; i++) {
							Log.i("receive message package data log", btBuf[i] + "" + "_" + i);
						}

						String tempdata = new String(btBuf, "utf-8");
						Log.i("receive data===", tempdata);

						// //////////////////////////////////////////////////////
						if (btBuf[0] == Command.START_FRAME && btBuf[btBuf.length - 1] == Command.END_FRAME) {
							Log.i("receive message", "put whole data");
							packdata.clear();
							for (int i = 0; i < btBuf.length; i++) {
								packdata.add(btBuf[i]);
							}
							parsePacketData(3);
						}

						else if ((btBuf[0] == Command.START_FRAME) && (btBuf[btBuf.length - 1] != Command.END_FRAME)) {
							Log.i("receive message package data", "No1");
							if (packdata != null && packdata.size() > 0) {
								packdata.clear();

							}
							for (int i = 0; i < btBuf.length; i++) {
								packdata.add(btBuf[i]);
							}
						}

						else if ((btBuf[0] != Command.START_FRAME) && (btBuf[btBuf.length - 1] == Command.END_FRAME)) {
							Log.i("receive message package data", "No2");
							if (packdata != null && packdata.size() > 0) {
								if (packdata.get(0) == Command.START_FRAME) {

									if (packdata.size() <= 2048) {
										for (int i = 0; i < btBuf.length; i++) {
											packdata.add(btBuf[i]);
										}
										parsePacketData(3);
									} else {
										packdata.clear();
										Log.i("receive message package data", "Clear 2");
									}
								}
							}
						} else if (btBuf[0] != Command.START_FRAME && btBuf[btBuf.length - 1] != Command.END_FRAME) {
							if (packdata != null && packdata.size() > 0) {
								if (packdata.get(0) == Command.START_FRAME) {
									// Log.i("receive message package data","No3");
									for (int i = 0; i < btBuf.length; i++) {
										packdata.add(btBuf[i]);
									}
								}
							}// if Packet No3
						}// if

					}// if Byte Available
				}// while

			} catch (Exception e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in reciver_Runnable in bluebamboo_bluetooth][reciver_Runnable][" + e.getLocalizedMessage() + "]");
				isreceive = false;
			}
			try {
				if (mmSocket != null) {
					mmOutStream.close();
					mmInStream.close();
					mmSocket.close();
				}
				mmSocket = null;
				isreceive = false;
				// this.interrupt();
			} catch (IOException e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in reciver_Runnable in bluebamboo_bluetooth][reciver_Runnable][" + e.getLocalizedMessage() + "]");
			}
		}// run()
	};

	/*---------------------------------------------------------------run----------------------------------------------------------------------------------*/
	// @Override
	// public void run() {
	// msgReciverThread.start();
	// }// run

	/*--------------------------------------------------------------cancel--------------------------------------------------------------------------------*/
	public void flush() {

		try {
			mmOutStream.flush();
			// mmInStream.close();
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in flush in bluebamboo_bluetooth][flush][" + e.getLocalizedMessage() + "]");
		}
		stopReceiveThread();

	}// Cancel

	/*--------------------------------------------------------------printfont--------------------------------------------------------------------------------*/

	public byte[] printfont(String content, byte fonttype, byte fontalign, byte linespace, byte language) {

		if (content != null && content.length() > 0) {
			Log.i("print==", content);
			content = content + "\n";
			byte[] temp = null;
			temp = PocketPos.convertPrintData(content, 0, content.length(), language, fonttype, fontalign, linespace);

			return temp;
		} else {
			return null;
		}
	}

	/*--------------------------------------------------------------startReceiveThread--------------------------------------------------------------------------------*/
	public void startReceiveThread() {
		// start();
		msgReciverThread.start();
	}

	/*--------------------------------------------------------------stopReceiveThread--------------------------------------------------------------------------------*/

	public void stopReceiveThread() {
		isreceive = false;
	}

	/*----------------------------------------------------------------isConnectionAlive---------------------------------------------------------------------------*/
	public boolean isConnectionAlive() {
		try {
			if (mmOutStream == null || mmSocket == null)
				return false;

			synchronized (state) {
				state.setState(BlueBambooStates.NO_RESPONSE);
				mmOutStream.write(Command.QUERY_STAT);
				state.wait(10000);
			}

			if (state.getState() == BlueBambooStates.NO_ERROR)
				return true;
			else {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("BlueBamboo " + state.getDesc(state.getState()));
				isreceive = false;
				return false;
			}
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in isConnectionAlive in bluebamboo_bluetooth][isConnectionAlive][" + e.getLocalizedMessage() + "]");
			isreceive = false;
			return false;
		}
	}

	/*--------------------------------------------------------------sendMsg--------------------------------------------------------------------------------*/

	private void parsePacketData(int flag) {
		if (flag == 3) {
			int size = packdata.size();
			byte[] buffer = new byte[size - 2]; // do not copy C0 and C1 in buffer

			for (int i = 0; i < (size - 2); i++)
				buffer[i] = packdata.get(i + 1);

			if (buffer != null) {
				// handle the message
				// byte[] content = PocketPos.FrameUnpack(buffer, 0, buffer.length);

				switch (buffer[0]) {

					case PocketPos.FRAME_MSR :

						// String msrData = new String(buffer);
						String trackone = "";
						String tracktwo = "";
						String trackthree = "";
						byte tracknumber;
						int offset = 6;
						byte[] tracknumberlength = new byte[4];
						Log.i("buffer length = ", buffer.length + "");

						do {

							if (offset < buffer.length - 2) {
								tracknumber = buffer[offset];// track number
							} else {
								return;
							}
							if (StringUtil.toHexChar(tracknumber) == (byte) 0x31) {
								tracknumberlength[0] = buffer[offset + 1];
								tracknumberlength[1] = buffer[offset + 2];
								tracknumberlength[2] = buffer[offset + 3];
								tracknumberlength[3] = buffer[offset + 4];

								offset += 4;
								int tracklength = NumberUtil.parseInt(tracknumberlength, 0, tracknumberlength.length, 10);
								byte[] trackcontent = new byte[tracklength];
								System.arraycopy(buffer, 1 + offset, trackcontent, 0, tracklength);

								try {
									trackone = new String(trackcontent, "utf-8");
								} catch (UnsupportedEncodingException e) {
									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.exception("[exception in parsePacketData in bluebamboo_bluetooth][parsePacketData][" + e.getLocalizedMessage() + "]");
								}
								Log.i("track one content=", trackone);
								offset += tracklength;
								offset++;
							}

							if (offset < buffer.length - 2) {
								tracknumber = buffer[offset];// track number
							} else {
								break;
							}

							if (StringUtil.toHexChar(tracknumber) == (byte) 0x32) {
								Log.i("tracknumber = ", StringUtil.toHexString(tracknumber));
								tracknumberlength[0] = buffer[offset + 1];
								tracknumberlength[1] = buffer[offset + 2];
								tracknumberlength[2] = buffer[offset + 3];
								tracknumberlength[3] = buffer[offset + 4];

								offset += 4;
								int tracklength = NumberUtil.parseInt(tracknumberlength, 0, tracknumberlength.length, 10);
								byte[] trackcontent = new byte[tracklength];
								System.arraycopy(buffer, 1 + offset, trackcontent, 0, tracklength);

								try {
									tracktwo = new String(trackcontent, "utf-8");
								} catch (UnsupportedEncodingException e) {
									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.exception("[exception in parsePacketData in bluebamboo_bluetooth][parsePacketData][" + e.getLocalizedMessage() + "]");
								}

								offset += tracklength;
								offset++;
							}

							if (offset < buffer.length - 2) {
								tracknumber = buffer[offset];// track number
							} else {
								break;
							}

							if (StringUtil.toHexChar(tracknumber) == (byte) 0x33) {
								Log.i("tracknumber = ", StringUtil.toHexString(tracknumber));
								tracknumberlength[0] = buffer[offset + 1];
								tracknumberlength[1] = buffer[offset + 2];
								tracknumberlength[2] = buffer[offset + 3];
								tracknumberlength[3] = buffer[offset + 4];

								offset += 4;
								int tracklength = NumberUtil.parseInt(tracknumberlength, 0, tracknumberlength.length, 10);
								byte[] trackcontent = new byte[tracklength];
								System.arraycopy(buffer, 1 + offset, trackcontent, 0, tracklength);

								try {
									trackthree = new String(trackcontent, "utf-8");
								} catch (UnsupportedEncodingException e) {
									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.exception("[exception in parsePacketData in bluebamboo_bluetooth][parsePacketData][" + e.getLocalizedMessage() + "]");
								}
								Log.i("track three content=", trackthree);

								offset += tracklength;
								offset++;
								break;
							}

						} while (offset < buffer.length - 2);

						String track1Data = StringUtil.getDataBetweenTags(trackone, "%", "?");
						String track2Data = StringUtil.getDataBetweenTags(tracktwo, ";", "?");
						String cardNumber = "";
						String expDate = "";
						String name = "";

						if (!track1Data.equals("")) {
							int nameStart = track1Data.indexOf("^", 0);
							int nameEnd = track1Data.indexOf("^", nameStart + 1);
							if (nameStart != -1 && nameEnd != -1) {
								cardNumber = track1Data.substring(1, nameStart);
								cardNumber = StringUtil.removeChar(cardNumber, ' ');
								name = track1Data.substring(nameStart + 1, nameEnd);
								expDate = track1Data.substring(nameEnd + 1, nameEnd + 1 + 4);
							}
						}
						if (!track2Data.equals("")) {
							int cardNumberEnd = track2Data.indexOf("=", 0);
							if (cardNumberEnd != -1) {
								cardNumber = track2Data.substring(0, cardNumberEnd);
								cardNumber = StringUtil.removeChar(cardNumber, ' ');
								expDate = track2Data.substring(cardNumberEnd + 1, cardNumberEnd + 1 + 4);
							}
						}

						if (expDate.length() == 4)
							expDate = expDate.substring(2) + expDate.substring(0, 2);

						if (!cardNumber.equalsIgnoreCase("")) {
							itcurves.ncs.CreditCard cc = new itcurves.ncs.CreditCard();
							cc.setTrack1Data(track1Data);
							cc.setTrack2Data(track2Data);
							cc.setCardNumber(cardNumber);
							cc.setExpirationDate(expDate);
							cc.setCardHolderName(name);

							for (IMessageListener list : AVL_Service.msg_listeners.values())
								list.receivedCreditCardData(cc);
						}// if
						break;
					case PocketPos.FRAME_ACK :
						synchronized (state) {
							state.setState(BlueBambooStates.NO_ERROR);
							state.notifyAll();
						}
						break;
					case PocketPos.FRAME_STATUS_RESPONSE :
						synchronized (state) {
							state.setState(buffer[1]);
							state.notifyAll();
						}
						break;
					case PocketPos.FRAME_ETX :
						synchronized (state) {
							isPrinted = true;
							state.notifyAll();
						}
						break;

				}// switch
			}// buffer != null

		}

	}// sendmsg()

}// Class Bluetooth