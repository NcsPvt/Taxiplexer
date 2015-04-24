package itcurves.ncs.creditcard.mjm;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

public class MJM_CreditCardResponse {

	protected String TansactionID;
	protected String Date;
	protected String Time;
	public String Text;
	public String ResponseCode;
	protected String Amount1;
	protected String Amount2;
	protected String ApprovalCode;
	protected String ErrorCode;
	public String ErrorMsg;

	public String messageType;

	public static String MsgType_Response = "Response";
	public static String MsgType_Error = "Error";

	public MJM_CreditCardResponse(String response) throws InvalidResponse {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in MJM_CreditCardResponse in MJM_CreditCardResponse][MJM_CreditCardResponse][" + e.getLocalizedMessage() + "] ParserConfigurationException");

		}
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(response));
		Document doc = null;
		try {
			doc = db.parse(is);
		} catch (SAXException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in MJM_CreditCardResponse in MJM_CreditCardResponse][MJM_CreditCardResponse][" + e.getLocalizedMessage() + "] SAXException");

		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in MJM_CreditCardResponse in MJM_CreditCardResponse][MJM_CreditCardResponse][" + e.getLocalizedMessage() + "] IOException");

		}
		doc.getDocumentElement().normalize();
		NodeList responseLst = doc.getElementsByTagName("Response");

		if (responseLst.getLength() > 0) {
			this.messageType = "Response";
			Element resp = (Element) responseLst.item(0);
			this.TansactionID = ((Element) resp.getElementsByTagName("TransactionID").item(0)).getFirstChild().getNodeValue();
			this.Date = ((Element) resp.getElementsByTagName("Date").item(0)).getFirstChild().getNodeValue();
			this.Time = ((Element) resp.getElementsByTagName("Time").item(0)).getFirstChild().getNodeValue();
			this.Text = ((Element) resp.getElementsByTagName("Text").item(0)).getFirstChild().getNodeValue();
			this.ResponseCode = ((Element) resp.getElementsByTagName("ResponseCode").item(0)).getFirstChild().getNodeValue();
			this.ApprovalCode = ((Element) resp.getElementsByTagName("ApprovalCode").item(0)).getFirstChild().getNodeValue();
			if (ResponseCode.equalsIgnoreCase("1")) {
				this.Amount2 = ((Element) resp.getElementsByTagName("Amount2").item(0)).getFirstChild().getNodeValue();
				this.Amount1 = ((Element) resp.getElementsByTagName("Amount1").item(0)).getFirstChild().getNodeValue();
			}
		} else {
			this.messageType = "Error";
			responseLst = doc.getElementsByTagName("Error");
			Element resp = (Element) responseLst.item(0);
			// this.TansactionID = ((Element) resp.getElementsByTagName("TransactionID").item(0)).getFirstChild().getNodeValue();
			this.ErrorCode = ((Element) resp.getElementsByTagName("Code").item(0)).getFirstChild().getNodeValue();
			this.ErrorMsg = ((Element) resp.getElementsByTagName("Message").item(0)).getFirstChild().getNodeValue();
		}

	}
}