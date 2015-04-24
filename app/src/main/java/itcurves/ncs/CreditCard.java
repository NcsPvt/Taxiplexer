package itcurves.ncs;

public final class CreditCard {

	public CreditCard() {
		cardType = null;
		cardHolderName = "";
		cardNumber = "";
		expirationDate = "";
		track1Data = "";
		track2Data = "";
	}

	public CreditCardType getCardType() {
		return cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) throws IllegalArgumentException {
		if (cardNumber == null) {
			this.cardNumber = "";
		} else {
			if (cardNumber.length() < 13 || cardNumber.length() > 16)
				throw new IllegalArgumentException("Card number length is invalid.");
			cardType = CreditCardType.determineCreditCardType(cardNumber);
			this.cardNumber = cardNumber;
		}
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) throws IllegalArgumentException {
		if (expirationDate == null || expirationDate.length() == 0) {
			this.expirationDate = "";
		} else {
			if (expirationDate.length() != 4)
				throw new IllegalArgumentException("Expiration Date must have a length of 4");
			this.expirationDate = expirationDate;
		}
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) throws IllegalArgumentException {
		if (cardHolderName == null) {
			this.cardHolderName = "";
		} else {
			if (cardHolderName.length() > 26)
				throw new IllegalArgumentException("Card holder name must have a length of 26 or less.");
			this.cardHolderName = cardHolderName;
		}
	}

	String getTrack1Data() {
		return track1Data;
	}

	public void setTrack1Data(String track1Data) throws IllegalArgumentException {
		if (track1Data == null) {
			this.track1Data = "";
		} else {
			if (track1Data.length() > 76)
				throw new IllegalArgumentException("Track 1 Data cannot have a length greater than 76.");
			this.track1Data = track1Data;
		}
	}

	String getTrack2Data() {
		return track2Data;
	}

	public void setTrack2Data(String track2Data) throws IllegalArgumentException {
		if (track2Data == null) {
			this.track2Data = "";
		} else {
			if (track2Data.length() > 40)
				throw new IllegalArgumentException("Track 2 Data cannot have a length greater than 40.");
			this.track2Data = track2Data;
		}
	}

	void sanitize() {
		if (track1Data.length() > 0 || track2Data.length() > 0)
			try {
				track1Data = "";
				track2Data = "";
				cardHolderName = "";
			} catch (Exception exception) {
			}
	}

	private CreditCardType cardType;
	private String cardNumber;
	private String expirationDate;
	private String track1Data;
	private String track2Data;
	private String cardHolderName;
	private static final String EMPTY_STRING = "";
	private static final int TRACK_1_DATA_LENGTH = 76;
	private static final int TRACK_2_DATA_LENGTH = 40;
}
