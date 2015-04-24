package itcurves.ncs.creditcard.cmt;

import org.ksoap2.serialization.SoapObject;

public class AdjustAuthorization {
	
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
	
	
	public AdjustAuthorization()
	{
		
		requestId = "";
		deviceId = "";
	    userId= "";
		jobId = "";
		transactionId = "";
		authorizationCode = "";
		operationMode = "";
		paymentAmt = "";
		fareAmt = "";
		tipAmt = "";
		tollAmt = "";
		surchargeAmt = "";
		taxAmt = "";
		convenienceFeeAmt = "";
		
		
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	public String getOperationMode() {
		return operationMode;
	}
	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getPaymentAmt() {
		return paymentAmt;
	}
	public void setFareAmt(String fareAmt) {
		this.fareAmt = fareAmt;
	}
	public String getFareAmt() {
		return fareAmt;
	}
	public void setTipAmt(String tipAmt) {
		this.tipAmt = tipAmt;
	}
	public String getTipAmt() {
		return tipAmt;
	}
	public void setTollAmt(String tollAmt) {
		this.tollAmt = tollAmt;
	}
	public String getTollAmt() {
		return tollAmt;
	}
	public void setSurchargeAmt(String surchargeAmt) {
		this.surchargeAmt = surchargeAmt;
	}
	public String getSurchargeAmt() {
		return surchargeAmt;
	}
	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}
	public String getTaxAmt() {
		return taxAmt;
	}
	public void setConvenienceFeeAmt(String convenienceFeeAmt) {
		this.convenienceFeeAmt = convenienceFeeAmt;
	}
	public String getConvenienceFeeAmt() {
		return convenienceFeeAmt;
	}
	
	public SoapObject CreateAdjustAuthorizationRequest(String NAMESPACE, String METHOD_NAME)
	{
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("requestId",requestId);
		request.addProperty("deviceId",deviceId);
		request.addProperty("userId",userId);
		request.addProperty("jobId",jobId);
		request.addProperty("transactionId",transactionId);
		request.addProperty("authorizationCode",authorizationCode);
		request.addProperty("operationMode",operationMode);
		request.addProperty("paymentAmt",paymentAmt);
		request.addProperty("fareAmt",fareAmt);
		request.addProperty("tipAmt",tipAmt);
		request.addProperty("tollAmt",tollAmt);
		request.addProperty("surchargeAmt",surchargeAmt);
		request.addProperty("taxAmt",taxAmt);
		request.addProperty("convenienceFeeAmt",convenienceFeeAmt);
		
		return request;	
		
	}

	

}
