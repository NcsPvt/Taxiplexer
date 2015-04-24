package itcurves.ncs.creditcard.mjm;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MJM_XML_Handler extends DefaultHandler {

	public MJM_CreditCardResponse tempResponse = null;
	String currentValue = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.equalsIgnoreCase(""))
			try {
				tempResponse = new MJM_CreditCardResponse("");
			} catch (InvalidResponse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (localName.equalsIgnoreCase("FaultString")) {
			throw new SAXException(currentValue);
		}

		// if (localName.equalsIgnoreCase("TransDate"))
		// tempResponse.setTransDate(currentValue);
		// else if (localName.equalsIgnoreCase("TransTime"))
		// tempResponse.setTransTime(currentValue);
		// else if (localName.equalsIgnoreCase("SourceID"))
		// tempResponse.setSourceID(currentValue);
		// else if (localName.equalsIgnoreCase("CardNumber"))
		// tempResponse.setCardNumber(currentValue);
		// else if (localName.equalsIgnoreCase("Balance"))
		// tempResponse.setBalance(currentValue);
		// else if (localName.equalsIgnoreCase("ResponseCode"))
		// tempResponse.setResponseCode(currentValue);
		// else if (localName.equalsIgnoreCase("ResponseMessage"))
		// tempResponse.setResponseMessage(currentValue);
		// else if (localName.equalsIgnoreCase("ApprovalCode"))
		// tempResponse.setApprovalCode(currentValue);
		// else if (localName.equalsIgnoreCase("ApprovalAmount"))
		// tempResponse.setApprovalAmount(currentValue);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		currentValue = new String(ch, start, length);
	}

	public MJM_CreditCardResponse getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
