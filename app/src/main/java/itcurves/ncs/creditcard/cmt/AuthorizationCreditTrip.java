package itcurves.ncs.creditcard.cmt;

import org.ksoap2.serialization.SoapObject;

public class AuthorizationCreditTrip {

	private String _requestId;
	private String _deviceId;
	private String _userId;
	private String _jobId;
	private String _paymentAmt;
	private String _fareAmt;
	private String _tipAmt;
	private String _tollAmt;
	private String _surchargeAmt;
	private String _taxAmt;
	private String _convenienceFeeAmt;
	private String _accountNumber;
	private String _expiryDate;
	private String _swipedData;
	private String _zipCode;
	private String _cvv2;
	private String _encryptionKeyVersion;
	private String _encryptedToken;
	private String _encryptionAlgorithm;
	private String _pickupDate;
	private String _pickupLatitude;
	private String _pickupLongitude;
	private String _dropoffDate;
	private String _dropoffLatitude;
	private String _dropoffLongitude;
	private String _passengerCount;
	private String _tripDistance;
	private String _tripDuration;
	private String _readyToSettle;
	private String _cardReadMethod;

	public AuthorizationCreditTrip() {
		_requestId = "";
		_deviceId = "";
		_userId = "";
		_jobId = "";
		_paymentAmt = "";
		_fareAmt = "";
		_tipAmt = "";
		_tollAmt = "";
		_surchargeAmt = "";
		_taxAmt = "";
		_convenienceFeeAmt = "";
		_accountNumber = "";
		_expiryDate = "";
		set_cardReadMethod("1");
		set_swipedData("");
		_zipCode = "";
		_cvv2 = "";
		_encryptionKeyVersion = "";
		_encryptedToken = "";
		_encryptionAlgorithm = "";
		_pickupDate = "";
		_pickupLatitude = "";
		_pickupLongitude = "";
		_dropoffDate = "";
		_dropoffLatitude = "";
		_dropoffLongitude = "";
		_passengerCount = "";
		_tripDistance = "";
		_tripDuration = "";
		_readyToSettle = "";

	}

	public void set_requestId(String _requestId) {
		this._requestId = _requestId;
	}
	public String get_requestId() {
		return _requestId;
	}
	public void set_deviceId(String _deviceId) {
		this._deviceId = _deviceId;
	}
	public String get_deviceId() {
		return _deviceId;
	}
	public void set_userId(String _userId) {
		this._userId = _userId;
	}
	public String get_userId() {
		return _userId;
	}
	public void set_jobId(String _jobId) {
		this._jobId = _jobId;
	}
	public String get_jobId() {
		return _jobId;
	}
	public void set_paymentAmt(String _paymentAmt) {
		this._paymentAmt = _paymentAmt;
	}
	public String get_paymentAmt() {
		return _paymentAmt;
	}
	public void set_fareAmt(String _fareAmt) {
		this._fareAmt = _fareAmt;
	}
	public String get_fareAmt() {
		return _fareAmt;
	}
	public void set_tipAmt(String _tipAmt) {
		this._tipAmt = _tipAmt;
	}
	public String get_tipAmt() {
		return _tipAmt;
	}
	public void set_tollAmt(String _tollAmt) {
		this._tollAmt = _tollAmt;
	}
	public String get_tollAmt() {
		return _tollAmt;
	}
	public void set_surchargeAmt(String _surchargeAmt) {
		this._surchargeAmt = _surchargeAmt;
	}
	public String get_surchargeAmt() {
		return _surchargeAmt;
	}
	public void set_taxAmt(String _taxAmt) {
		this._taxAmt = _taxAmt;
	}
	public String get_taxAmt() {
		return _taxAmt;
	}
	public void set_convenienceFeeAmt(String _convenienceFeeAmt) {
		this._convenienceFeeAmt = _convenienceFeeAmt;
	}
	public String get_convenienceFeeAmt() {
		return _convenienceFeeAmt;
	}
	public void set_accountNumber(String _accountNumber) {
		this._accountNumber = _accountNumber;
	}
	public String get_accountNumber() {
		return _accountNumber;
	}
	public void set_expiryDate(String _expiryDate) {
		this._expiryDate = _expiryDate;
	}
	public String get_expiryDate() {
		return _expiryDate;
	}
	public void set_zipCode(String _zipCode) {
		this._zipCode = _zipCode;
	}
	public String get_zipCode() {
		return _zipCode;
	}
	public void set_cvv2(String _cvv2) {
		this._cvv2 = _cvv2;
	}
	public String get_cvv2() {
		return _cvv2;
	}
	public void set_encryptionKeyVersion(String _encryptionKeyVersion) {
		this._encryptionKeyVersion = _encryptionKeyVersion;
	}
	public String get_encryptionKeyVersion() {
		return _encryptionKeyVersion;
	}
	public void set_encryptedToken(String _encryptedToken) {
		this._encryptedToken = _encryptedToken;
	}
	public String get_encryptedToken() {
		return _encryptedToken;
	}
	public void set_encryptionAlgorithm(String _encryptionAlgorithm) {
		this._encryptionAlgorithm = _encryptionAlgorithm;
	}
	public String get_encryptionAlgorithm() {
		return _encryptionAlgorithm;
	}
	public void set_pickupDate(String _pickupDate) {
		this._pickupDate = _pickupDate;
	}
	public String get_pickupDate() {
		return _pickupDate;
	}
	public void set_pickupLatitude(String _pickupLatitude) {
		this._pickupLatitude = _pickupLatitude;
	}
	public String get_pickupLatitude() {
		return _pickupLatitude;
	}
	public void set_pickupLongitude(String _pickupLongitude) {
		this._pickupLongitude = _pickupLongitude;
	}
	public String get_pickupLongitude() {
		return _pickupLongitude;
	}
	public void set_dropoffDate(String _dropoffDate) {
		this._dropoffDate = _dropoffDate;
	}
	public String get_dropoffDate() {
		return _dropoffDate;
	}
	public void set_dropoffLatitude(String _dropoffLatitude) {
		this._dropoffLatitude = _dropoffLatitude;
	}
	public String get_dropoffLatitude() {
		return _dropoffLatitude;
	}
	public void set_dropoffLongitude(String _dropoffLongitude) {
		this._dropoffLongitude = _dropoffLongitude;
	}
	public String get_dropoffLongitude() {
		return _dropoffLongitude;
	}
	public void set_passengerCount(String _passengerCount) {
		this._passengerCount = _passengerCount;
	}
	public String get_passengerCount() {
		return _passengerCount;
	}
	public void set_tripDistance(String _tripDistance) {
		this._tripDistance = _tripDistance;
	}
	public String get_tripDistance() {
		return _tripDistance;
	}
	public void set_tripDuration(String _tripDuration) {
		this._tripDuration = _tripDuration;
	}
	public String get_tripDuration() {
		return _tripDuration;
	}
	public void set_readyToSettle(String _readyToSettle) {
		this._readyToSettle = _readyToSettle;
	}
	public String get_readyToSettle() {
		return _readyToSettle;
	}

