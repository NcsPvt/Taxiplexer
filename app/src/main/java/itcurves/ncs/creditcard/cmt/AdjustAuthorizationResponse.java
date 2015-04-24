package itcurves.ncs.creditcard.cmt;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class AdjustAuthorizationResponse {

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

	public AdjustAuthorizationResponse() {
		set_ErrorMessage("");
		set_RequestEndTime("");
		set_RequestStartTime("");
		set_ResultCode("");

		set_RequestId("");
		set_ResponseType("");
		set_CardType("");
		set_AuthorizationDate("");
		set_TransactionId("");
		set_AuthorizationCode("");
		set_PaymentAmt("");
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

	public void set_RequestId(String _RequestId) {
		this._RequestId = _RequestId;
	}

	public String get_RequestId() {
		return _RequestId;
	}

	public void set_ResponseType(String _ResponseType) {
		this._ResponseType = _ResponseType;
	}

	public String get_ResponseType() {
		return _ResponseType;
	}

	public void set_CardType(String _CardType) {
		this._CardType = _CardType;
	}

	public String get_CardType() {
		return _CardType;
	}

	public void set_AuthorizationDate(String _AuthorizationDate) {
		this._AuthorizationDate = _AuthorizationDate;
	}

	public String get_AuthorizationDate() {
		return _AuthorizationDate;
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

	public void set_PaymentAmt(String _PaymentAmt) {
		this._PaymentAmt = _PaymentAmt;
	}

	public String get_PaymentAmt() {
		return _PaymentAmt;
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

	public void ParseAdjustAuthorizeResponse(SoapSerializationEnvelope envelope) throws SoapFault {
		String successVar = "Success";
		SoapObject result = (SoapObject) envelope.getResponse();

		_ResultCode = result.getProperty("ResultCode").toString();

		if (_ResultCode.equals(successVar)) {

			_RequestEndTime = result.getProperty("RequestEndTime").toString();
			_RequestStartTime = result.getProperty("RequestStartTime").toString();

			SoapObject result123 = (SoapObject) result.getProperty("ResultObj");
			_ResponseType = result123.getProperty("ResponseType").toString();
			_RequestId = result123.getProperty("RequestId").toString();
			_PaymentAmt = result123.getProperty("PaymentAmt").toString();
			_JobId = result123.getProperty("JobId").toString();

			if (_ResponseType.equalsIgnoreCase("1")) {
				_AuthorizationDate = result123.getProperty("AuthorizationDate").toString();
				_TransactionId = result123.getProperty("TransactionId").toString();
				_AuthorizationCode = result123.getProperty("AuthorizationCode").toString();
			} else
				_DeclineReason = result123.getProperty("DeclineReason").toString();

		} else {
			_ErrorMessage = result.getProperty("ErrorMessage").toString();
		}

	}

}
