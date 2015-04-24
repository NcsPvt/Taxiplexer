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

public class CMTSettlementAuthorization {

	// Header Members
	private String _username;
	private String _password;
	private String _dataSource;

	// Request Members
	private String deviceId;
	private String JobId;
	private String TransactionId;
	private String AuthorizationCode;

	// Response Members
	private String _ErrorMessage;
	private String _RequestEndTime;
	private String _RequestStartTime;
	private String _ResultCode;
	private String _deviceId;
	private String _settleStatus;
	private String _TransactionId;
	private String _AuthorizationCode;
	private String _DeclineReason;
	private String _JobId;
	private final String URL;

	public CMTSettlementAuthorization(String url) {
		URL = url;
	}

	public void SendRequest() {
		try {
			String SOAP_ACTION = "http://services.cmtnyc.com/payment/SettleAuthorization";
			String METHOD_NAME = "SettleAuthorization";
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

			SettleAuthorization settleauth = new SettleAuthorization();
			settleauth.setDeviceId(this.deviceId);
			settleauth.setJobId(this.JobId);
			settleauth.setTransactionId(this.TransactionId);
			settleauth.setAuthorizationCode(this.AuthorizationCode);

			envelope.dotNet = true;
			envelope.setOutputSoapObject(settleauth.CreateSettleAuthorizationRequest(NAMESPACE, METHOD_NAME));

			trustEveryone();

			// HttpTransportSE
			// Web method call
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.debug = true;

			androidHttpTransport.call(SOAP_ACTION, envelope);

			// get the response
			SettleAuthorizationResponse response = new SettleAuthorizationResponse();
			response.ParseSettleAuthorizeResponse(envelope);

			_ErrorMessage = response.get_ErrorMessage();
			_RequestEndTime = response.get_RequestEndTime();
			_RequestStartTime = response.get_RequestStartTime();
			_ResultCode = response.get_ResultCode();
			_deviceId = response.get_deviceId();
			_settleStatus = response.get_settleStatus();
			_TransactionId = response.get_TransactionId();
			_AuthorizationCode = response.get_AuthorizationCode();
			_DeclineReason = response.get_DeclineReason();
			_JobId = response.get_JobId();

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in SendRequest in CMTSettlementAuthorization][SendRequest][" + e.getLocalizedMessage() + "] ");
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

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setJobId(String jobId) {
		JobId = jobId;
	}

	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}

	public void setAuthorizationCode(String authorizationCode) {
		AuthorizationCode = authorizationCode;
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

	public String get_deviceId() {
		return _deviceId;
	}

	public String get_ResultCode() {
		return _ResultCode;
	}

	public String get_settleStatus() {
		return _settleStatus;
	}

	public String get_TransactionId() {
		return _TransactionId;
	}

	public String get_AuthorizationCode() {
		return _AuthorizationCode;
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
				list.exception("[exception in trustEveryone in CMTSettlementAuthorization][trustEveryone][" + e.getLocalizedMessage() + "] ");
			e.printStackTrace();
		}
	}
}
