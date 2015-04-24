package itcurves.ncs.creditcard.mjm;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class TestingClass {

	private static final SimpleDateFormat mjm_DateFormat = new SimpleDateFormat("ddMMyy");
	private static final SimpleDateFormat mjm_TimeFormat = new SimpleDateFormat("hhmmss");

	public static void main(String args[])

	{

		MJM_GiftCardRequest request = new MJM_GiftCardRequest();
		request.setTransDate(mjm_DateFormat.format(System.currentTimeMillis()));
		request.setTransTime(mjm_TimeFormat.format(System.currentTimeMillis() - 32400000));
		request.setSourceID("99");
		request.setCardNumber("3950011000000092");
		request.setCardExpiry("1212");
		request.setRequestType("2");
		request.setAmount("1");
		request.setDriverID("8000");
		request.setVehicleID("9000");
		request.setGPS("34.037834,-118.266502");
		request.setAddress("1501 Sulgrave Ave, Baltimore, MD 21209");
		request.setTripID("0000000001");
		request.setPassengers("1");
		request.setTrackInfo("3950011000000092=12127010000000000000");

		String xmlRequest = request.to_XML();

		MJM_GiftCardResponse wsResponse1;
		try {
			wsResponse1 = request.sendRequest("http://207.59.103.5:8221"); // "http://209.251.35.63:8225"

			System.out.print(wsResponse1 == null ? "No Response" : wsResponse1.toString());
			System.out.print("\n");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block

		}

		// /////////////////////////////////////////////////////////////////////////////
		// /// Test For Sale with Swiped /////////////////////////////////////////////

		MJM_Sale s = new MJM_Sale();
		s.set_userID("MJMTEST");
		s.set_userPwd("TestMJM");
		s.set_bankID("026900");
		s.set_merchantID("878827000011609");

		s.setAmount1(2);
		s.setAmount2(1);
		s.setTrackII("4000000000000019=13121011000");

		String response = s.SendSaleRequest();

		System.out.print(response);
		System.out.print("\n");

		// //////////////////////////////////////////////////////////////////////////////
		// ///////////////// Test For Return with Swiped ////////////////////////////////////

		MJM_Return r = new MJM_Return();
		r.set_userID("MJMTEST");
		r.set_userPwd("TestMJM");
		r.set_bankID("026900");
		r.set_merchantID("878827000011609");

		r.setAmount1(2);
		r.setTrackII("4000000000000019=131210110000");

		response = r.SendReturnRequest();

		System.out.print(response);
		System.out.print("\n");

		// //////////////////////////////////////////////////////////////////////////////////
		// //////////////// Test For Authorise with Swiped ///////////////////////////////////

		MJM_Authorize a = new MJM_Authorize();
		a.set_userID("MJMTEST");
		a.set_userPwd("TestMJM");
		a.set_bankID("026900");
		a.set_merchantID("878827000011609");

		a.setAmount1("2");
		a.setTrackII("4000000000000019=131210110000");

		response = a.SendAuthorizeRequest();

		System.out.print(response);
		System.out.print("\n");

		// //////////////////////////////////////////////////////////////////////////////////
		// //////////////// Test For Post Authorise with Swiped /////////////////////////////

		MJM_PostAuthorize pa = new MJM_PostAuthorize();
		pa.set_userID("MJMTEST");
		pa.set_userPwd("TestMJM");
		pa.set_bankID("026900");
		pa.set_merchantID("878827000011609");

		pa.setAmount1(2);
		pa.setAmount2(1);
		pa.setApprovalCode("123456");
		pa.setTransactionID("B95495FA-8F4A-499C-A9AB-21F237499C7D");
		pa.setTrackII("4000000000000019=131210110000");

		response = pa.SendPostAuthorizeRequest();

		System.out.print(response);
		System.out.print("\n");

		// /////////////////////////////////////////////////////////////////////////////////
		// /////////////// Test For Void Sale with Swiped ////////////////////////////////////

		MJM_VoidSale vs = new MJM_VoidSale();
		vs.set_userID("MJMTEST");
		vs.set_userPwd("TestMJM");
		vs.set_bankID("026900");
		vs.set_merchantID("878827000011609");

		vs.setAmount1(2);
		vs.setApprovalCode("123456");
		vs.setTransactionID("B95495FA-8F4A-499C-A9AB-21F237499C7D");
		vs.setTrackII("4000000000000019=131210110000");

		response = vs.SendVoidSaleRequest();

		System.out.print(response);
		System.out.print("\n");

	}
}
