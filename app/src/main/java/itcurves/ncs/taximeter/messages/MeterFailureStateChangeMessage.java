package itcurves.ncs.taximeter.messages;

import android.util.Log;

public class MeterFailureStateChangeMessage extends MeterMessage {
	
	/*
	|--------------------------------------------------------------------------|
	| Contructors                                                              |
	|--------------------------------------------------------------------------|
	*/

	public MeterFailureStateChangeMessage()
	{
		super();
		messageId = MessageId.METER_ON_OFF_STATE_CHANGE;
	}
	
	public MeterFailureStateChangeMessage(char newStateIn)
	{
		this();
		state = newStateIn;
	}

	public MeterFailureStateChangeMessage(byte[] byteArray) throws InvalidMeterMessageException
	{
		super(byteArray);
	}

	/*
	|--------------------------------------------------------------------------|
	| Public Methods (interface)                                               |
	|--------------------------------------------------------------------------|
	*/

	/**
	 * Return the overall SMM length.
	 */
	public int getLength()
	{
		return super.getLength() + STATE_SIZE;
	}

	/**
	 * Convert the fields into a byte array.
	 */
	public byte[] toByteArray()
	{
		byte[] b = super.toByteArray();

		int index = getMessageBodyStart();
		
		b[index] = (byte) state;
		index += STATE_SIZE;
		
		int bcc = super.calculateBlockCharacterChecksum(b);
		ByteArray.insertInt(b, index, MeterMessage.BLOCK_CHECKSUM_CHARACTER_LENGTH, bcc);

		return b;
	}

	public String toString()
	{
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
	public char getState()
	{
		return state;
	}

	/*
	|--------------------------------------------------------------------------|
	| Protected Methods                                                        |
	|--------------------------------------------------------------------------|
	*/

	protected void parseMessage(byte[] byteArray) throws InvalidMeterMessageException
	{
		super.parseMessage(byteArray);

		int index = getMessageBodyStart();

		state = (char) byteArray[index];
		index += STATE_SIZE;
		
		Log.i(getClass().getSimpleName(), "Parsed new state: " + state);

	}

	/*
	|--------------------------------------------------------------------------|
	| Attributes/Fields                                                        |
	|--------------------------------------------------------------------------|
	*/

	private char state;


	private static final int STATE_SIZE = 1;

	public static final byte METER_NO_FAILURE = '0';
	public static final char METER_FAILURE = '1';

}
