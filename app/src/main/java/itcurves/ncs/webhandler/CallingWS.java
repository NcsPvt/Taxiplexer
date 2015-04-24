package itcurves.ncs.webhandler;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;
import itcurves.ncs.bluebamboo.StringUtil;

import java.io.IOException;
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
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class CallingWS {

	public static WS_Response submit(String... Url_Action_Envelope) {

		WS_Response wsResponse = new WS_Response(null);
		try {
			final DefaultHttpClient httpClient = new DefaultHttpClient();
			// request parameters
			HttpParams params = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 90000);
			HttpConnectionParams.setSoTimeout(params, 90000);
			// set parameter
			HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);

			// POST the envelope
			HttpPost httppost = new HttpPost(Url_Action_Envelope[0]);
			// add headers
			httppost.setHeader("soapaction", Url_Action_Envelope[1]);
			httppost.setHeader("Content-Type", "text/xml; charset=utf-8");

			String responseString = "";

			// the entity holds the request
			HttpEntity entity = new StringEntity(Url_Action_Envelope[2]);
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
			Log.w("Sending " + Url_Action_Envelope[1].split("/")[3], " Envelope= " + Url_Action_Envelope[2]);

			// For Debugging purpose only
			// responseString =
			// "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><soap:Fault><faultcode>soap:Server</faultcode><faultstring>Server was unable to process request. ---&gt; ExecuteReader: Connection property has not been initialized.</faultstring><detail /></soap:Fault></soap:Body></soap:Envelope>";

			// close the connection
			httpClient.getConnectionManager().shutdown();

			wsResponse.errorString = StringUtil.getDataBetweenTags(responseString, "<faultstring>", "</faultstring>");

			if (wsResponse.errorString.trim().length() > 3) {
				wsResponse.error = true;
			} else {
				XMLPullParserHandler xmlPullParserHandler = new XMLPullParserHandler(responseString);
				wsResponse = xmlPullParserHandler.parse();
			}

			// } else {
			// SAXParserFactory factory = SAXParserFactory.newInstance();
			// // factory.setValidating(true);
			// SAXParser sp = factory.newSAXParser();
			// XMLReader reader = sp.getXMLReader();
			// WS_XmlHandler parser = new WS_XmlHandler();
			//
			// BufferedReader br = new BufferedReader(new StringReader(responseString));
			// reader.setContentHandler(parser);
			// InputSource is = new InputSource(br);
			// reader.parse(is);
			//
			// wsResponse = parser.getResponse();
			// }

			return wsResponse;

		} catch (XmlPullParserException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[Calling ITC-WEBService][Parsing: " + Url_Action_Envelope[1].split("/")[3] + "] " + e.getLocalizedMessage());
			return null;
		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[Calling ITC-WEBService][Response: " + Url_Action_Envelope[1].split("/")[3] + "] " + e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[Calling ITC-WEBService][Response: " + Url_Action_Envelope[1].split("/")[3] + "] " + e.getLocalizedMessage());
			return null;
		}
		// return wsResponse;
	}// doInBackground

}
