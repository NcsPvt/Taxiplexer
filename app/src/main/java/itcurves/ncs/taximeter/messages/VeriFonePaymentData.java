package itcurves.ncs.taximeter.messages;

public class VeriFonePaymentData extends MeterMessage {
	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public VeriFonePaymentData() {
		super();

	}

	/*
	 * public CreditCardDataMessage(String accountNumberIn, String expiryDateIn, String additionalDataIn, String fareIn)
	 * {
	 * this();
	 * accountNumberString = accountNumberIn;
	 * expiryDateString = expiryDateIn;
	 * additionalDataString = additionalDataIn;
	 * fareString = fareIn;
	 * }
	 */

	public VeriFonePaymentData(byte[] byteArray, String Id) throws InvalidMeterMessageException {
		super(byteArray);
		if (Id.equalsIgnoreCase("3"))
			messageId = MessageId.VERIFONE_CASH_DATA;
		else if (Id.equalsIgnoreCase("4"))
			messageId = MessageId.VERIFONE_CREDIT_CARD_DATA;
		else if (Id.equalsIgnoreCase("7"))
			messageId = MessageId.VERIFONE_CMD1_ACK_DATA;
		else if (Id.equalsIgnoreCase("8"))
			messageId = MessageId.VERIFONE_CMD8_NACK_DATA;
		else if (Id.equalsIgnoreCase("9"))
			messageId = MessageId.VERIFONE_CMD2_ACK_DATA;
		else if (Id.equalsIgnoreCase("10"))
			messageId = MessageId.VERIFONE_CMD10_DATA;
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

		return super.getLength();
	}

	/**
	 * Convert the fields into a byte array.
	 */
	@Override
	public byte[] toByteArray() {
		return null;
		/*
		 * byte[] b = super.toByteArray();
		 * 
		 * int index = getMessageBodyStart();
		 * 
		 * b[index] = (byte) state;
		 * index += STATE_SIZE;
		 * 
		 * int bcc = super.calculateBlockCharacterChecksum(b);
		 * ByteArray.insertInt(b, index, MeterMessage.BLOCK_CHECKSUM_CHARACTER_LENGTH, bcc);
		 * 
		 * return b;
		 */
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[" + getClass().getName() + "] (");
		sb.append(" Data: ");
		sb.append(DataString);
		sb.append(" )");

		return sb.toString();
	}

	public String getData() {
		return DataString;
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

		DataString = new String(ByteArray.extractByteArray(byteArray, index, (byteArray.length - index)));

	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private String DataString;

}
