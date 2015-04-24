package itcurves.ncs.taximeter.messages;

public class AcknowledgeCommandMessage extends MeterMessage {
	
	/*
	|--------------------------------------------------------------------------|
	| Contructors                                                              |
	|--------------------------------------------------------------------------|
	*/

	public AcknowledgeCommandMessage()
	{
		super();
		messageId = MessageId.ACKNOWLEDGE_COMMAND;
	}

	public AcknowledgeCommandMessage(byte[] byteArray) throws InvalidMeterMessageException
	{
		super(byteArray);
	}

	/*
	|--------------------------------------------------------------------------|
	| Public Methods (interface)                                               |
	|--------------------------------------------------------------------------|
	*/

	/**
	 * Return the overall MM length.
	 */
	public int getLength()
	{
		return super.getLength();
	}

	/**
	 * Convert the fields into a byte array.
	 */
	public byte[] toByteArray()
	{
		byte[] b = super.toByteArray();

		int index = getMessageBodyStart();
		
		int bcc = super.calculateBlockCharacterChecksum(b);
		ByteArray.insertInt(b, index, MeterMessage.BLOCK_CHECKSUM_CHARACTER_LENGTH, bcc);

		return b;
		
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[" + getClass().getName() + "] (");
		sb.append("Ack");
		sb.append(")");

		return sb.toString();
	}


	/*
	|--------------------------------------------------------------------------|
	| Protected Methods                                                        |
	|--------------------------------------------------------------------------|
	*/

	protected void parseMessage(byte[] byteArray) throws InvalidMeterMessageException
	{
		super.parseMessage(byteArray);
	}

	/*
	|--------------------------------------------------------------------------|
	| Attributes/Fields                                                        |
	|--------------------------------------------------------------------------|
	*/
}
