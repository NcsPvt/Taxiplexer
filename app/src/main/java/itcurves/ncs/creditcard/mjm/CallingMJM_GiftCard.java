package itcurves.ncs.creditcard.mjm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.os.AsyncTask;

public class CallingMJM_GiftCard extends AsyncTask<String, Integer, MJM_GiftCardResponse> {

	@Override
	protected void onPreExecute() {

	}// onPreExecute

	@Override
	protected MJM_GiftCardResponse doInBackground(String... url_xmlPacket) {

		try {
			final DefaultHttpClient httpClient = new DefaultHttpClient();
			// request parameters
			HttpParams params = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 15000);
			// set parameter
			HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);

			// POST the envelope
			HttpPost httppost = new HttpPost(url_xmlPacket[0]); // "http://207.59.103.5:8221"

			String responseString = "";

			// the entity holds the request
			HttpEntity entity = new StringEntity(url_xmlPacket[1]);
			httppost.setEntity(entity);

			// Response handler
			ResponseHandler<String> rh = new ResponseHandler<String>() {
				// invoked when client receives response
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

					// get response entity
					HttpEntity entity = response.getEntity();

					// read the response as byte array
					StringBuffer out = new StringBuffer();
					byte[] b = EntityUtils.toByteArray(entity);

					// write the response byte array to a string buffer
					out.append(new String(b, 0, b.length));
					return out.toString();
				}
			};

			responseString = httpClient.execute(httppost, rh);

			// close the connection
			httpClient.getConnectionManager().shutdown();

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser sp = factory.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			MJM_GiftCardResponse parser = new MJM_GiftCardResponse();

			BufferedReader br = new BufferedReader(new StringReader(responseString));
			reader.setContentHandler(parser);
			InputSource is = new InputSource(br);
			reader.parse(is);

			return parser;

		} catch (Exception e) {
			return null;
		}

	}// doInBackground

	@Override
	protected void onPostExecute(MJM_GiftCardResponse result) {
		try {

			this.finalize();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}// onPostExecute

}
