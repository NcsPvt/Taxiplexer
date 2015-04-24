package itcurves.ncs.creditcard.cmt;

import org.ksoap2.serialization.SoapObject;

public class SettleAuthorization {
	
	private String deviceId;
	private String JobId;
	private String TransactionId;
	private String AuthorizationCode;
	
	public SettleAuthorization()
	{
		setDeviceId("");
		setJobId("");
		setTransactionId("");
		setAuthorizationCode("");		
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setJobId(String jobId) {
		JobId = jobId;
	}

	public String getJobId() {
		return JobId;
	}

	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}

	public String getTransactionId() {
		return TransactionId;
	}

	public void setAuthorizationCode(String authorizationCode) {
		AuthorizationCode = authorizationCode;
	}

	public String getAuthorizationCode() {
		return AuthorizationCode;
	}
	
	public SoapObject CreateSettleAuthorizationRequest(String NAMESPACE, String METHOD_NAME)
	{
		
		SoapObject object = new SoapObject(NAMESPACE,"Authorization");
		
		if(deviceId != "")
		{
			object.addProperty("deviceId", deviceId);
		}
		
		if(JobId != "")
		{
			object.addProperty("JobId", JobId);
		}
		
		if(TransactionId != "")
		{
			object.addProperty("TransactionId", TransactionId);
		}
	   	
		if(AuthorizationCode != "")
		{
			object.addProperty("AuthorizationCode", AuthorizationCode);
		}
	   	  	
	   	    
	   	SoapObject object1 = new SoapObject(NAMESPACE,"ArrayOfAuthorization");
	   	object1.addProperty("Authorization", object);    
	   	    
	   	    
	   	SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
	   	request.addProperty("authorizationList",object1);
	   	       	  
		
		return request;
		
	}
	
	
	

}
