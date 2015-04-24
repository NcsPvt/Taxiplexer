package itcurves.ncs.taximeter.messages;

public class CreditCardDataMessage extends MeterMessage {

	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public CreditCardDataMessage() {
		super();
		messageId = MessageId.SEND_CREDIT_CARD_DATA;
	}

	public CreditCardDataMessage(byte[] byteArray) throws InvalidMeterMessageException {
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
		return super.getLength() + ACCOUNT_NUMBER_SIZE + EXPIRY_DATE_SIZE + ADDITIONAL_DATA_SIZE + FARE_SIZE;
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
		sb.append("Account Number: ");
		sb.append(accountNumber);
		sb.append(", Additional Data: ");
		sb.append(additionalData);
		sb.append(", Fare: ");
		sb.append(fare);
		sb.append(")");

		return sb.toString();
	}

	/**
	 * @return Returns the account number.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return Returns the expiry Date in MMYY format.
	 */
	public String getExpiry() {
		return (expiryDate.substring(2) + expiryDate.substring(0, 2));
	}

	/**
	 * <p>
	 * <b>Service code (3 digits)</b> values common in financial cards:
	 * </p>
	 * <p>
	 * First digit
	 * </p>
	 * <dl>
	 * <dd>1: International interchange OK</dd>
	 * <dd>2: International interchange, use IC (chip) where feasible</dd>
	 * <dd>5: National interchange only except under bilateral agreement</dd>
	 * <dd>6: National interchange only except under bilateral agreement, use IC (chip) where feasible</dd>
	 * <dd>7: No interchange except under bilateral agreement (closed loop)</dd>
	 * <dd>9: Test</dd>
	 * </dl>
	 * <p>
	 * Second digit
	 * </p>
	 * <dl>
	 * <dd>0: Normal</dd>
	 * <dd>2: Contact issuer via online means</dd>
	 * <dd>4: Contact issuer via online means except under bilateral agreement</dd>
	 * </dl>
	 * <p>
	 * Third digit
	 * </p>
	 * <dl>
	 * <dd>0: No restrictions, PIN required</dd>
	 * <dd>1: No restrictions</dd>
	 * <dd>2: Goods and services only (no cash)</dd>
	 * <dd>3: ATM only, PIN required</dd>
	 * <dd>4: Cash only</dd>
	 * <dd>5: Goods and services only (no cash), PIN required</dd>
	 * <dd>6: No restrictions, use PIN where feasible</dd>
	 * <dd>7: Goods and services only (no cash), use PIN where feasible</dd>
	 * </dl>
	 * <p>
	 * All values not explicitly mentioned above are reserved for future use
	 * </p>
	 * 
	 * @return Returns the Service Code.
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @return Returns the Discretion data.
	 */
	public String getdiscretionData() {
		return discretionData;
	}

	/**
	 * @return Returns the additional data.
	 */
	public String getAdditionalData() {
		return additionalData;
	}

	/**
	 * @return Returns the total fare in ASCII.
	 */
	public String getFare() {
		return fare;
	}

	/**
	 * @return Returns the Track2 info.
	 *         <p>
	 *         <dd>< accountNumber = expiryDate serviceCode discretionData ></dd>
	 *         <dd>(16 digits) (4 digits) (3 digits) (8 digits)</dd>
	 *         </p>
	 */
	public String getTrackII() {

		return accountNumber + '=' + expiryDate + serviceCode + discretionData;
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

		accountNumber = new String(ByteArray.extractByteArray(byteArray, index, CreditCardDataMessage.ACCOUNT_NUMBER_SIZE));
		accountNumber = accountNumber.trim();
		index += CreditCardDataMessage.ACCOUNT_NUMBER_SIZE;

		expiryDate = new String(ByteArray.extractByteArray(byteArray, index, CreditCardDataMessage.EXPIRY_DATE_SIZE));
		index += CreditCardDataMessage.EXPIRY_DATE_SIZE;

		serviceCode = new String(ByteArray.extractByteArray(byteArray, index, CreditCardDataMessage.SERVICE_CODE_SIZE));
		serviceCode = serviceCode.trim();
		index += CreditCardDataMessage.SERVICE_CODE_SIZE;

		discretionData = new String(ByteArray.extractByteArray(byteArray, index, CreditCardDataMessage.DISCRETION_DATA_SIZE));
		discretionData = discretionData.trim();
		index += CreditCardDataMessage.DISCRETION_DATA_SIZE;

		additionalData = new String(ByteArray.extractByteArray(byteArray, index, CreditCardDataMessage.ADDITIONAL_DATA_SIZE));
		additionalData = additionalData.trim();
		index += CreditCardDataMessage.ADDITIONAL_DATA_SIZE;

		fare = new String(ByteArray.extractByteArray(byteArray, index, CreditCardDataMessage.FARE_SIZE));
		fare = fare.trim();
		fare = Double.toString(Double.valueOf(fare) / 100);
		index += CreditCardDataMessage.FARE_SIZE;
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private String accountNumber;
	private String expiryDate;
	private String serviceCode;
	private String discretionData;
	private String additionalData;
	private String fare;

	private static final int ACCOUNT_NUMBER_SIZE = 19;
	private static final int EXPIRY_DATE_SIZE = 4;
	private static final int SERVICE_CODE_SIZE = 3;
	private static final int DISCRETION_DATA_SIZE = 13;
	private static final int ADDITIONAL_DATA_SIZE = 13;
	private static final int FARE_SIZE = 8;

	public static final byte METER_OFF = '0';
	public static final char METER_ON = '1';
	public static final byte METER_TIME_OFF = '2';
	public static final byte METER_HIRED_TIME_OFF_PAYMENT = '3';

}
