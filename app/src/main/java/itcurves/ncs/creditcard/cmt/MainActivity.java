package itcurves.ncs.creditcard.cmt;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.xmlpull.v1.XmlPullParserException;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

public class MainActivity {
	/** Called when the activity is first created. */

	public static void main(String args[])

	{

		CMTAuthorizationCreditTrip k = new CMTAuthorizationCreditTrip("https://services.cmtnyc.com/payment/Payment.asmx");// http://services-staging.cmtnyc.com/payment/Payment.asmx
		k.set_username("regencyWSUser");// "testWSUser"
		k.set_password("1OWJCHSSLZQdP/iAzaxGYA==");// "eEJ8LSJwykc7F4cHFdK19g=="
		k.set_dataSource("j0xMtdJBwSBprpcjMuK0zLPPM9NfZiiK6khpnCVM8zU=");// "T65E372rO+Z8IreospS8X3nMeWO55saYMC1dbS6Xi8A="

		k.set_accountNumber("378282246310005");
		k.set_convenienceFeeAmt("0");
		k.set_cvv2("");
		k.set_deviceId("89");
		k.set_dropoffDate("");
		k.set_dropoffLatitude("0");
		k.set_dropoffLongitude("0");
		k.set_encryptedToken("");
		k.set_encryptionAlgorithm("");
		k.set_encryptionKeyVersion("");
		k.set_expiryDate("1312");
		k.set_fareAmt("0.5");
		k.set_jobId("91");
		k.set_passengerCount("0");
		k.set_paymentAmt("0.5");
		k.set_pickupDate("");
		k.set_pickupLatitude("0");
		k.set_pickupLongitude("0");
		k.set_readyToSettle("false");
		k.set_requestId("88");
		k.set_surchargeAmt("0");
		k.set_taxAmt("0");
		k.set_tipAmt("0");
		k.set_tollAmt("0");
		k.set_tripDistance("0");
		k.set_tripDuration("0");
		k.set_userId("90");
		k.set_zipCode("");

		try {
			k.SendRequest();
		} catch (KeyManagementException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in main in MainActivity][main][" + e.getLocalizedMessage() + "] KeyManagementException");

		} catch (NoSuchAlgorithmException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in main in MainActivity][main][" + e.getLocalizedMessage() + "] NoSuchAlgorithmException");

		} catch (IOException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in main in MainActivity][main][" + e.getLocalizedMessage() + "] IOException");

		} catch (XmlPullParserException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in main in MainActivity][main][" + e.getLocalizedMessage() + "] XmlPullParserException");

		}

		System.out.print("Response Type: " + k.get_ResponseType()
				+ "\nResponse Code: "
				+ k.get_ResultCode()
				+ "\nError Message: "
				+ k.get_ErrorMessage()
				+ "\nDecline Reason: "
				+ k.get_DeclineReason());
		System.out.print("\n");
		System.exit(0);

	}

}
