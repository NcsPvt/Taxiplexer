package itcurves.ncs.bluebamboo;

public class BlueBambooStates {

	public static final byte NO_ERROR = 0x00;
	public static final byte NO_PAPER = 0x01;
	public static final byte HEAD_TEMP = 0x04;
	public static final byte LOW_BATTERY = 0x08;
	public static final byte NO_RESPONSE = 0x0A;

	private byte state = NO_ERROR;

	public String getDesc(int msgNumber) {
		switch (msgNumber) {
			case NO_ERROR :
				return "PRINTER OK";
			case NO_PAPER :
				return "NO PAPER or COVER OPEN ON PRINTER";
			case HEAD_TEMP :
				return "HEAD TEMPERATURE TOO HIGH ON PRINTER";
			case LOW_BATTERY :
				return "LOW BATTERY ON PRINTER";
			case NO_RESPONSE :
				return "NO REPONSE FROM PRINTER";
			default :
				return "Unknown Msg Type";
		}

	}

	/**
	 * @return the state
	 */
	public byte getState() {
		return state;
	}

	/**
	 * @param newState the state to set
	 */
	public void setState(byte newState) {
		this.state = newState;
	}
}
