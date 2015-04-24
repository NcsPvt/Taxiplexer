package itcurves.ncs.creditcard.cmt;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

public class CMTAdjustAuthorization {

	// header members
	private String _username;
	private String _password;
	private String _dataSource;

	// request members
	private String requestId;
	private String deviceId;
	private String userId;
	private String jobId;
	private String transactionId;
	private String authorizationCode;
	private String operationMode;
	private String paymentAmt;
	private String fareAmt;
	private String tipAmt;
	private String tollAmt;
	private String surchargeAmt;
	private String taxAmt;
	private String convenienceFeeAmt;

	// response members
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

	private final String URL;
	public CMTAdjustAuthorization(String url) {
		URL = url;
	}

	public void SendRequest() {
		try {
			String SOAP_ACTION = "http://services.cmtnyc.com/payment/AdjustAuthorization";
			String METHOD_NAME = "AdjustAuthorization";
			String NAMESPACE = "http://services.cmtnyc.com/payment";
			// String URL = AVL_Service.pref.getString("CCServerIP", "https://services.cmtnyc.com/payment/Payment.asmx");

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

			// ///// FOR CREATING AUTHENTICATION HEADER ////////////////////////////////////////////////
			AuthenticationHeader authheader = new AuthenticationHeader();
			authheader.set_username(this._username);
			authheader.set_password(this._password);
			authheader.set_dataSource(this._dataSource);

			envelope.headerOut = authheader.CreateAuthenticationHeader(NAMESPACE);

			// /////////////////////////////////////////////////////////////////////////////////////////

			// ////////// FOR CREATING ADJUST_AUTHORIZATION REQUEST OBJECT ///////////////////
			AdjustAuthorization adjAuth = new AdjustAuthorization();
			adjAuth.setRequestId(this.requestId);
			adjAuth.setDeviceId(this.deviceId);
			adjAuth.setUserId(this.userId);
			adjAuth.setJobId(this.jobId);
			adjAuth.setTransactionId(this.transactionId);
			adjAuth.setAuthorizationCode(this.authorizationCode);
			adjAuth.setOperationMode(this.operationMode);
			adjAuth.setPaymentAmt(this.paymentAmt);
			adjAuth.setFareAmt(this.fareAmt);
			adjAuth.setTipAmt(this.tipAmt);
			adjAuth.setTollAmt(this.tollAmt);
			adjAuth.setSurchargeAmt(this.surchargeAmt);
			adjAuth.setTaxAmt(this.taxAmt);
			adjAuth.setConvenienceFeeAmt(this.convenienceFeeAmt);

			// ////////////////////////////////////////////////////////////////////////////////////

			envelope.dotNet = true;
			envelope.setOutputSoapObject(adjAuth.CreateAdjustAuthorizationRequest(NAMESPACE, METHOD_NAME));

			trustEveryone();

			// HttpTransportSE
			// Web method call
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// get the response
			AdjustAuthorizationResponse adjResponse = new AdjustAuthorizationResponse();
			adjResponse.ParseAdjustAuthorizeResponse(envelope);

			_ErrorMessage = adjResponse.get_ErrorMessage();
			_RequestEndTime = adjResponse.get_RequestEndTime();
			_RequestStartTime = adjResponse.get_RequestStartTime();
			_ResultCode = adjResponse.get_ResultCode();
			_RequestId = adjResponse.get_RequestId();
			_ResponseType = adjResponse.get_ResponseType();
			_CardType = adjResponse.get_CardType();
			_AuthorizationDate = adjResponse.get_AuthorizationDate();
			_TransactionId = adjResponse.get_TransactionId();
			_AuthorizationCode = adjResponse.get_AuthorizationCode();
			_PaymentAmt = adjResponse.get_PaymentAmt();
			_DeclineReason = adjResponse.get_DeclineReason();
			_JobId = adjResponse.get_JobId();

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in SendRequest in CMTAdjustAuthorization][SendRequest][" + e.getLocalizedMessage() + "] ");
			e.setStackTrace(null);
		}
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

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public void setFareAmt(String fareAmt) {
		this.fareAmt = fareAmt;
	}

	public void setTipAmt(String tipAmt) {
		this.tipAmt = tipAmt;
	}

	public void setTollAmt(String tollAmt) {
		this.tollAmt = tollAmt;
	}

	public void setSurchargeAmt(String surchargeAmt) {
		this.surchargeAmt = surchargeAmt;
	}

	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}

	public void setConvenienceFeeAmt(String convenienceFeeAmt) {
		this.convenienceFeeAmt = convenienceFeeAmt;
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
	private void trustEveryone() {
		try {
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
		} catch (Exception e) { // should never happen
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in trustEveryone in CMTAdjustAuthorization][trustEveryone][" + e.getLocalizedMessage() + "] ");
			e.printStackTrace();
		}
	}
}
