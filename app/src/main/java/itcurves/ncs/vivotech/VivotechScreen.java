package itcurves.ncs.vivotech;

import java.io.UnsupportedEncodingException;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;
import itcurves.ncs.TaxiPlexer;
import itcurves.ncs.Vivotech_Bluetooth;
import android.util.Log;

public class VivotechScreen extends Vivotech_Bluetooth {

	public static class Button {

		private String ID = "0";
		private String name = "";
		private String type = "";

		Button(String type) {
			this.setID("0");
			this.name = "";
			this.setType(type);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getID() {
			return ID;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	private enum DisplayMethod {
		NO_CLEAR_CENTER_ON_Y("0"),
		CLEAR_SCREEN_CENTER_ON_Y("1"),
		NO_CLEAR_DISPLAY_AT_XY("2"),
		CLEAR_SCREEN_DISPLAY_AT_XY("3"),
		NO_CLEAR_CENTER_TEXT("4"),
		CLEAR_SCREEN_CENTER_TEXT("5");

		private final String displayMethod;

		private DisplayMethod(String display) {
			this.displayMethod = display;
		}

		public String getDisplayMethod() {
			return displayMethod;
		}
	}

	private enum FontSize {
		REGULAR_11("1"),
		REGULAR_15("2"),
		BOLD_15("3"),
		REGULAR_18("4"),
		REGULAR_16("5"),
		BOLD_16("6"),
		REGULAR_24("7"),
		REGULAR_32("8"),
		BOLD_32("9"),
		REGULAR_48("10"),
		BOLD_48("11");

		private final String fontSize;

		private FontSize(String size) {
			this.fontSize = size;
		}

		public String getSize() {
			return fontSize;
		}
	}

	public enum Screen {
		WELCOME,
		CASH,
		PAYMENT_START,
		TIP,
		PRE_SWIPE,
		SWIPE,
		AUTHORIZING,
		APPROVED,
		REJECTED,
	}

	private enum ColorScheme {
		BLACK_ON_WHITE(new byte[]{0, 0, 0, 0, 0, (byte) 255, (byte) 255, (byte) 255}),
		BLACK_ON_YELLOW(new byte[]{0, 0, 0, 0, 0, (byte) 255, (byte) 255, 0}),
		WHITE_ON_BLACK(new byte[]{0, (byte) 255, (byte) 255, (byte) 255, 0, 0, 0, 0}),
		BLACK_ON_BLACK(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}),
		YELLOW_ON_BLACK(new byte[]{0, (byte) 255, (byte) 255, 0, 0, 0, 0, 0}),
		GREEN_ON_BLACK(new byte[]{0, 0, (byte) 255, 0, 0, 0, 0, 0}),
		RED_ON_BLACK(new byte[]{0, (byte) 255, 0, 0, 0, 0, 0, 0}),
		BLUE_ON_BLACK(new byte[]{0, 0, 0, (byte) 255, 0, 0, 0, 0}),
		BLACK_ON_RED(new byte[]{0, 0, 0, 0, 0, (byte) 255, 0, 0});

		byte[] selectedColor;

		private ColorScheme(byte[] colors) {
			this.selectedColor = colors;
		}

	}

	public static Screen currentScreen;

	public static void showScreen(Screen screenID) {

		switch (screenID) {
			case WELCOME : {
				currentScreen = Screen.WELCOME;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					cancelInputOrTrans();
					clearEventQueue();
					startCustomDisplay();
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("0", "40", FontSize.BOLD_32, DisplayMethod.CLEAR_SCREEN_CENTER_ON_Y, "**Welcome**");
					setColors(ColorScheme.GREEN_ON_BLACK);
					if (TaxiPlexer.currentTrip != null)
						displayText("0", "80", FontSize.BOLD_32, DisplayMethod.NO_CLEAR_CENTER_ON_Y, TaxiPlexer.currentTrip.clientName);
					else
						displayText("0", "80", FontSize.BOLD_32, DisplayMethod.NO_CLEAR_CENTER_ON_Y, "Guest");
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("0", "140", FontSize.BOLD_32, DisplayMethod.NO_CLEAR_CENTER_ON_Y, "You are in vehicle:");
					setColors(ColorScheme.GREEN_ON_BLACK);
					displayText("0", "180", FontSize.BOLD_32, DisplayMethod.NO_CLEAR_CENTER_ON_Y, AVL_Service.pref.getString("VehicleID", "N/A"));
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;

			}// WELCOME
			case CASH : {
				currentScreen = Screen.CASH;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					clearEventQueue();
					startCustomDisplay();
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("40", "40", FontSize.BOLD_32, DisplayMethod.CLEAR_SCREEN_CENTER_ON_Y, "Driver Selected Cash");
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}
				break;
			}// CASH

			case PAYMENT_START : {
				currentScreen = Screen.PAYMENT_START;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					cancelInputOrTrans();
					clearEventQueue();
					startCustomDisplay();
					setColors(ColorScheme.WHITE_ON_BLACK);
					String subtotalString = "Fare:  $" + TaxiPlexer.Fare;
					displayText("0", "20", FontSize.BOLD_32, DisplayMethod.CLEAR_SCREEN_CENTER_ON_Y, subtotalString);
					displayText("0", "90", FontSize.BOLD_32, DisplayMethod.NO_CLEAR_CENTER_ON_Y, "Charge your ride?");
					setColors(ColorScheme.GREEN_ON_BLACK);
					displayButton("150", "160", "160", "80", FontSize.BOLD_32, DisplayMethod.NO_CLEAR_CENTER_ON_Y, new Button("OK"), "OK");
					waitForInput();
					// startScreen();
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;
			}// case:PAYMENT_START
			case TIP : {
				currentScreen = Screen.TIP;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					clearEventQueue();
					startCustomDisplay();
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("0", "10", FontSize.BOLD_32, DisplayMethod.CLEAR_SCREEN_CENTER_ON_Y, "Enter Tip");
					displayText("60", "60", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, "10%");
					displayText("200", "60", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_CENTER_ON_Y, "15%");
					displayText("380", "60", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, "20%");
					setColors(ColorScheme.GREEN_ON_BLACK);
					displayButton("10", "85", "140", "70", FontSize.REGULAR_18, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, new Button("ACCEPT"), "10%");
					displayButton("180", "85", "140", "70", FontSize.REGULAR_18, DisplayMethod.NO_CLEAR_CENTER_ON_Y, new Button("ACCEPT"), "15%");
					displayButton("330", "85", "140", "70", FontSize.REGULAR_18, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, new Button("ACCEPT"), "20%");
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("40", "160", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, TaxiPlexer.TipA);
					displayText("180", "160", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_CENTER_ON_Y, TaxiPlexer.TipB);
					displayText("360", "160", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, TaxiPlexer.TipC);
					displayText("40", "225", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, "Skip Tip:");
					displayButton("330", "210", "100", "50", FontSize.REGULAR_18, DisplayMethod.NO_CLEAR_CENTER_ON_Y, new Button("OK"), "OK");
					waitForInput();
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;
			}// TIP
			case PRE_SWIPE : {
				currentScreen = Screen.PRE_SWIPE;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					clearEventQueue();
					startCustomDisplay();
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("230", "20", FontSize.REGULAR_24, DisplayMethod.CLEAR_SCREEN_DISPLAY_AT_XY, "Fare:   $" + TaxiPlexer.Fare);
					displayText("220", "60", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, "Extras:   $" + TaxiPlexer.Extras);
					displayText("238", "100", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, "Tip:   $" + TaxiPlexer.Tip);
					displayText("20", "140", FontSize.REGULAR_24, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, "Total charged to card:   $" + String.valueOf(TaxiPlexer.total));
					setColors(ColorScheme.RED_ON_BLACK);
					displayButton("10", "200", "120", "60", FontSize.REGULAR_18, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, new Button("CANCEL"), "CANCEL");
					setColors(ColorScheme.GREEN_ON_BLACK);
					displayButton("350", "200", "120", "60", FontSize.REGULAR_18, DisplayMethod.NO_CLEAR_DISPLAY_AT_XY, new Button("ACCEPT"), "ACCEPT");
					waitForInput();
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;
			}// PRE_SWIPE
			case SWIPE : {
				currentScreen = Screen.SWIPE;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					cancelInputOrTrans();
					clearEventQueue();
					setColors(ColorScheme.WHITE_ON_BLACK);
					activateTransaction();
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;
			}// SWIPE
			case AUTHORIZING : {
				currentScreen = Screen.AUTHORIZING;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					clearEventQueue();
					setColors(ColorScheme.WHITE_ON_BLACK);
					displayText("40", "100", FontSize.BOLD_32, DisplayMethod.CLEAR_SCREEN_CENTER_TEXT, "Processing...");
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;
			}// AUTHORIZING
			case APPROVED : {
				currentScreen = Screen.APPROVED;
				Log.w("LOADING SCREEN", currentScreen.name());

				try {
					clearCommandBuffer();
					clearEventQueue();
					startCustomDisplay();
					setColors(ColorScheme.GREEN_ON_BLACK);
					displayText("40", "100", FontSize.BOLD_32, DisplayMethod.CLEAR_SCREEN_CENTER_TEXT, "Payment Approved");
				} catch (Exception e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in showScreen in VivotechScreen][showScreen][" + e.getLocalizedMessage() + "]");
				}

				break;
			}// APPROVED

		}// switch
	}// showScreen

