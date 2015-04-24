package itcurves.ncs;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GoogleReverseGeocodeXmlHandler extends DefaultHandler {
	private boolean isAddress = false;
	private boolean finished = false;
	private StringBuilder builder;
	private String address = "";
	private String[] AddressInParts;

	public String getLocalityName() {
		if (address.contains(",")) {
			AddressInParts = address.split(",", 3);
			return AddressInParts[0] + AddressInParts[1];
		} else
			return address;
	}

	public String getAddress() {
		return this.address;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (isAddress && !this.finished) {
			if ((ch[start] != '\n') && (ch[start] != ' ')) {
				builder.append(ch, start, length);
			}
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (localName.equalsIgnoreCase("address")) {
			this.isAddress = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

		if (!this.finished) {
			if (localName.equalsIgnoreCase("address")) {
				this.address = builder.toString();
				this.finished = true;
			}

			if (builder != null) {
				builder.setLength(0);
			}
		}
	}
}