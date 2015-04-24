package itcurves.ncs.creditcard.cmt;

import java.io.IOException;
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
import org.xmlpull.v1.XmlPullParserException;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

public class CMTAcknowledgeAuthorization {

	// header member
	private String _username;
	private String _password;
	private String _dataSource;

	// Request members
	private String _requestId;
	private String _deviceId;
	private String _userId;
	private String _jobId;
	private String _transactionId;
	private String _authorizationCode;
	private String _responseCode;

	// Response members
	private String _ErrorMessage;
	private String _RequestEndTime;
	private String _RequestStartTime;
	private String _ResultCode;

	private String _RequestId;
	private String _IsSuccesful;

	private final String URL;
	public CMTAcknowledgeAuthorization(String url) {
		URL = url;
	}

	public void SendRequest() throws IOException, XmlPullParserException {

		String SOAP_ACTION = "http://services.cmtnyc.com/payment/AcknowledgeAuthorization";
		String METHOD_NAME = "AcknowledgeAuthorization";
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

		// ////////// FOR CREATING ACKNOWLEDGE_AUTHORIZATION REQUEST OBJECT ///////////////////
		AcknowledgeAuthorization ackAuth = new AcknowledgeAuthorization();
		ackAuth.set_requestId(this._requestId);
		ackAuth.set_deviceId(this._deviceId);
		ackAuth.set_userId(this._userId);
		ackAuth.set_jobId(this._jobId);
		ackAuth.set_transactionId(this._transactionId);
		ackAuth.set_authorizationCode(this._authorizationCode);
		ackAuth.set_responseCode(this._responseCode);
		// ////////////////////////////////////////////////////////////////////////////////////

		envelope.dotNet = true;
		envelope.setOutputSoapObject(ackAuth.CreateAcknowledgeAuthorizationRequest(NAMESPACE, METHOD_NAME));

		trustEveryone();

		// HttpTransportSE
		// Web method call
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.call(SOAP_ACTION, envelope);

		// get the response
		AcknowledgeAuthorizationResponse ackAuthRes = new AcknowledgeAuthorizationResponse();
		ackAuthRes.ParseAcknowledgeAuthorizeResponse(envelope);

		_ErrorMessage = ackAuthRes.get_ErrorMessage();
		_RequestEndTime = ackAuthRes.get_RequestEndTime();
		_RequestStartTime = ackAuthRes.get_RequestStartTime();
		_ResultCode = ackAuthRes.get_ResultCode();

		_RequestId = ackAuthRes.get_RequestId();
		_IsSuccesful = ackAuthRes.get_IsSuccesful();

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

	public void set_transactionId(String _transactionId) {
		this._transactionId = _transactionId;
	}

	public void set_authorizationCode(String _authorizationCode) {
		this._authorizationCode = _authorizationCode;
	}

	public void set_responseCode(String _responseCode) {
		this._responseCode = _responseCode;
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

	public String get_IsSuccesful() {
		return _IsSuccesful;
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
				list.exception("[exception in trustEveryone in CMTAcknowledgeAuthorization][trustEveryone][" + e.getLocalizedMessage() + "] ");
			e.printStackTrace();
		}
	}
}
