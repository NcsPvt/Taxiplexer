package itcurves.ncs.creditcard.cmt;

import org.ksoap2.serialization.SoapObject;

public class AcknowledgeAuthorization {
	
	private String _requestId;
    private String _deviceId;
    private String _userId;
    private String _jobId;
    private String _transactionId;
    private String _authorizationCode;
    private String _responseCode;
    
    
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
	public void set_transactionId(String _transactionId) {
		this._transactionId = _transactionId;
	}
	public String get_transactionId() {
		return _transactionId;
	}
	public void set_authorizationCode(String _authorizationCode) {
		this._authorizationCode = _authorizationCode;
	}
	public String get_authorizationCode() {
		return _authorizationCode;
	}
	public void set_responseCode(String _responseCode) {
		this._responseCode = _responseCode;
	}
	public String get_responseCode() {
		return _responseCode;
	}
    
	
	public AcknowledgeAuthorization()
	{
		_requestId = "";
	    _deviceId = "";
	    _userId = "";
	    _jobId= "";
	    _transactionId = "";
	    _authorizationCode = "";
	    _responseCode= "";			
	}
	
	public SoapObject CreateAcknowledgeAuthorizationRequest(String NAMESPACE,String METHOD_NAME )
	{
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("requestId",_requestId);
		request.addProperty("deviceId",_deviceId);
		request.addProperty("userId",_userId);
		request.addProperty("jobId",_jobId);
		request.addProperty("transactionId",_transactionId);
		request.addProperty("authorizationCode",_authorizationCode);
		request.addProperty("responseCode",_responseCode);
		
		return request;
	}
	
	
	
	
    


}
