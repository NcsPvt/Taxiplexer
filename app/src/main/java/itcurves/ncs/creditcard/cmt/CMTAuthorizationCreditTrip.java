package itcurves.ncs.creditcard.cmt;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class CMTAuthorizationCreditTrip {

	// Header Members
	private String _username;
	private String _password;
	private String _dataSource;

	// Request Members
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
	private String _swipeData;
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

	// Response Members
	private String _ErrorMessage;
	private String _RequestEndTime;
	private String _RequestStartTime;
	private String _ResultCode;
	private String _RequestId;
	private String _ResponseType;
	private String _CardType;
	private String _AuthorizationDate;
	private String _TransactionId;
	private String _AuthorizationCode;
	private String _PaymentAmt;
	private String _DeclineReason;
	private String _JobId;
	private String _cardReadMethod;

	private final String URL;
	public CMTAuthorizationCreditTrip(String url) {
		URL = url;
	}

	public void SendRequest() throws KeyManagementException, NoSuchAlgorithmException, IOException, XmlPullParserException {

		String SOAP_ACTION = "http://services.cmtnyc.com/payment/AuthorizeCreditTrip";
		String METHOD_NAME = "AuthorizeCreditTrip";
		String NAMESPACE = "http://services.cmtnyc.com/payment";
		// String URL = AVL_Service.pref.getString("CCServerIP", "https://services.cmtnyc.com/payment/Payment.asmx");
		// "https://services.cmtnyc.com/payment/Payment.asmx";// "http://services-staging.cmtnyc.com/payment/Payment.asmx"

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

		// ///// FOR CREATING AUTHENTICATION HEADER ////////////////////////////////////////////////
		AuthenticationHeader authheader = new AuthenticationHeader();
		authheader.set_username(this._username);
		authheader.set_password(this._password);
		authheader.set_dataSource(this._dataSource);

		envelope.headerOut = authheader.CreateAuthenticationHeader(NAMESPACE);

		// /////////////////////////////////////////////////////////////////////////////////////////

		// ////////// FOR CREATING AUTHORIZATIONCREDIT_TRIP_KEYED REQUEST OBJECT ///////////////////
		AuthorizationCreditTrip authCreditTripK = new AuthorizationCreditTrip();
		authCreditTripK.set_requestId(_requestId);
		authCreditTripK.set_deviceId(_deviceId);
		authCreditTripK.set_userId(_userId);
		authCreditTripK.set_jobId(_jobId);
		authCreditTripK.set_paymentAmt(_paymentAmt);
		authCreditTripK.set_fareAmt(this._fareAmt);
		authCreditTripK.set_tipAmt(this._tipAmt);
		authCreditTripK.set_tollAmt(this._tollAmt);
		authCreditTripK.set_surchargeAmt(this._surchargeAmt);
		authCreditTripK.set_taxAmt(this._taxAmt);
		authCreditTripK.set_convenienceFeeAmt(this._convenienceFeeAmt);
		authCreditTripK.set_accountNumber(this._accountNumber);
		authCreditTripK.set_expiryDate(this._expiryDate);
		authCreditTripK.set_swipedData(this._swipeData);
		authCreditTripK.set_encryptionKeyVersion(this._encryptionKeyVersion);
		authCreditTripK.set_encryptedToken(this._encryptedToken);
		authCreditTripK.set_encryptionAlgorithm(this._encryptionAlgorithm);
		authCreditTripK.set_pickupDate(this._pickupDate);
		authCreditTripK.set_pickupLatitude(this._pickupLatitude);
		authCreditTripK.set_pickupLongitude(this._pickupLongitude);
		authCreditTripK.set_dropoffDate(this._dropoffDate);
		authCreditTripK.set_dropoffLatitude(this._dropoffLatitude);
		authCreditTripK.set_dropoffLongitude(this._dropoffLongitude);
		authCreditTripK.set_readyToSettle(this._readyToSettle);
		authCreditTripK.set_tripDistance(this._tripDistance);
		authCreditTripK.set_tripDuration(this._tripDuration);
		authCreditTripK.set_passengerCount(this._passengerCount);
		authCreditTripK.set_zipCode(this._zipCode);
		authCreditTripK.set_cvv2(this._cvv2);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(authCreditTripK.CreateAuthorizationCreditTrip(NAMESPACE, METHOD_NAME));

		trustEveryone();

		// HttpTransportSE
		// Web method call
		HttpTransportSE ksoapHttpTransport = new HttpTransportSE(URL);

		ksoapHttpTransport.debug = true;
		ksoapHttpTransport.call(SOAP_ACTION, envelope);

		// get the response
		AuthorizeCreditTripResponse authResponse = new AuthorizeCreditTripResponse();
		authResponse.ParseAuthorizeCreditTripResponse(envelope);

		_ErrorMessage = authResponse.get_ErrorMessage();
		_RequestEndTime = authResponse.get_RequestEndTime();
		_RequestStartTime = authResponse.get_RequestStartTime();
		_ResultCode = authResponse.get_ResultCode();
		_RequestId = authResponse.get_RequestId();
		_ResponseType = authResponse.get_ResponseType();
		_CardType = authResponse.get_CardType();
		_AuthorizationDate = authResponse.get_AuthorizationDate();
		_TransactionId = authResponse.get_TransactionId();
		_AuthorizationCode = authResponse.get_AuthorizationCode();
		_PaymentAmt = authResponse.get_PaymentAmt();
		_DeclineReason = authResponse.get_DeclineReason();
		_JobId = authResponse.get_JobId();

	}

	private void trustEveryone() throws NoSuchAlgorithmException, KeyManagementException {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new X509TrustManager[]{new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		}}, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}

	public void set_requestId(String _requestId) {
		this._requestId = _requestId;
	}

	public void set_deviceId(String _deviceId) {
		this._deviceId = _deviceId;
	}

	public void set_userId(String _userId) {
		this._userId = _userId;
	}

	public void set_jobId(String _jobId) {
		this._jobId = _jobId;
	}

	public void set_paymentAmt(String _paymentAmt) {
		this._paymentAmt = _paymentAmt;
	}

	public void set_fareAmt(String _fareAmt) {
		this._fareAmt = _fareAmt;
	}

	public void set_tipAmt(String _tipAmt) {
		this._tipAmt = _tipAmt;
	}

	public void set_tollAmt(String _tollAmt) {
		this._tollAmt = _tollAmt;
	}

	public void set_surchargeAmt(String _surchargeAmt) {
		this._surchargeAmt = _surchargeAmt;
	}

	public void set_taxAmt(String _taxAmt) {
		this._taxAmt = _taxAmt;
	}

	public void set_convenienceFeeAmt(String _convenienceFeeAmt) {
		this._convenienceFeeAmt = _convenienceFeeAmt;
	}

	public void set_accountNumber(String _accountNumber) {
		this._accountNumber = _accountNumber;
	}

	public void set_expiryDate(String _expiryDate) {
		this._expiryDate = _expiryDate;
	}

	public void set_zipCode(String _zipCode) {
		this._zipCode = _zipCode;
	}

	public void set_cvv2(String _cvv2) {
		this._cvv2 = _cvv2;
	}

	public void set_encryptionKeyVersion(String _encryptionKeyVersion) {
		this._encryptionKeyVersion = _encryptionKeyVersion;
	}

	public void set_encryptedToken(String _encryptedToken) {
		this._encryptedToken = _encryptedToken;
	}

	public void set_encryptionAlgorithm(String _encryptionAlgorithm) {
		this._encryptionAlgorithm = _encryptionAlgorithm;
	}

	public void set_pickupDate(String _pickupDate) {
		this._pickupDate = _pickupDate;
	}

	public void set_pickupLatitude(String _pickupLatitude) {
		this._pickupLatitude = _pickupLatitude;
	}

	public void set_pickupLongitude(String _pickupLongitude) {
		this._pickupLongitude = _pickupLongitude;
	}

	public void set_dropoffDate(String _dropoffDate) {
		this._dropoffDate = _dropoffDate;
	}

	public void set_dropoffLatitude(String _dropoffLatitude) {
		this._dropoffLatitude = _dropoffLatitude;
	}

	public void set_dropoffLongitude(String _dropoffLongitude) {
		this._dropoffLongitude = _dropoffLongitude;
	}

	public void set_passengerCount(String _passengerCount) {
		this._passengerCount = _passengerCount;
	}

	public void set_tripDistance(String _tripDistance) {
		this._tripDistance = _tripDistance;
	}

	public void set_tripDuration(String _tripDuration) {
		this._tripDuration = _tripDuration;
	}

	public void set_readyToSettle(String _readyToSettle) {
		this._readyToSettle = _readyToSettle;
	}

	public String get_ErrorMessage() {
		return _ErrorMessage;
	}

	public String get_RequestEndTime() {
		return _RequestEndTime;
	}

	public String get_RequestStartTime() {
		return _RequestStartTime;
	}

	public String get_ResultCode() {
		return _ResultCode;
	}

	public String get_RequestId() {
		return _RequestId;
	}

	public String get_ResponseType() {
		return _ResponseType;
	}

	public String get_CardType() {
		return _CardType;
	}

	public String get_AuthorizationDate() {
		return _AuthorizationDate;
	}

	public String get_TransactionId() {
		return _TransactionId;
	}

	public String get_AuthorizationCode() {
		return _AuthorizationCode;
	}

	public String get_PaymentAmt() {
		return _PaymentAmt;
	}

	public String get_DeclineReason() {
		return _DeclineReason;
	}

	public String get_JobId() {
		return _JobId;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public void set_dataSource(String _dataSource) {
		this._dataSource = _dataSource;
	}

	public void set_swipeData(String _swipeData) {
		this._swipeData = _swipeData;
		this._cardReadMethod = "0";
	}

}
