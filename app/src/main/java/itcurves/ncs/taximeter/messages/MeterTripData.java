package itcurves.ncs.taximeter.messages;

public class MeterTripData extends MeterMessage {
	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public MeterTripData() {
		super();
		messageId = MessageId.REPORT_METER_TRIP_DATA;
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

	public MeterTripData(byte[] byteArray) throws InvalidMeterMessageException {
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

		return super.getLength() + FARE_SIZE + TAX_SIZE + EXTRAS_SIZE + NET_TOTAL_SIZE + PAID_DISTACE_SIZE;
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
		sb.append(" Fare: ");
		sb.append(fareString);
		sb.append(", Tax: ");
		sb.append(taxString);
		sb.append(", Extras : ");
		sb.append(extrasString);
		sb.append(", NetTotal: ");
		sb.append(fareString);
		sb.append(", PaidDistance(1/10 km): ");
		sb.append(payDistanceString);
		sb.append(" )");

		return sb.toString();
	}

	/**
	 * @return Returns the tax amount.
	 */
	public String getTax() {

		return taxString;
	}

	/**
	 * @return Returns the extras value.
	 */
	public String getExtras() {

		return extrasString;
	}

	/**
	 * @return Returns the Net Total.
	 */
	public String getNetTotal() {

		return netTotalString;
	}

	/**
	 * @return Returns the fare in ASCII.
	 */
	public String getFare() {
		return fareString;
	}

	/**
	 * @return Returns the Paid Distance in ASCII.
	 */
	public String getPayDistance() {

		return payDistanceString;
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

		fareString = new String(ByteArray.extractByteArray(byteArray, index, MeterTripData.FARE_SIZE));
		fareString = fareString.trim();
		fareString = Double.toString(Double.valueOf(fareString) / 100);
		index += MeterTripData.FARE_SIZE;

		taxString = new String(ByteArray.extractByteArray(byteArray, index, MeterTripData.TAX_SIZE));
		taxString = taxString.trim();
		index += MeterTripData.TAX_SIZE;

		extrasString = new String(ByteArray.extractByteArray(byteArray, index, MeterTripData.EXTRAS_SIZE));
		extrasString = extrasString.trim();
		extrasString = Double.toString(Double.valueOf(extrasString) / 100);
		index += MeterTripData.EXTRAS_SIZE;

		netTotalString = new String(ByteArray.extractByteArray(byteArray, index, MeterTripData.NET_TOTAL_SIZE));
		netTotalString = netTotalString.trim();
		index += MeterTripData.NET_TOTAL_SIZE;
		payDistanceString = new String(ByteArray.extractByteArray(byteArray, index, MeterTripData.PAID_DISTACE_SIZE));
		payDistanceString = payDistanceString.trim();
		index += MeterTripData.PAID_DISTACE_SIZE;

	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private String fareString;
	private String taxString;
	private String extrasString;
	private String netTotalString;
	private String payDistanceString;

	private static final int NET_TOTAL_SIZE = 8;
	private static final int EXTRAS_SIZE = 4;
	private static final int FARE_SIZE = 8;
	private static final int TAX_SIZE = 8;
	private static final int PAID_DISTACE_SIZE = 8;

}
