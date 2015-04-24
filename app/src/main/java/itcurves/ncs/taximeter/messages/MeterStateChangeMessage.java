package itcurves.ncs.taximeter.messages;

import android.util.Log;

public class MeterStateChangeMessage extends MeterMessage {

	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public MeterStateChangeMessage(byte[] byteArray) throws InvalidMeterMessageException {
		super(byteArray);
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

	/**
	 * Return the overall SMM length.
	 */
	@Override
	public int getLength() {
		return super.getLength() + STATE_SIZE;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[" + getClass().getName() + "] (");
		sb.append("State: ");
		sb.append(state);
		sb.append(")");

		return sb.toString();
	}

	/**
	 * @return Returns the type.
	 */
	public char getState() {
		return state;
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Protected Methods |
	 * |--------------------------------------------------------------------------|
	 */

	@Override
	protected void parseMessage(byte[] byteArray) throws InvalidMeterMessageException {
		super.parseMessage(byteArray);

		int index = getMessageBodyStart();

		state = (char) byteArray[index];
		index += STATE_SIZE;

		Log.i(getClass().getSimpleName(), "Parsed new state: " + state);

	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private char state;

	private static final int STATE_SIZE = 1;

	public static final byte METER_OFF = '0';
	public static final char METER_ON = '1';
	public static final byte METER_TIME_OFF = '2';
	public static final byte METER_HIRED_TIME_OFF_PAYMENT = '3';

}
