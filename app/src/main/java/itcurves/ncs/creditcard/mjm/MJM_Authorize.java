package itcurves.ncs.creditcard.mjm;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

import android.annotation.SuppressLint;
import com.mjminnovations.transactions.CreditCard;

@SuppressLint("NewApi")
public class MJM_Authorize {

	private String _userID;
	private String _userPwd;
	private String _bankID;
	private String _merchantID;

	private final Integer TransactionType = 3;
	private String Amount1;
	private final Integer POSEntryType = 1;
	private String AccountNumber;
	private String ExpirationDate;
	private String TrackII;

	public MJM_Authorize() {
		_userID = "";
		_userPwd = "";
		_bankID = "";
		_merchantID = "";

		Amount1 = "0";
		AccountNumber = "";
		ExpirationDate = "";
		TrackII = "";

	}

	public void set_userID(String _userID) {
		this._userID = _userID;
	}

	public void set_userPwd(String _userPwd) {
		this._userPwd = _userPwd;
	}

	public void set_bankID(String _bankID) {
		this._bankID = _bankID;
	}

	public void set_merchantID(String _merchantID) {
		this._merchantID = _merchantID;
	}

	public void setAmount1(String f) {
		Amount1 = f;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public void setExpirationDate(String expirationDate) {
		ExpirationDate = expirationDate;
	}

	public void setTrackII(String trackII) {
		TrackII = trackII;
	}

	@SuppressLint("NewApi")
	public String SendAuthorizeRequest() {
		String xmlString = "";

		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element root = doc.createElement("Msg");
			doc.appendChild(root);

			// /////////// Header tag information /////////////////////////
			Element header = doc.createElement("Header");
			root.appendChild(header);

			Element headerchild = doc.createElement("UserID");
			header.appendChild(headerchild);
			Text headerchildText = doc.createTextNode(_userID);
			headerchild.appendChild(headerchildText);

			Element headerchild1 = doc.createElement("UserPwd");
			header.appendChild(headerchild1);
			Text headerchild1Text = doc.createTextNode(_userPwd);
			headerchild1.appendChild(headerchild1Text);

			Element headerchild2 = doc.createElement("BankID");
			header.appendChild(headerchild2);
			Text headerchild2Text = doc.createTextNode(_bankID);
			headerchild2.appendChild(headerchild2Text);

			Element headerchild3 = doc.createElement("MerchantID");
			header.appendChild(headerchild3);
			Text headerchild3Text = doc.createTextNode(_merchantID);
			headerchild3.appendChild(headerchild3Text);
			// /////////////////////////////////////////////////

			// ///////////// Request tag information //////////////////////////
			Element request = doc.createElement("Request");
			root.appendChild(request);

			Element requestchild = doc.createElement("TransactionType");
			request.appendChild(requestchild);
			Text requestchildText = doc.createTextNode(String.valueOf(TransactionType));
			requestchild.appendChild(requestchildText);

			Element requestchild1 = doc.createElement("Amount1");
			request.appendChild(requestchild1);
			Text requestchild1Text = doc.createTextNode(String.valueOf(Amount1));
			requestchild1.appendChild(requestchild1Text);

			Element requestchild3 = doc.createElement("POSEntryType");
			request.appendChild(requestchild3);
			Text requestchild3Text = doc.createTextNode(String.valueOf(POSEntryType));
			requestchild3.appendChild(requestchild3Text);

			if (TrackII.trim().length() > 2) {

				Element requestchild7 = doc.createElement("TrackII");
				request.appendChild(requestchild7);
				Text requestchild7Text = doc.createTextNode(TrackII);
				requestchild7.appendChild(requestchild7Text);

			} else {

				Element requestchild7 = doc.createElement("AccountNumber");
				request.appendChild(requestchild7);
				Text requestchild7Text = doc.createTextNode(AccountNumber);
				requestchild7.appendChild(requestchild7Text);

				Element requestchild8 = doc.createElement("ExpirationDate");
				request.appendChild(requestchild8);
				Text requestchild8Text = doc.createTextNode(ExpirationDate);
				requestchild8.appendChild(requestchild8Text);

			}

			// /////////////////////////////////////////////////////////////////

			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			xmlString = sw.toString();
			xmlString = "<?xml version=\"1.0\" standalone=\"yes\"?>" + xmlString;
			// ////////////////////////////////////////////////////////////////////////

			// /////////////// Communicating With MJM //////////////////////////////
			CreditCard crd = new CreditCard();
			xmlString = crd.ProcessTransaction(xmlString);
			// ////////////////////////////////////////////////////////////////////////

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in SendAuthorizeRequest in MJM_Authorize][SendAuthorizeRequest][" + e.getLocalizedMessage() + "] ");
		}

		return xmlString;

	}

}
