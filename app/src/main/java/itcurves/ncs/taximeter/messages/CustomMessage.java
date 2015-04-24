package itcurves.ncs.taximeter.messages;

import java.io.UnsupportedEncodingException;

public class CustomMessage extends MeterMessage {

	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public CustomMessage() {
		super();
		messageId = MessageId.VERIFONE_CMD1_DATA;
	}

	public CustomMessage(String CustomText, String VMessageType) throws IllegalArgumentException {
		this();
		if (VMessageType.equalsIgnoreCase("1"))
			messageId = MessageId.VERIFONE_CMD1_DATA;
		if (VMessageType.equalsIgnoreCase("2"))
			messageId = MessageId.VERIFONE_PING_DATA;
		if (VMessageType.equalsIgnoreCase("5"))
			messageId = MessageId.VERIFONE_GPS_DATA;
		if (VMessageType.equalsIgnoreCase("6"))
			messageId = MessageId.VERIFONE_PAYMENT_ACK_DATA;
		if (VMessageType.equalsIgnoreCase("7"))
			messageId = MessageId.VERIFONE_CMD1_ACK_DATA;
		if (VMessageType.equalsIgnoreCase("11"))
			messageId = MessageId.VERIFONE_LogOff_DATA;

		// messageId = MessageId.PRINT_BLOCK;
		customMessage = CustomText;

	}

	public void SetText(String CustomText) {
		customMessage = CustomText;
	}

	public void SetDriverSnap(byte[] pDriversnap) {
		DriverSnap = pDriversnap;
	}

	public void SetID(String VMessageType) {
		if (VMessageType.equalsIgnoreCase("1"))
			messageId = MessageId.VERIFONE_CMD1_DATA;
		if (VMessageType.equalsIgnoreCase("2"))
			messageId = MessageId.VERIFONE_PING_DATA;
		if (VMessageType.equalsIgnoreCase("5"))
			messageId = MessageId.VERIFONE_GPS_DATA;
		if (VMessageType.equalsIgnoreCase("6"))
			messageId = MessageId.VERIFONE_PAYMENT_ACK_DATA;
		if (VMessageType.equalsIgnoreCase("7"))
			messageId = MessageId.VERIFONE_CMD1_ACK_DATA;
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

	/**
	 * Return the overall MM length.
	 */
	@Override
	public int getLength() {
		return super.getLength() + customMessage.getBytes().length;
	}

	/**
	 * Convert the fields into a byte array.
	 */
	@Override
	public byte[] toByteArray() {

		byte[] b = super.toByteArray();

		int index = getMessageBodyStart();

		byte[] textBytes = customMessage.getBytes();
		/* Driver snap change */

		/*
		 * int MessageArrayLength = customMessage.getBytes().length;
		 * byte[] textBytes = new byte[MessageArrayLength + DriverSnap.length];
		 * 
		 * System.arraycopy(customMessage.getBytes(), 0, textBytes, 0, MessageArrayLength);
		 * System.arraycopy(DriverSnap, 0, textBytes, MessageArrayLength, DriverSnap.length);
		 */

		/* Driver snap change */
		System.arraycopy(textBytes, 0, b, index, customMessage.length());
		index += textBytes.length;

		int bcc = super.calculateBlockCharacterChecksum(b);
		ByteArray.insertInt(b, index, MeterMessage.BLOCK_CHECKSUM_CHARACTER_LENGTH, bcc);
		String str = "";
		try {
			str = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String hex = bytesToHex(b);
		// b = hex.getBytes();
		return b;
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[" + getClass().getName() + "] (");
		sb.append("Text: ");
		sb.append(customMessage);
		sb.append(")");

		return sb.toString();
	}

	/**
	 * @return Returns the text to print.
	 */
	public String getTextToPrint() {
		return customMessage;
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

		customMessage = new String(ByteArray.extractByteArray(byteArray, index, getDataBlockLength()));
		customMessage = customMessage.trim();
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private String customMessage;
	public byte[] DriverSnap;

}
