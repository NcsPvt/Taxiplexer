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

public class CMTAdjustAndSettleAuthorization {

	// header member
	private String _username;
	private String _password;
	private String _dataSource;

	// Request members
	private String _deviceId;
	private String _jobId;
	private String _transactionId;
	private String _authorizationCode;
	private String _fareAmt;
	private String _tipAmt;
	private String _tollAmt;
	private String _surchargeAmt;
	private String _taxAmt;
	private String _convenienceFeeAmt;

	private final String URL;
	public CMTAdjustAndSettleAuthorization(String url) {
		URL = url;
	}

	public void SendRequest() throws IOException, XmlPullParserException {
		try {
			String SOAP_ACTION = "http://services.cmtnyc.com/payment/AdjustAndSettleAuthorization";
			String METHOD_NAME = "AdjustAndSettleAuthorization";
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
			settleauth.setDeviceId(this._deviceId);
			settleauth.setJobId(this._jobId);
			settleauth.setTransactionId(this._transactionId);
			settleauth.setAuthorizationCode(this._authorizationCode);

			AdjustAndSettleAuthorization adjsettleauth = new AdjustAndSettleAuthorization();
			adjsettleauth.set_settleAuth(settleauth);
			adjsettleauth.set_fare(this._fareAmt);
			adjsettleauth.set_tips(this._tipAmt);
			adjsettleauth.set_tolls(this._tollAmt);
			adjsettleauth.set_surcharge(this._surchargeAmt);
			adjsettleauth.set_tax(this._taxAmt);
			adjsettleauth.set_operationMode("1");
			adjsettleauth.set_convenienceFee(this._convenienceFeeAmt);

			envelope.dotNet = true;
			envelope.setOutputSoapObject(adjsettleauth.CreateAdjustAndSettleAuthorizationRequest(NAMESPACE, METHOD_NAME));

			trustEveryone();

			// HttpTransportSE
			// Web method call
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.debug = true;

			androidHttpTransport.call(SOAP_ACTION, envelope);

			// get the response
			AdjustAndSettleAuthorizationResponse response = new AdjustAndSettleAuthorizationResponse();
			response.ParseAdjustAndSettleAuthorizeResponse(envelope);

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in SendRequest in CMTAdjustAndSettleAuthorization][SendRequest][" + e.getLocalizedMessage() + "] ");
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
		this._deviceId = deviceId;
	}

	public void setJobId(String jobId) {
		this._jobId = jobId;
	}

	public void setTransactionId(String transactionId) {
		this._transactionId = transactionId;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this._authorizationCode = authorizationCode;
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
				list.exception("[exception in trustEveryone in CMTAdjustAndSettleAuthorization][trustEveryone][" + e.getLocalizedMessage() + "] ");
			e.printStackTrace();
		}
	}
}
