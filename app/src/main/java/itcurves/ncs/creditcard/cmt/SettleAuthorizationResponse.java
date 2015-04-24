package itcurves.ncs.creditcard.cmt;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class SettleAuthorizationResponse {

	private String _ErrorMessage;
	private String _RequestEndTime;
	private String _RequestStartTime;
	private String _ResultCode = "0";

	private String _deviceId;
	private String _settleStatus;
	private String _TransactionId;
	private String _AuthorizationCode;
	private String _DeclineReason;
	private String _JobId;

	public SettleAuthorizationResponse() {
		set_ErrorMessage("Error");
		set_RequestEndTime("");
		set_RequestStartTime("");
		set_ResultCode("0");

		set_TransactionId("");
		set_AuthorizationCode("");
		set_settleStatus("");
		set_deviceId("");
		set_DeclineReason("");
		set_JobId("");

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

	public void set_TransactionId(String _TransactionId) {
		this._TransactionId = _TransactionId;
	}

	public String get_TransactionId() {
		return _TransactionId;
	}

	public void set_AuthorizationCode(String _AuthorizationCode) {
		this._AuthorizationCode = _AuthorizationCode;
	}

	public String get_AuthorizationCode() {
		return _AuthorizationCode;
	}

	public void set_DeclineReason(String _DeclineReason) {
		this._DeclineReason = _DeclineReason;
	}

	public String get_DeclineReason() {
		return _DeclineReason;
	}

	public void set_JobId(String _JobId) {
		this._JobId = _JobId;
	}

	public String get_JobId() {
		return _JobId;
	}

	public void set_deviceId(String _deviceId) {
		this._deviceId = _deviceId;
	}

	public String get_deviceId() {
		return _deviceId;
	}

	public void set_settleStatus(String _settleStatus) {
		this._settleStatus = _settleStatus;
	}

	public String get_settleStatus() {
		return _settleStatus;
	}

	public void ParseSettleAuthorizeResponse(SoapSerializationEnvelope envelope) throws SoapFault {
		String successVar = "Success";
		SoapObject result = (SoapObject) envelope.getResponse();

		if (result.toString().contains(successVar)) {

			_ResultCode = result.getProperty("ResultCode").toString();

			if (_ResultCode.equals(successVar)) {

				_RequestEndTime = result.getProperty("RequestEndTime").toString();
				_RequestStartTime = result.getProperty("RequestStartTime").toString();

				SoapObject result1 = (SoapObject) result.getProperty("ResultObj");
				SoapObject result123 = (SoapObject) result1.getProperty("SettleResponse");

				_settleStatus = result123.getProperty("SettleStatus").toString();
				_JobId = result123.getProperty("JobId").toString();

				if (_settleStatus.equals("false")) {
					_DeclineReason = result123.getProperty("DeclineReason").toString();
				} else {
					_deviceId = result123.getProperty("deviceId").toString();
					_TransactionId = result123.getProperty("TransactionId").toString();
					_AuthorizationCode = result123.getProperty("AuthorizationCode").toString();
				}

			} else {
				_ErrorMessage = result.getProperty("ErrorMessage").toString();
			}

		}

	}

}
