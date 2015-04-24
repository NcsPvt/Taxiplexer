package itcurves.ncs.creditcard.cmt;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class AcknowledgeAuthorizationResponse {

	private String _ErrorMessage;
	private String _RequestEndTime;
	private String _RequestStartTime;
	private String _ResultCode;

	private String _RequestId;
	private String _IsSuccesful;

	public AcknowledgeAuthorizationResponse() {
		set_ErrorMessage("");
		set_RequestEndTime("");
		set_RequestStartTime("");
		set_ResultCode("");

		_RequestId = "";
		_IsSuccesful = "";

	}

	public void set_ErrorMessage(String _ErrorMessage) {
		this._ErrorMessage = _ErrorMessage;
	}

	public String get_ErrorMessage() {
		return _ErrorMessage;
	}

	public void set_RequestEndTime(String _RequestEndTime) {
		this._RequestEndTime = _RequestEndTime;
	}

	public String get_RequestEndTime() {
		return _RequestEndTime;
	}

	public void set_RequestStartTime(String _RequestStartTime) {
		this._RequestStartTime = _RequestStartTime;
	}

	public String get_RequestStartTime() {
		return _RequestStartTime;
	}

	public void set_ResultCode(String _ResultCode) {
		this._ResultCode = _ResultCode;
	}

	public String get_ResultCode() {
		return _ResultCode;
	}

	public void set_RequestId(String _RequestId) {
		this._RequestId = _RequestId;
	}

	public String get_RequestId() {
		return _RequestId;
	}

	public void set_IsSuccesful(String _IsSuccesful) {
		this._IsSuccesful = _IsSuccesful;
	}

	public String get_IsSuccesful() {
		return _IsSuccesful;
	}

	public void ParseAcknowledgeAuthorizeResponse(SoapSerializationEnvelope envelope) throws SoapFault {
		String successVar = "Success";
		SoapObject result = (SoapObject) envelope.getResponse();

		_ResultCode = result.getProperty("ResultCode").toString();

		if (_ResultCode.equals(successVar)) {

			_RequestEndTime = result.getProperty("RequestEndTime").toString();
			_RequestStartTime = result.getProperty("RequestStartTime").toString();

		} else {
			_ErrorMessage = result.getProperty("ErrorMessage").toString();
		}

		SoapObject result123 = (SoapObject) result.getProperty("ResultObj");

		_RequestId = result123.getProperty("requestId").toString();
		_IsSuccesful = result123.getProperty("IsSuccesful").toString();

	}
}
