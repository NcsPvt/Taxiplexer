package itcurves.ncs.creditcard.mjm;

import java.util.concurrent.ExecutionException;

public class MJM_GiftCardRequest {

	private String TransDate = "";
	private String TransTime = "";
	private String SourceID = "";
	private String CardNumber = "";
	private String ExpDate = "1299";
	private String RequestType = "";
	private String Amount = "";
	private String DriverID = "";
	private String VehicleID = "";
	private String GPS = "";
	private String Address = "";
	private String TripID = "";
	private String Passengers = "";
	private String TrackInfo = "";// 3950011200000001=12127010000000000000

	/**
	 * @param TransactionDate DDMMYY
	 */
	public void setTransDate(String transactionDate) {
		this.TransDate = transactionDate;
	}

	/**
	 * @param transactionTime HHMMSS
	 */
	public void setTransTime(String transactionTime) {
		this.TransTime = transactionTime;
	}

	/**
	 * @param companyID Cab Company ID provided by MJM
	 */
	public void setSourceID(String companyID) {
		this.SourceID = companyID;
	}

	public void setCardNumber(String cardNum) {
		this.CardNumber = cardNum;
	}

	/**
	 * @param expiry MMYY
	 */
	public void setCardExpiry(String expiry) throws IllegalArgumentException {
		if (Integer.parseInt(expiry.substring(0, 2)) < 13)
			this.ExpDate = expiry;
		else
			throw new IllegalArgumentException("Expiry Date Should be in MMYY format");
	}
	/**
	 * @param requestType Valid values include: 1 (Balance Inquiry), 2 (Purchase)
	 */
	public void setRequestType(String requestType) throws IllegalArgumentException {
		if (requestType.matches("1") || requestType.matches("2"))
			this.RequestType = requestType;
		else
			throw new IllegalArgumentException("Valid Request Types are 1 and 2");
	}

	/**
	 * @param fare ###.##
	 */
	public void setAmount(String fare) {
		this.Amount = fare;
	}

	public void setDriverID(String prams) {
		this.DriverID = prams;
	}

	public void setVehicleID(String prams) {
		this.VehicleID = prams;
	}

	public void setGPS(String prams) {
		this.GPS = prams;
	}

	public void setAddress(String prams) {
		this.Address = prams;
	}

	public void setTripID(String prams) {
		this.TripID = prams;
	}

	public void setPassengers(String prams) {
		this.Passengers = prams;
	}

	/**
	 * @param trackII <p>
	 *        sample is 3950011000000092=12127010000000000000
	 *        <p>
	 *        Maximum Length = 80
	 */
	public void setTrackInfo(String trackII) {
		this.TrackInfo = trackII;
	}

	public MJM_GiftCardResponse sendRequest(String url) throws InterruptedException, ExecutionException {
		return new CallingMJM_GiftCard().execute(url, this.to_XML()).get();
	}

	/**
	 * @returns Returns Object data in XML format
	 */
	public String to_XML() {
		//@formatter:off
		String xmlRequest = new StringBuilder("<PaymentRequest>")
		.append("<TransDate>").append(TransDate).append("</TransDate>")
		.append("<TransTime>").append(TransTime).append("</TransTime>")
		.append("<SourceID>").append(SourceID).append("</SourceID>")
		.append("<CardNumber>").append(CardNumber).append("</CardNumber>")
		.append("<ExpDate>").append(ExpDate).append("</ExpDate>")
		.append("<RequestType>").append(RequestType).append("</RequestType>")
		.append("<Amount>").append(Amount).append("</Amount>")
		.append("<DriverID>").append(DriverID).append("</DriverID>")
		.append("<VehicleID>").append(VehicleID).append("</VehicleID>")
		.append("<GPS>").append(GPS).append("</GPS>")
		.append("<Address>").append(Address).append("</Address>")
		.append("<TripID>").append(TripID).append("</TripID>")
		.append("<Passengers>").append(Passengers).append("</Passengers>")
		.append("<TrackInfo>").append(TrackInfo).append("</TrackInfo>")    // 3950011000000092=12127010000000000000
		.append("</PaymentRequest>")
		.toString();
		//@formatter:on
		return xmlRequest;
	}
}