	private static void clearCommandBuffer() {
		waitingInput = false;
		synchronized (vivo_msg_list) {
			vivo_msg_list.clear();
			vivo_msg_list.notifyAll();
		}
		TaxiPlexer.screenButtons.clear();
	}

	// Byte 0-9 Byte 10 Byte 11 Byte 12 Byte 13 Byte 14 Byte 15 Byte 16
	// HeaderTag Command Sub-Command DataLength(MSB) DataLength(LSB) Timeout CRC(LSB) CRC(MSB)
	private static void waitForInput() {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.GET_INPUT_EVENT_8800);
		byte[] data = {90};
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void displayButton(String x, String y, String width, String height, FontSize font, DisplayMethod dsplyMethod, Button btn, String btnName) throws UnsupportedEncodingException {
		btn.setName(btnName);
		btn.setID("0");
		TaxiPlexer.screenButtons.add(btn);

		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.DISPLAY_BUTTON_8800);
		String dataString = x + "\0" + y + "\0" + width + "\0" + height + "\0" + "1\0" + font.getSize() + "\0" + dsplyMethod.getDisplayMethod() + "\0" + btn.getType() + "\0";
		byte[] data = dataString.getBytes("ASCII");
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void displayText(String xPosition, String yPosition, FontSize font, DisplayMethod dsplyMethod, String stringToShow) throws UnsupportedEncodingException {

		TaxiPlexer.screenButtons.add(new Button("LABEL"));

		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.DISPLAY_TEXT_8800);
		String dataString = xPosition + "\0" + yPosition + "\0" + "1\0" + font.getSize() + "\0" + dsplyMethod.getDisplayMethod() + "\0" + stringToShow + "\0";
		byte[] data = dataString.getBytes("ASCII");
		msg.setDataBlock(data);
		sendCommand(msg);

	}

	private static void cancelInputOrTrans() {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.CANCEL_TRANS_OR_INPUT);
		byte[] data = {};
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void clearEventQueue() {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.CLEAR_EVENT_QUEUE_8800);
		byte[] data = {};
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void stopCustomDisplay() {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.STOP_CUSTOM_DISPLAY_8800);
		byte[] data = {};
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void startCustomDisplay() {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.START_CUSTOM_DISPLAY_8800);
		byte[] data = {};
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void setColors(ColorScheme colors) {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.SET_COLORS_8800);
		byte[] data = colors.selectedColor;
		msg.setDataBlock(data);
		sendCommand(msg);
	}

	private static void activateTransaction() {
		VivoTechCommandPacket msg = new VivoTechCommandPacket();
		msg.setVivoTechCommand(VivoTechCommand.ACTIVATE_TRANSACTION);
		byte[] data = {90};
		msg.setDataBlock(data);
		sendCommand(msg);
	}

}
