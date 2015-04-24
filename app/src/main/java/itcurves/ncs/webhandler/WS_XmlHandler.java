package itcurves.ncs.webhandler;

import java.text.SimpleDateFormat;

import itcurves.ncs.ManifestWallTrip;
import itcurves.ncs.WallTrip;
import itcurves.ncs.webhandler.WS_Response;
import itcurves.ncs.webhandler.WS_Response.CCMappings;
import itcurves.ncs.webhandler.WS_Response.CCProcessingCompany;
import itcurves.ncs.webhandler.WS_Response.SdZoneList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WS_XmlHandler extends DefaultHandler {

	private static final SimpleDateFormat MRMS_DateFormat = new SimpleDateFormat("HHmmss MMddyyyy");
	public WS_Response tempResponse = null;

	public CCProcessingCompany CCProcessor;
	public CCMappings CCMapping;
	public WallTrip temp_wt;
	public WallTrip temp_trip;
	public ManifestWallTrip temp_mwt;
	public SdZoneList temp_getzonelist;

	String currentValue = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) {

		// if PostXMLResult

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

	public WS_Response getResponse() {
		return tempResponse;
	}

}
