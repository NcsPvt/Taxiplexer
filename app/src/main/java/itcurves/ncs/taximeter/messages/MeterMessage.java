package itcurves.ncs.taximeter.messages;

import android.util.Log;

public abstract class MeterMessage {

	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	protected MeterMessage() {
		// Do something interesting in the constructor
	}

	/**
	 * Create a SMM based on a passed in byte array. Used to parse
	 * an incoming message to an SMM.
	 * 
	 * @param byteArray
	 */
	protected MeterMessage(byte[] byteArray) throws InvalidMeterMessageException {
		this();
		parseMessage(byteArray);
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

	public MessageId getMessageId() {
		return messageId;
	}

	public int getDataBlockLength() {
		return dataBlockLength;
	}

	public String getName() {
		return messageId.toString();
	}

	/**
	 * Calculate the total length of the MM.
	 * Each subclass must override this to add its length.
	 */
	public int getLength() {
		int headerLength = (START_OF_TRANSMISSION_LENGTH + MESSAGEID_SIZE + MESSAGELENGTH_SIZE + BLOCK_CHECKSUM_CHARACTER_LENGTH + END_OF_TRANSMISSION_LENGTH);
		return headerLength;
	}

	/**
	 * Calculate the length of the message body.
	 * That is the length that is inserted into the message
	 * length field of every MM.
	 */
	public int getMessageLengthFieldValue() {
		int headerLength = (START_OF_TRANSMISSION_LENGTH + MESSAGEID_SIZE + MESSAGELENGTH_SIZE + BLOCK_CHECKSUM_CHARACTER_LENGTH + END_OF_TRANSMISSION_LENGTH);
		return getLength() - headerLength;
	}

	/**
	 * Convert all the data to a byte array.
	 * Each subclass must implement this method.
	 * 
	 * @return byte[] representing the message
	 */
	public byte[] toByteArray() {
		int totalLength = getLength();
		byte[] body = new byte[totalLength];

		int index = 0;

		ByteArray.insertInt(body, index, START_OF_TRANSMISSION_LENGTH, START_OF_TRANSMISSION);
		index += START_OF_TRANSMISSION_LENGTH;

		ByteArray.insertInt(body, index, MESSAGEID_SIZE, messageId.getValue());
		index += MESSAGEID_SIZE;

		ByteArray.insertInt(body, index, MESSAGELENGTH_SIZE, getMessageLengthFieldValue());
		index += MESSAGELENGTH_SIZE;

		ByteArray.insertInt(body, totalLength - 1, END_OF_TRANSMISSION_LENGTH, END_OF_TRANSMISSION);

		return body;
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Protected Methods |
	 * |--------------------------------------------------------------------------|
	 */

	/**
	 * Parse a byte array into data objects. Each child will override to
	 * parse its individual message body. The parent only parses the header.
	 * 
	 * @param msg
	 */
	protected void parseMessage(byte[] msg) throws InvalidMeterMessageException {
		int index = START_OF_TRANSMISSION_LENGTH;

		// Get the Message Id
		messageId = MessageId.getMessageId(msg[index]);
		index += MESSAGEID_SIZE;

		Log.i(getClass().getSimpleName(), "Message Id: " + messageId.toString());

		// Get the message length
		dataBlockLength = ByteArray.extractInt(msg, index, MESSAGELENGTH_SIZE);
		index += MESSAGELENGTH_SIZE;

		Log.i(getClass().getSimpleName(), "Data Block Length: " + dataBlockLength);

		// Validate the message length
		int headerLength = (START_OF_TRANSMISSION_LENGTH + MESSAGEID_SIZE + MESSAGELENGTH_SIZE + BLOCK_CHECKSUM_CHARACTER_LENGTH + END_OF_TRANSMISSION_LENGTH);
		if (dataBlockLength != msg.length - headerLength) {
			throw new InvalidMeterMessageException("Message length is invalid.  Expected: " + (dataBlockLength + headerLength) + " Actual: " + msg.length);
		}

		index += dataBlockLength;

		// Get the Block Checksum Character
		blockChecksumCharacter = ByteArray.extractInt(msg, index, BLOCK_CHECKSUM_CHARACTER_LENGTH);
		index += BLOCK_CHECKSUM_CHARACTER_LENGTH;

		Log.i(getClass().getSimpleName(), "Block Checksum Character: " + blockChecksumCharacter);

		// Verify the Block Checksum Character
		if (msg[2] >= 0) {
			byte[] bccData = ByteArray.extractByteArray(msg, 0, START_OF_TRANSMISSION_LENGTH + MESSAGEID_SIZE + MESSAGELENGTH_SIZE + dataBlockLength);

			Log.i(getClass().getSimpleName(), "Recieved Block Checksum Character Data: " + ByteArray.byteArrayToHexString(bccData));

			int bcc = 0;
			for (int i = 0; i < bccData.length; i++) {
				bcc = bcc ^ bccData[i];
			}

			Log.i(getClass().getSimpleName(), "Calculated Block Checksum Character Data: " + Integer.toHexString(bcc));

			if (bcc != blockChecksumCharacter) {
				throw new InvalidMeterMessageException("Block Checksum Character check failed..  Expected: " + blockChecksumCharacter + " Actual: " + bcc);
			}
		}
	}

	/**
	 * Return the start of the message body.
	 * Used by subclasses to build the byte array.
	 * 
	 * @return int - Message body start index
	 */
	protected int getMessageBodyStart() {
		return START_OF_TRANSMISSION_LENGTH + messageId.getLength() + MESSAGELENGTH_SIZE;
	}

	public static int calculateBlockCharacterChecksum(byte[] messageIn) {
		byte[] bccData = ByteArray.extractByteArray(messageIn, 0, (messageIn.length - BLOCK_CHECKSUM_CHARACTER_LENGTH - END_OF_TRANSMISSION_LENGTH));

		// Log.i(getClass().getSimpleName(), "Calculating - Block Checksum Character Data: " + ByteArray.byteArrayToHexString(bccData));

		int bcc = 0;
		for (int i = 0; i < bccData.length; i++) {
			bcc = (bcc ^ bccData[i]);
		}

		return bcc;

	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	// Message header attribute data
	protected MessageId messageId;
	protected int dataBlockLength;
	protected int blockChecksumCharacter;

	// Size definitions
	public static final int START_OF_TRANSMISSION_LENGTH = 1;
	public static final int END_OF_TRANSMISSION_LENGTH = 1;
	public static final int MESSAGEID_SIZE = 1;
	public static int MESSAGELENGTH_SIZE = 1;
	public static final int BLOCK_CHECKSUM_CHARACTER_LENGTH = 1;

	public static final int START_OF_TRANSMISSION = 0x02;
	public static final int END_OF_TRANSMISSION = 0x03;

}
