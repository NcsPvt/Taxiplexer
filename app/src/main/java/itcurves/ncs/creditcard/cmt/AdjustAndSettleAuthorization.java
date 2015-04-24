package itcurves.ncs.creditcard.cmt;

import org.ksoap2.serialization.SoapObject;

public class AdjustAndSettleAuthorization {
	
	private SettleAuthorization _settleAuth;
	private String _fare;
	private String _tips;
	private String _tolls;
	private String _surcharge;
	private String _tax;
	private String _convenienceFee;
	private String _operationMode;
	public void set_fare(String _fare) {
		this._fare = _fare;
	}
	public String get_fare() {
		return _fare;
	}
	public void set_tips(String _tips) {
		this._tips = _tips;
	}
	public String get_tips() {
		return _tips;
	}
	public void set_tolls(String _tolls) {
		this._tolls = _tolls;
	}
	public String get_tolls() {
		return _tolls;
	}
	public void set_surcharge(String _surcharge) {
		this._surcharge = _surcharge;
	}
	public String get_surcharge() {
		return _surcharge;
	}
	public void set_tax(String _tax) {
		this._tax = _tax;
	}
	public String get_tax() {
		return _tax;
	}
	public void set_convenienceFee(String _convenienceFee) {
		this._convenienceFee = _convenienceFee;
	}
	public String get_convenienceFee() {
		return _convenienceFee;
	}
	public void set_operationMode(String _operationMode) {
		this._operationMode = _operationMode;
	}
	public String get_operationMode() {
		return _operationMode;
	}
	public void set_settleAuth(SettleAuthorization _settleAuth) {
		this._settleAuth = _settleAuth;
	}
	public SettleAuthorization get_settleAuth() {
		return _settleAuth;
	}
	
	
	public AdjustAndSettleAuthorization()
	{
		_fare = "";
		_tips = "";
		_tolls = "";
		_surcharge = "";
		_tax = "";
		_convenienceFee = "";
		_operationMode = "";
		_settleAuth = new SettleAuthorization();
	}
	
	public SoapObject CreateAdjustAndSettleAuthorizationRequest(String NAMESPACE,String METHOD_NAME)
	{
		

		SoapObject object = new SoapObject(NAMESPACE,"Adjustment");
		
		if(_settleAuth.getDeviceId().toString() != "")
		{
			object.addProperty("deviceId", _settleAuth.getDeviceId().toString());
		}
		
		if(_settleAuth.getJobId().toString() != "")
		{
			object.addProperty("JobId", _settleAuth.getJobId().toString());
		}
		
		if(_settleAuth.getTransactionId().toString() != "")
		{
			object.addProperty("TransactionId", _settleAuth.getTransactionId().toString());
		}
	   	
		if(_settleAuth.getAuthorizationCode().toString() != "")
		{
			object.addProperty("AuthorizationCode", _settleAuth.getAuthorizationCode().toString());
		}
	   	  	
		if(_fare != "")
		{
			object.addProperty("Fare", _fare);
		}
		
		if(_tips != "")
		{
			object.addProperty("Tips", _tips);
		}
		if(_tolls != "")
		{
			object.addProperty("Tolls", _tolls);
		}
		if(_surcharge != "")
		{
			object.addProperty("Surcharge", _surcharge);
		}
		if(_tax != "")
		{
			object.addProperty("Tax", _tax);
		}
		if(_convenienceFee != "")
		{
			object.addProperty("ConvenienceFee", _convenienceFee);
		}
		if(_operationMode != "")
		{
			object.addProperty("OperationMode", _operationMode);
		}
		
		
	   	SoapObject object1 = new SoapObject(NAMESPACE,"ArrayOfAdjustment");
	   	object1.addProperty("Adjustment", object);    
	   	    
	   	    
	   	SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
	   	request.addProperty("adjustmentList",object1);
	   	       	  
		
		return request;				
	}
	
	

}
