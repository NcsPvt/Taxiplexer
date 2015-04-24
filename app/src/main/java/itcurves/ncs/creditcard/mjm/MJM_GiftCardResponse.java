package itcurves.ncs.creditcard.mjm;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MJM_GiftCardResponse extends DefaultHandler {

	private String TransDate = "";
	private String TransTime = "";
	private String SourceID = "";
	private String CardNumber = "";
	private String Balance = "";
	private String ResponseCode = "";
	private String ResponseMessage = "";
	private String ApprovalCode = "";
	private String ApprovalAmount = "";

	public static final String Approval = "00";
	public static final String Bad_Packet = "06";
	public static final String Card_No_Error = "14";
	public static final String Expired_Card = "54";
	public static final String Serv_Not_Allowed = "57";
	public static final String System_Malfunction = "96";

	String currentValue = null;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (localName.equalsIgnoreCase("TransDate"))
			this.TransDate = currentValue;
		else if (localName.equalsIgnoreCase("TransTime"))
			this.TransTime = currentValue;
		else if (localName.equalsIgnoreCase("SourceID"))
			this.SourceID = currentValue;
		else if (localName.equalsIgnoreCase("CardNumber"))
			this.CardNumber = currentValue;
		else if (localName.equalsIgnoreCase("Balance"))
			this.Balance = currentValue;
		else if (localName.equalsIgnoreCase("ResponseCode"))
			this.ResponseCode = currentValue;
		else if (localName.equalsIgnoreCase("ResponseMessage"))
			this.ResponseMessage = currentValue;
		else if (localName.equalsIgnoreCase("ApprovalCode"))
			this.ApprovalCode = currentValue;
		else if (localName.equalsIgnoreCase("ApprovalAmount"))
			this.ApprovalAmount = currentValue;

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		currentValue = new String(ch, start, length);
	}

	public String getTransDate() {
		return TransDate;
	}

	public String getTransTime() {
		return TransTime;
	}

	public String getSourceID() {
		return SourceID;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public String getBalance() {
		return Balance;
	}

	public String getResponseCode() {
		return ResponseCode;
	}

	public String getResponseMessage() {
		return ResponseMessage;
	}

	public String getApprovalCode() {
		return ApprovalCode;
	}

	public String getApprovalAmount() {
		return ApprovalAmount;
	}

	@Override
	public String toString() {
		//@formatter:off
		String xmlResponse = new StringBuilder("<PaymentResponse>")
		.append("<TransDate>").append(TransDate).append("</TransDate>")
		.append("<TransTime>").append(TransTime).append("</TransTime>")
		.append("<SourceID>").append(SourceID).append("</SourceID>")
		.append("<CardNumber>").append(CardNumber).append("</CardNumber>")
		.append("<Balance>").append(Balance).append("</Balance>")
		.append("<ResponseCode>").append(ResponseCode).append("</ResponseCode>")
		.append("<ResponseMessage>").append(ResponseMessage).append("</ResponseMessage>")
		.append("<ApprovalCode>").append(ApprovalCode).append("</ApprovalCode>")
		.append("<ApprovalAmount>").append(ApprovalAmount).append("</ApprovalAmount>")
		.append("</PaymentResponse>")
		.toString();
		//@formatter:on
		return xmlResponse;
	}
}