	public void set_swipedData(String _swipedData) {
		this._swipedData = _swipedData;
		this.set_cardReadMethod("0");
	}

	public String get_swipedData() {
		return _swipedData;
	}

	public SoapObject CreateAuthorizationCreditTrip(String NAMESPACE, String METHOD_NAME) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// add AuthorizeCreditTrip Tags
		if (_requestId != "") {
			request.addProperty("requestId", _requestId);
		}

		if (_deviceId != "") {
			request.addProperty("deviceId", _deviceId);
		}

		if (_userId != "") {
			request.addProperty("userId", _userId);
		}

		if (_jobId != "") {
			request.addProperty("jobId", _jobId);
		}

		if (_paymentAmt != "") {
			request.addProperty("paymentAmt", _paymentAmt);
		}

		if (_fareAmt != "") {
			request.addProperty("fareAmt", _fareAmt);
		}

		if (_tipAmt != "") {
			request.addProperty("tipAmt", _tipAmt);
		}

		if (_tollAmt != "") {
			request.addProperty("tollAmt", _tollAmt);
		}

		if (_surchargeAmt != "") {
			request.addProperty("surchargeAmt", _surchargeAmt);
		}

		if (_taxAmt != "") {
			request.addProperty("taxAmt", _taxAmt);
		}

		if (_convenienceFeeAmt != "") {
			request.addProperty("convenienceFeeAmt", _convenienceFeeAmt);
		}

		if (_swipedData != "") {
			request.addProperty("swipeData", _swipedData);
		}

		if (_accountNumber != "") {
			request.addProperty("accountNumber", _accountNumber);
		}

		if (_expiryDate != "") {
			request.addProperty("expiryDate", _expiryDate);
		}

		if (_zipCode != "") {
			request.addProperty("zipCode", _zipCode);
		}

		if (_cvv2 != "") {
			request.addProperty("cvv2", _cvv2);
		}

		request.addProperty("cardReaderMethod", _cardReadMethod);

		if (_encryptionKeyVersion != "") {
			request.addProperty("encryptionKeyVersion", _encryptionKeyVersion);
		}

		if (_encryptedToken != "") {
			request.addProperty("encryptedToken", _encryptedToken);
		}

		if (_encryptionAlgorithm != "") {
			request.addProperty("encryptionAlgorithm", _encryptionAlgorithm);
		}

		if (_pickupDate != "") {
			request.addProperty("pickupDate", _pickupDate);
		}

		if (_pickupLatitude != "") {
			request.addProperty("pickupLatitude", _pickupLatitude);
		}

		if (_pickupLongitude != "") {
			request.addProperty("pickupLongitude", _pickupLongitude);
		}

		if (_dropoffDate != "") {
			request.addProperty("dropoffDate", _dropoffDate);
		}

		if (_dropoffLatitude != "") {
			request.addProperty("dropoffLatitude", _dropoffLatitude);
		}

		if (_dropoffLongitude != "") {
			request.addProperty("dropoffLongitude", _dropoffLongitude);
		}

		if (_passengerCount != "") {
			request.addProperty("passengerCount", _passengerCount);
		}

		if (_tripDistance != "") {
			request.addProperty("tripDistance", _tripDistance);
		}

		if (_tripDuration != "") {
			request.addProperty("tripDuration", _tripDuration);
		}

		if (_readyToSettle != "") {
			request.addProperty("readyToSettle", _readyToSettle);
		}

		return request;
	}

	/**
	 * @param _cardReadMethod the _cardReadMethod to set
	 * 
	 *        Method of card reader used(Required Field) :
	 * 
	 *        0: Swipe Reader
	 *        1: RFID Tap
	 * 
	 */
	public void set_cardReadMethod(String _cardReadMethod) {
		this._cardReadMethod = _cardReadMethod;
	}

}
