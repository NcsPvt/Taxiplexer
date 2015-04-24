package itcurves.ncs;

import itcurves.ncs.taximeter.messages.ByteArray;
import itcurves.ncs.vivotech.VivoTechCommand;
import itcurves.ncs.vivotech.VivoTechCommandPacket;
import itcurves.ncs.vivotech.VivoTechResponsePacket;
import itcurves.ncs.vivotech.VivotechMessageReader;
import itcurves.ncs.vivotech.VivotechScreen;
import itcurves.ncs.vivotech.VivoTechResponsePacket.VivoTechResponseStatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.UUID;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/*
 * |-------------------| | BlueTooth Connection Class |
 * |--------------------------------------------------|
 */

public class Vivotech_Bluetooth extends Thread {
	private BluetoothSocket mmSocket = null;
	private InputStream mmInStream;
	private OutputStream mmOutStream;
	private VivoTechResponsePacket responsePacket;
	protected static LinkedList<VivoTechCommandPacket> vivo_msg_list = new LinkedList<VivoTechCommandPacket>();
	private Thread msgSenderThread;
	protected static Boolean waitingInput = false;
	public static boolean isreceive = true;

	/*---------------------------------------------------------------connectToBluetooth-------------------------------------------------------------------*/
	public Boolean connectToBluetooth(BluetoothDevice device) {

		try {
			interrupted(); // clears any intrruption flag
			if (mmSocket != null) {
				mmSocket.close();
				mmSocket = null;
			}
			mmSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			mmSocket.connect();
			device.getAddress();
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IOException");
			try {
				if (mmSocket != null) {
					mmSocket.close();
					mmSocket = null;
				}
				Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
				mmSocket = (BluetoothSocket) m.invoke(device, 1);
				mmSocket.connect();
				device.getAddress();
			} catch (NoSuchMethodException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] NoSuchMethodException");
				return false;
			} catch (IllegalAccessException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IllegalAccessException");
				return false;
			} catch (InvocationTargetException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] InvocationTargetException");
				return false;
			} catch (IOException e1) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] ");
				return false;
			}
			// return true;
		} catch (SecurityException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] SecurityException");
			return false;
		} catch (IllegalArgumentException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IllegalArgumentException");
			return false;
		}

		try {
			mmInStream = mmSocket.getInputStream();
			mmOutStream = mmSocket.getOutputStream();
			mmInStream.available();
			msgSenderThread = new Thread(null, sender_Runnable, "Vivotech_msg_sender_thread");
			return true;
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in connectToBluetooth in vivotech_bluetooth][connectToBluetooth][" + e.getLocalizedMessage() + "] IOException");
			mmInStream = null;
			mmOutStream = null;
			return false;
		}
	}// connectToBluetooth

	/*----------------------------------------------------------------isConnectionAlive---------------------------------------------------------------------------*/
	public boolean isConnectionAlive() {

		try {
			Log.w(getClass().getSimpleName(), " isConnectionAlive() ");
			VivoTechCommandPacket msg = new VivoTechCommandPacket();
			msg.setVivoTechCommand(VivoTechCommand.BEEP);
			byte[] data = {0x0};
			msg.setDataBlock(data);
			mmOutStream.write(msg.toCmdStringByte());
		} catch (NullPointerException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in isConnectionAlive in vivotech_bluetooth][isConnectionAlive][" + e.getLocalizedMessage() + "] NullPointerException");
			return false;
		} catch (IOException io) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in isConnectionAlive in vivotech_bluetooth][isConnectionAlive][" + io.getLocalizedMessage() + "] IOException");
			return false;
		}
		return true && !isInterrupted();
	}

	/*---------------------------------------------------------------sendCommand-------------------------------------------------------------------*/
	protected static Boolean sendCommand(VivoTechCommandPacket msg) {

		synchronized (vivo_msg_list) {
			if (vivo_msg_list.isEmpty()) {
				vivo_msg_list.addFirst(msg);
				vivo_msg_list.notifyAll();
			} else
				vivo_msg_list.addFirst(msg);
		}

		return true;

	}

	/*---------------------------------------------------------------run----------------------------------------------------------------------------------*/
	@Override
	public void run() {

		try {

			Thread.currentThread().setName("VivoTech Receiver");
			if (!msgSenderThread.isAlive())
				msgSenderThread.start();

			VivoTechCommandPacket msg = new VivoTechCommandPacket();
			msg.setVivoTechCommand(VivoTechCommand.BEEP);
			byte[] data = {0x03};
			msg.setDataBlock(data);
			Log.w("Vivo Reciever", "Sent first msg as BEEP " + new String(msg.toCmdStringByte()));
			mmOutStream.write(msg.toCmdStringByte());

			VivotechMessageReader reader = new VivotechMessageReader(mmInStream);

			byte[] bytes;

			while (isreceive) {
				try {
					// Read from the InputStream
					bytes = reader.getNextMessage();
					if (bytes != null) {
						Log.w(getClass().getSimpleName(), "Bluetooth Vivotech - Read Raw Bytes: " + ByteArray.byteArrayToHexString(bytes));
						Log.w(getClass().getSimpleName(), "Bluetooth Vivotech - String Bytes: " + new String(bytes));
						synchronized (vivo_msg_list) {
							vivo_msg_list.notifyAll();
						}
						responsePacket = new VivoTechResponsePacket(bytes.clone());
						if (responsePacket.getStatusCodeString() == VivoTechResponseStatusCode.OK) {
							switch (responsePacket.getCommand()) {
								case 0x83 : {
									if (responsePacket.getDataBlockString().length() == 4) {
										for (int i = 0; i < TaxiPlexer.screenButtons.size(); i++)
											if (TaxiPlexer.screenButtons.get(i).getID().equalsIgnoreCase("0")) {
												TaxiPlexer.screenButtons.get(i).setID(responsePacket.getDataBlockHexString());
												break;
											}
									} else if (responsePacket.getDataBlockString().length() > 4) {
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedVivotechMessage(responsePacket.getDataBlockString());
									}
								}// case 0x83
							}// Switch
						}// if VivoTechResponseStatusCode.OK
						else {
							for (IMessageListener list : AVL_Service.msg_listeners.values())
								list.receivedVivotechError(responsePacket.getStatusCodeString().toString());
							VivotechScreen.showScreen(VivotechScreen.currentScreen);
						}
					}// if
				} catch (IOException e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in run in vivotech_bluetooth][run][" + e.getLocalizedMessage() + "] ");
					interrupt();
				}
			}// while

			if (mmSocket != null) {
				mmSocket.close();
				mmSocket = null;
			}
		} catch (RuntimeException re) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in run in vivotech_bluetooth][run][" + re.getLocalizedMessage() + "] RuntimeException");
		} catch (IOException closeException) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in run in vivotech_bluetooth][run][" + closeException.getLocalizedMessage() + "] IOException");
		}
	}// run

	/*--------------------------------------------------------------Sender Runnable-----------------------------------------------------------------------*/
	private final Runnable sender_Runnable = new Runnable() {

		private VivoTechCommandPacket msgToSend;

		public void run() {

			while (!isInterrupted()) {
				try {

					synchronized (vivo_msg_list) {
						if (!vivo_msg_list.isEmpty()) {
							msgToSend = vivo_msg_list.removeLast();
							if (msgToSend.getVivoTechCommand() == VivoTechCommand.GET_INPUT_EVENT_8800)
								waitingInput = true;
							mmOutStream.write(msgToSend.toCmdStringByte());
							vivo_msg_list.wait(1000);
						}// if
						else
							vivo_msg_list.wait();
					}// synchronized

				} catch (InterruptedException e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in sender_Runnable in vivotech_bluetooth][sender_Runnable][" + e.getLocalizedMessage() + "] InterruptedException");
				} catch (IOException e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in sender_Runnable in vivotech_bluetooth][sender_Runnable][" + e.getLocalizedMessage() + "] IOException");
					interrupt();
				}
			}// while
		}
	}; // network_Runnable

	/*--------------------------------------------------------------cancel--------------------------------------------------------------------------------*/
	public void cancel() {

		isreceive = false;
		try {
			mmOutStream.flush();
			// mmInStream.close();
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in cancel in vivotech_bluetooth][cancel][" + e.getLocalizedMessage() + "] ");
		}

	}// Cancel

}// Class Bluetooth
