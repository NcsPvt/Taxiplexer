package itcurves.ncs;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class MyGeocoder {

	public static String reverseGeocode(Double lat, Double lon) {
		// http://maps.google.com/maps/geo?q=40.714224,-73.961452&output=json&oe=utf8&sensor=true_or_false&key=your_api_key

		String latitude = Double.toString(lat);
		String longitude = Double.toString(lon);

		String localityName = "";
		HttpURLConnection connection = null;
		URL serverAddress = null;

		try {
			// build the URL using the latitude & longitude you want to lookup
			serverAddress = new URL("http://maps.google.com/maps/geo?q=" + latitude + "," + longitude + "&output=xml&oe=utf8&sensor=true&key=" + AVL_Service.googleMapAPIKey);

			// Set up the initial connection
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(15000);

			connection.connect();

			try {
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				InputSource source = new InputSource(isr);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				XMLReader xr = parser.getXMLReader();
				GoogleReverseGeocodeXmlHandler handler = new GoogleReverseGeocodeXmlHandler();

				xr.setContentHandler(handler);
				xr.parse(source);

				localityName = handler.getAddress();
			} catch (Exception ex) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("[exception in reverseGeocode in mygeocoder][reverseGeocode][" + ex.getLocalizedMessage() + "]");
			}

			finally {
				connection.disconnect();
			}

		} catch (Exception ex) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in reverseGeocode in mygeocoder][reverseGeocode][" + ex.getLocalizedMessage() + "]");
		}

		return localityName;
	}

}