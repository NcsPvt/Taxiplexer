package itcurves.ncs.webhandler;

import itcurves.ncs.CannedMessage;
import itcurves.ncs.ManifestWallTrip;
import itcurves.ncs.WallTrip;
import itcurves.ncs.webhandler.WS_Response.CCMappings;
import itcurves.ncs.webhandler.WS_Response.CCProcessingCompany;
import itcurves.ncs.webhandler.WS_Response.SdZoneList;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XMLPullParserHandler {

	private final XmlPullParser xpp;
	public WS_Response tempResponsePullParser = null;
	private String textBtwTags;
	public CannedMessage temp_cm;
	private static final SimpleDateFormat MRMS_DateFormat = new SimpleDateFormat("HHmmss MMddyyyy");

	public CCProcessingCompany CCProcessor;
	public CCMappings CCMapping;
	public WallTrip temp_wt;
    public WS_Response.ClassofService temp_smr;
	public WallTrip temp_trip;
	public ManifestWallTrip temp_mwt;
	public SdZoneList temp_getzonelist;

	public XMLPullParserHandler(String XmlString) throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		xpp = factory.newPullParser();
		xpp.setInput(new StringReader(XmlString));
	}
	public WS_Response parse() throws XmlPullParserException, IOException {

		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			String tagname = xpp.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG :
					if (tagname.equalsIgnoreCase("GetAssignedAndPendingTripsInStringResult"))
						tempResponsePullParser = new WS_Response("GetAssignedAndPendingTripsInStringResult");
					else if (tagname.equalsIgnoreCase("VehicleMileageResponse"))
						tempResponsePullParser = new WS_Response("VehicleMileageResponse");
					else if (tagname.equalsIgnoreCase("GetMessageHistoryResult"))
						tempResponsePullParser = new WS_Response("GetMessageHistoryResult");
					else if (tagname.equalsIgnoreCase("getSmartDeviceApplicationsResult"))
						tempResponsePullParser = new WS_Response("getSmartDeviceApplicationsResult");
					else if (tagname.equalsIgnoreCase("GetCreditCardProcessingCompanyResult"))
						tempResponsePullParser = new WS_Response("GetCreditCardProcessingCompanyResult");
					else if (tagname.equalsIgnoreCase("GetCreditCardMappingsResult"))
						tempResponsePullParser = new WS_Response("GetCreditCardMappingsResult");
					else if (tagname.equalsIgnoreCase("GetSpecializedWallTripsResult"))
						tempResponsePullParser = new WS_Response("GetSpecializedWallTripsResult");
					else if (tagname.equalsIgnoreCase("GetWallTripsResult"))
						tempResponsePullParser = new WS_Response("GetWallTripsResult");
					else if (tagname.equalsIgnoreCase("GetCannedMessagesResult"))
						tempResponsePullParser = new WS_Response("GetCannedMessagesResult");
					else if (tagname.equalsIgnoreCase("Process_SaleResult"))
						tempResponsePullParser = new WS_Response("Process_SaleResult");
					else if (tagname.equalsIgnoreCase("Process_PreAuthResult"))
						tempResponsePullParser = new WS_Response("Process_PreAuthResult");
					else if (tagname.equalsIgnoreCase("Process_PostAuthResult"))
						tempResponsePullParser = new WS_Response("Process_PostAuthResult");
					else if (tagname.equalsIgnoreCase("Process_InquiryResult"))
						tempResponsePullParser = new WS_Response("Process_InquiryResult");
					else if (tagname.equalsIgnoreCase("UpdateSmartDeviceMeterInfoResult"))
						tempResponsePullParser = new WS_Response("UpdateSmartDeviceMeterInfoResult");
					else if (tagname.equalsIgnoreCase("SDGetGeneralSettingsResult"))
						tempResponsePullParser = new WS_Response("SDGetGeneralSettingsResult");
					else if (tagname.equalsIgnoreCase("CalculateRouteByStreetAddressWithCostEstimatesResult"))
						tempResponsePullParser = new WS_Response("CalculateRouteByStreetAddressWithCostEstimatesResult");
					else if (tagname.equalsIgnoreCase("ReverseGeoCodeBylatlngResult"))
						tempResponsePullParser = new WS_Response("ReverseGeoCodeBylatlngResult");
					else if (tagname.equalsIgnoreCase("GetManifestSummaryInfoResult"))
						tempResponsePullParser = new WS_Response("GetManifestSummaryInfoResult");
					else if (tagname.equalsIgnoreCase("GetLiveMiscInfoResult"))
						tempResponsePullParser = new WS_Response("GetLiveMiscInfoResult");
					else if (tagname.equalsIgnoreCase("SDGetAdjacentZonesResult"))
						tempResponsePullParser = new WS_Response("SDGetAdjacentZonesResult");
					else if (tagname.equalsIgnoreCase("GetPersonBalanceResult"))
						tempResponsePullParser = new WS_Response("GetPersonBalanceResult");
					else if (tagname.equalsIgnoreCase("TopupCustomerBalanceFromBookinAppResult"))
						tempResponsePullParser = new WS_Response("TopupCustomerBalanceFromBookinAppResult");
					else if (tagname.equalsIgnoreCase("GetSDZoneListResult"))
						tempResponsePullParser = new WS_Response("GetSDZoneListResult");
					else if (tagname.equalsIgnoreCase("GetBalanceAndBlackListStatusResult"))
						tempResponsePullParser = new WS_Response("GetBalanceAndBlackListStatusResult");
					else if (tagname.equalsIgnoreCase("UploadSignatureResponse"))
						tempResponsePullParser = new WS_Response("UploadSignatureResponse");
					else if (tagname.equalsIgnoreCase("CheckPromotionValidityResult"))
						tempResponsePullParser = new WS_Response("CheckPromotionValidityResult");
                    else if (tagname.equalsIgnoreCase("PPV_CheckallowedBalanceOnTripCompletionResult"))
                        tempResponsePullParser = new WS_Response("PPV_CheckallowedBalanceOnTripCompletionResult");
                    else if (tagname.equalsIgnoreCase("ClassOfServiceRates"))
                        tempResponsePullParser = new WS_Response("ClassOfServiceRates");
                    else if (tagname.equalsIgnoreCase("SendPaymentReceiptToCustomer"))
                        tempResponsePullParser = new WS_Response("SendPaymentReceiptToCustomer");
					break;
				case XmlPullParser.TEXT :
					textBtwTags = xpp.getText();
					break;
				case XmlPullParser.END_TAG :
					if (tempResponsePullParser.responseType.equalsIgnoreCase("GetAssignedAndPendingTripsInStringResult")) {
						if (tagname.equalsIgnoreCase("TripDetail")) {
							tempResponsePullParser.tripList.add(textBtwTags);
						}
					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("VehicleMileageResponse")) {
						if (tagname.equalsIgnoreCase("bMileageUpdated"))
							tempResponsePullParser.updateVehicleMilage.set_bMileageUpdated(textBtwTags);
						else if (tagname.equalsIgnoreCase("FileMilage"))
							tempResponsePullParser.updateVehicleMilage.set_FileMilage(textBtwTags);
						else if (tagname.equalsIgnoreCase("FailureMsg"))
							tempResponsePullParser.updateVehicleMilage.set_FailureMsg(textBtwTags);
					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetMessageHistoryResult")) {
						if (tagname.equalsIgnoreCase("Message")) {
							temp_cm = new CannedMessage(textBtwTags);
							tempResponsePullParser.cannedMessages.add(temp_cm);
						}
					} // if getSmartDeviceApplicationsResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("getSmartDeviceApplicationsResult")) {
						if (tagname.equalsIgnoreCase("Excluded"))
							tempResponsePullParser.killAppsResp.setExcluded(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("ProcessName"))
							tempResponsePullParser.killAppsResp.setpName(textBtwTags);

						// if GetCreditCardProcessingCompany
					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetCreditCardProcessingCompanyResult")) {
						if (tagname.equalsIgnoreCase("CompanyAbbreviation"))
							tempResponsePullParser.CCProcessor.set_CompanyAbbreviation(textBtwTags);
						else if (tagname.equalsIgnoreCase("ServiceLink"))
							tempResponsePullParser.CCProcessor.set_ServiceLink(textBtwTags);
						else if (tagname.equalsIgnoreCase("UserName"))
							tempResponsePullParser.CCProcessor.set_UserName(textBtwTags);
						else if (tagname.equalsIgnoreCase("AccountPassword"))
							tempResponsePullParser.CCProcessor.set_AccountPassword(textBtwTags);
						else if (tagname.equalsIgnoreCase("MerchantId"))
							tempResponsePullParser.CCProcessor.set_MerchantId(textBtwTags);
						else if (tagname.equalsIgnoreCase("BankId"))
							tempResponsePullParser.CCProcessor.set_BankId(textBtwTags);
						else if (tagname.equalsIgnoreCase("Others"))
							tempResponsePullParser.CCProcessor.set_Others(textBtwTags);
						else if (tagname.equalsIgnoreCase("iGatewayVariable2"))
							tempResponsePullParser.CCProcessor.set_iGatewayVariable2(textBtwTags);
						else if (tagname.equalsIgnoreCase("iGatewayVariable3"))
							tempResponsePullParser.CCProcessor.set_iGatewayVariable3(textBtwTags);
						else if (tagname.equalsIgnoreCase("VVersion"))
							tempResponsePullParser.CCProcessor.set_vVersion(textBtwTags);
						else if (tagname.equalsIgnoreCase("vProduct"))
							tempResponsePullParser.CCProcessor.set_vProduct(textBtwTags);
						else if (tagname.equalsIgnoreCase("vKey"))
							tempResponsePullParser.CCProcessor.set_vKey(textBtwTags);
						else if (tagname.equalsIgnoreCase("bTestCAWKey"))
							tempResponsePullParser.CCProcessor.set_isTestCAWKey(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("PaymentProcessingMethod"))
							tempResponsePullParser.CCProcessor.set_PaymentProcessingMethod(textBtwTags);
						else if (tagname.equalsIgnoreCase("MultiStepScenario"))
							tempResponsePullParser.CCProcessor.set_MultiStepScenario(textBtwTags);
						else if (tagname.equalsIgnoreCase("CreditCardProcessCompany")) {
							CCProcessor = new CCProcessingCompany();
							CCProcessor.set_CompanyAbbreviation(tempResponsePullParser.CCProcessor.get_CompanyAbbreviation());
							CCProcessor.set_ServiceLink(tempResponsePullParser.CCProcessor.get_ServiceLink());
							CCProcessor.set_UserName(tempResponsePullParser.CCProcessor.get_UserName());
							CCProcessor.set_AccountPassword(tempResponsePullParser.CCProcessor.get_AccountPassword());
							CCProcessor.set_MerchantId(tempResponsePullParser.CCProcessor.get_MerchantId());
							CCProcessor.set_BankId(tempResponsePullParser.CCProcessor.get_BankId());
							CCProcessor.set_Others(tempResponsePullParser.CCProcessor.get_Others());
							CCProcessor.set_iGatewayVariable2(tempResponsePullParser.CCProcessor.get_iGatewayVariable2());
							CCProcessor.set_iGatewayVariable3(tempResponsePullParser.CCProcessor.get_iGatewayVariable3());
							CCProcessor.set_vVersion(tempResponsePullParser.CCProcessor.get_vVersion());
							CCProcessor.set_vProduct(tempResponsePullParser.CCProcessor.get_vProduct());
							CCProcessor.set_vKey(tempResponsePullParser.CCProcessor.get_vKey());
							CCProcessor.set_isTestCAWKey(tempResponsePullParser.CCProcessor.get_isTestCAWKey());
							CCProcessor.set_PaymentProcessingMethod(tempResponsePullParser.CCProcessor.get_PaymentProcessingMethod());
							CCProcessor.set_MultiStepScenario(tempResponsePullParser.CCProcessor.get_MultiStepScenario());
							tempResponsePullParser.cardProcessorList.put(tempResponsePullParser.CCProcessor.get_CompanyAbbreviation(), CCProcessor);
							Log.w("CreditCardProcessingCompany", CCProcessor.toString());
						}

					} // if GetCreditCardMappings
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetCreditCardMappingsResult")) {
						if (tagname.equalsIgnoreCase("CreditCardCompany"))
							tempResponsePullParser.CCMapping.setCardProcessingCompany(textBtwTags);
						else if (tagname.equalsIgnoreCase("CreditCardType")) {
							textBtwTags = textBtwTags.toUpperCase();
							tempResponsePullParser.CCMapping.setCreditCardType(textBtwTags);
						} else if (tagname.equalsIgnoreCase("CreditCardMapping")) {
							CCMapping = new CCMappings("", "");;
							CCMapping.setCardProcessingCompany(tempResponsePullParser.CCMapping.getCardProcessingCompany());
							CCMapping.setCreditCardType(tempResponsePullParser.CCMapping.getCreditCardType());
							tempResponsePullParser.CardMappingList.put(tempResponsePullParser.CCMapping.getCreditCardType(), CCMapping);
							Log.w("CreditCardMapping", CCMapping.toString());
						}

					} // if GetSpecializedWallTripsResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetSpecializedWallTripsResult")) {
						if (tagname.equalsIgnoreCase("ServiceID"))
							tempResponsePullParser.wt.tripNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("ConfNumb"))
							tempResponsePullParser.wt.ConfirmNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupDateTime"))
							try {
								tempResponsePullParser.wt.PUTime = MRMS_DateFormat.parse(textBtwTags);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						else if (tagname.equalsIgnoreCase("PickupAddress"))
							tempResponsePullParser.wt.PUaddress = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupZone"))
							tempResponsePullParser.wt.PickupZone = textBtwTags;
						else if (tagname.equalsIgnoreCase("DropZone"))
							tempResponsePullParser.wt.DropZone = textBtwTags;
						else if (tagname.equalsIgnoreCase("EstMiles"))
							tempResponsePullParser.wt.EstMiles = textBtwTags;
						else if (tagname.equalsIgnoreCase("EstFare"))
							tempResponsePullParser.wt.EstFare = textBtwTags;
						else if (tagname.equalsIgnoreCase("PhoneNumber"))
							tempResponsePullParser.wt.PhoneNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("CustomerName"))
							tempResponsePullParser.wt.CustomerName = textBtwTags;
						else if (tagname.equalsIgnoreCase("AMBPassengers"))
							tempResponsePullParser.wt.AMBPassengers = textBtwTags;
						else if (tagname.equalsIgnoreCase("WheelChairPassengers"))
							tempResponsePullParser.wt.WheelChairPassengers = textBtwTags;
						else if (tagname.equalsIgnoreCase("LOS"))
							tempResponsePullParser.wt.LOS = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupLat"))
							tempResponsePullParser.wt.PickUpLat = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupLong"))
							tempResponsePullParser.wt.PickUpLong = textBtwTags;
						else if (tagname.equalsIgnoreCase("bShowPhoneNumberOnTrip"))
							tempResponsePullParser.wt.ShowPhoneNumberOnTrip = Boolean.parseBoolean(textBtwTags);
						else if (tagname.equalsIgnoreCase("WallTrip")) {
							temp_wt = new WallTrip();
							temp_wt.tripNumber = tempResponsePullParser.wt.tripNumber;
							temp_wt.ConfirmNumber = tempResponsePullParser.wt.ConfirmNumber;
							temp_wt.PUTime = tempResponsePullParser.wt.PUTime;
							temp_wt.PUaddress = tempResponsePullParser.wt.PUaddress;
							temp_wt.PickupZone = tempResponsePullParser.wt.PickupZone;
							temp_wt.DropZone = tempResponsePullParser.wt.DropZone;
							temp_wt.EstMiles = tempResponsePullParser.wt.EstMiles;
							temp_wt.EstFare = tempResponsePullParser.wt.EstFare;
							temp_wt.PhoneNumber = tempResponsePullParser.wt.PhoneNumber;
							temp_wt.CustomerName = tempResponsePullParser.wt.CustomerName;
							temp_wt.AMBPassengers = tempResponsePullParser.wt.AMBPassengers;
							temp_wt.WheelChairPassengers = tempResponsePullParser.wt.WheelChairPassengers;
							temp_wt.LOS = tempResponsePullParser.wt.LOS;
							temp_wt.PickUpLat = tempResponsePullParser.wt.PickUpLat;
							temp_wt.PickUpLong = tempResponsePullParser.wt.PickUpLong;
							temp_wt.ShowPhoneNumberOnTrip = tempResponsePullParser.wt.ShowPhoneNumberOnTrip;
							tempResponsePullParser.wallTrips.add(temp_wt);
							Log.w("WallTrip", temp_wt.toString());
						}

					}// if GetWallTripsResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetWallTripsResult")) {
						if (tagname.equalsIgnoreCase("ServiceID"))
							tempResponsePullParser.wt.tripNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("ConfNumb"))
							tempResponsePullParser.wt.ConfirmNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupDateTime"))
							try {
								tempResponsePullParser.wt.PUTime = MRMS_DateFormat.parse(textBtwTags);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						else if (tagname.equalsIgnoreCase("PickupAddress"))
							tempResponsePullParser.wt.PUaddress = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupZone"))
							tempResponsePullParser.wt.PickupZone = textBtwTags;
						else if (tagname.equalsIgnoreCase("DropZone"))
							tempResponsePullParser.wt.DropZone = textBtwTags;
						else if (tagname.equalsIgnoreCase("EstMiles"))
							tempResponsePullParser.wt.EstMiles = textBtwTags;
						else if (tagname.equalsIgnoreCase("EstFare"))
							tempResponsePullParser.wt.EstFare = textBtwTags;
						else if (tagname.equalsIgnoreCase("PhoneNumber"))
							tempResponsePullParser.wt.PhoneNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("CustomerName"))
							tempResponsePullParser.wt.CustomerName = textBtwTags;
						else if (tagname.equalsIgnoreCase("AMBPassengers"))
							tempResponsePullParser.wt.AMBPassengers = textBtwTags;
						else if (tagname.equalsIgnoreCase("WheelChairPassengers"))
							tempResponsePullParser.wt.WheelChairPassengers = textBtwTags;
						else if (tagname.equalsIgnoreCase("LOS"))
							tempResponsePullParser.wt.LOS = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupLat"))
							tempResponsePullParser.wt.PickUpLat = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickupLong"))
							tempResponsePullParser.wt.PickUpLong = textBtwTags;
						else if (tagname.equalsIgnoreCase("bShowPhoneNumberOnTrip"))
							tempResponsePullParser.wt.ShowPhoneNumberOnTrip = Boolean.parseBoolean(textBtwTags);
						else if (tagname.equalsIgnoreCase("WallTrip")) {
							temp_wt = new WallTrip();
							temp_wt.tripNumber = tempResponsePullParser.wt.tripNumber;
							temp_wt.ConfirmNumber = tempResponsePullParser.wt.ConfirmNumber;
							temp_wt.PUTime = tempResponsePullParser.wt.PUTime;
							temp_wt.PUaddress = tempResponsePullParser.wt.PUaddress;
							temp_wt.PickupZone = tempResponsePullParser.wt.PickupZone;
							temp_wt.DropZone = tempResponsePullParser.wt.DropZone;
							temp_wt.EstMiles = tempResponsePullParser.wt.EstMiles;
							temp_wt.EstFare = tempResponsePullParser.wt.EstFare;
							temp_wt.PhoneNumber = tempResponsePullParser.wt.PhoneNumber;
							temp_wt.CustomerName = tempResponsePullParser.wt.CustomerName;
							temp_wt.AMBPassengers = tempResponsePullParser.wt.AMBPassengers;
							temp_wt.WheelChairPassengers = tempResponsePullParser.wt.WheelChairPassengers;
							temp_wt.LOS = tempResponsePullParser.wt.LOS;
							temp_wt.PickUpLat = tempResponsePullParser.wt.PickUpLat;
							temp_wt.PickUpLong = tempResponsePullParser.wt.PickUpLong;
							temp_wt.ShowPhoneNumberOnTrip = tempResponsePullParser.wt.ShowPhoneNumberOnTrip;
							tempResponsePullParser.wallTrips.add(temp_wt);
							Log.w("WallTrip", temp_wt.toString());
						}

					}// if GetCannedMessagesResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetCannedMessagesResult")) {
						if (tagname.equalsIgnoreCase("Message")) {
							tempResponsePullParser.cannedMsgsList.add(textBtwTags);
						}

					}// if Process_SaleResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("SDGetAdjacentZonesResult")) {
						if (tagname.equalsIgnoreCase("ZoneName")) {
							tempResponsePullParser.ZoneNameList.add(textBtwTags);
						} else if (tagname.equalsIgnoreCase("AdjZoneName")) {
							tempResponsePullParser.AdjZoneNameList.add(textBtwTags);
						}

					}// if Process_SaleResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("Process_SaleResult")) {
						if (tagname.equalsIgnoreCase("TransactionId"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_TransactionId(textBtwTags);
						else if (tagname.equalsIgnoreCase("Amount1"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Amount1(textBtwTags);
						else if (tagname.equalsIgnoreCase("Message"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Message(textBtwTags);
						else if (tagname.equalsIgnoreCase("AuthorizationCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_AuthCode(textBtwTags);
						else if (tagname.equalsIgnoreCase("JobID"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_JobId(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResponseType"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResponseType(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResultCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResultCode(textBtwTags);
						else if (tagname.equalsIgnoreCase("BalanceAmt"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_BalanceAmt(textBtwTags);

					}// if Process_PreAuthResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("Process_PreAuthResult")) {
						if (tagname.equalsIgnoreCase("TransactionId"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_TransactionId(textBtwTags);
						else if (tagname.equalsIgnoreCase("Amount1"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Amount1(textBtwTags);
						else if (tagname.equalsIgnoreCase("Message"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Message(textBtwTags);
						else if (tagname.equalsIgnoreCase("AuthorizationCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_AuthCode(textBtwTags);
						else if (tagname.equalsIgnoreCase("JobID"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_JobId(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResponseType"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResponseType(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResultCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResultCode(textBtwTags);

					}// if Process_PostAuthResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("Process_PostAuthResult")) {
						if (tagname.equalsIgnoreCase("TransactionId"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_TransactionId(textBtwTags);
						else if (tagname.equalsIgnoreCase("Amount1"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Amount1(textBtwTags);
						else if (tagname.equalsIgnoreCase("Message"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Message(textBtwTags);
						else if (tagname.equalsIgnoreCase("AuthorizationCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_AuthCode(textBtwTags);
						else if (tagname.equalsIgnoreCase("JobID"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_JobId(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResponseType"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResponseType(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResultCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResultCode(textBtwTags);

					}// if Process_InquiryResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("Process_InquiryResult")) {
						if (tagname.equalsIgnoreCase("TransactionId"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_TransactionId(textBtwTags);
						else if (tagname.equalsIgnoreCase("Amount1"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Amount1(textBtwTags);
						else if (tagname.equalsIgnoreCase("Message"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_Message(textBtwTags);
						else if (tagname.equalsIgnoreCase("AuthorizationCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_AuthCode(textBtwTags);
						else if (tagname.equalsIgnoreCase("JobID"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_JobId(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResponseType"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResponseType(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResultCode"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_ResultCode(textBtwTags);
						else if (tagname.equalsIgnoreCase("BalanceAmt"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_BalanceAmt(textBtwTags);
						else if (tagname.equalsIgnoreCase("MaxCardCharge"))
							tempResponsePullParser.cc_AuthorizeDotNet_MJM.set_MaxCardCharge(textBtwTags);

					}// if UpdateSmartDeviceMeterInfoResult

					else if (tempResponsePullParser.responseType.equalsIgnoreCase("UpdateSmartDeviceMeterInfoResult")) {
						if (tagname.equalsIgnoreCase("UpdateSmartDeviceMeterInfoResult"))
							tempResponsePullParser.responseString = textBtwTags;

					}// if SDGetGeneralSettingsResult

					else if (tempResponsePullParser.responseType.equalsIgnoreCase("SDGetGeneralSettingsResult")) {
						if (tagname.equalsIgnoreCase("AllowBTMeterOnSDStartUp"))
							tempResponsePullParser.generalSettings.set_AllowBTMeterOnSDStartUp(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("AllowCMTVivotechOnSDStartUp"))
							tempResponsePullParser.generalSettings.set_AllowCMTVivotechOnSDStartUp(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("AllowCentrodyneMeterOnSDStartUp"))
							tempResponsePullParser.generalSettings.set_AllowCentrodyneMeterOnSDStartUp(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("AllowBlueBamboOnSDStartUp"))
							tempResponsePullParser.generalSettings.set_AllowBlueBamboOnSDStartUp(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("AllowAudioCommandsOnSDStartUp"))
							tempResponsePullParser.generalSettings.set_AllowAudioCommandsOnSDStartUp(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowEstMilesOnDevice"))
							tempResponsePullParser.generalSettings.set_ShowEstMilesOnDevice(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("GoogleMapAPIKey"))
							tempResponsePullParser.generalSettings.set_GoogleMapAPIKey(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDCallOutRequestPrompt"))
							tempResponsePullParser.generalSettings.set_SDCallOutRequestPrompt(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDCallOutRequestPrompt_ar"))
							tempResponsePullParser.generalSettings.set_SDCallOutRequestPrompt_ar(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDEnableBreak"))
							tempResponsePullParser.generalSettings.set_SDEnableBreak(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnablePPT"))
							tempResponsePullParser.generalSettings.set_SDEnablePPT(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnableEmergency"))
							tempResponsePullParser.generalSettings.set_SDEnableEmergency(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnableCalcEstOnDropped"))
							tempResponsePullParser.generalSettings.set_SDEnableCalcEstOnDropped(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDCalcEstViaMRMSService"))
							tempResponsePullParser.generalSettings.set_SDCalcEstViaMRMSService(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("TipVoiceEnabled"))
							tempResponsePullParser.generalSettings.set_TipVoiceEnabled(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("HideCostOnSDByFundingSource")) {
							try {
								String[] current_Value = textBtwTags.split(",");
								for (int i = 0; i < current_Value.length; i++)
									tempResponsePullParser.generalSettings.set_HideCostOnSDByFundingSource(current_Value[i]);
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_HideCostOnSDByFundingSource("");
							}
						} else if (tagname.equalsIgnoreCase("ReverseGeocodeFromMRMSService"))
							tempResponsePullParser.generalSettings.set_ReverseGeocodeFromMRMSService(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowManifestWallOnSD"))
							tempResponsePullParser.generalSettings.set_ShowManifestWallOnSD(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SendBidOffers"))
							tempResponsePullParser.generalSettings.set_SendBidOffers(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("RestrictSoftDropIfMeterConnected"))
							tempResponsePullParser.generalSettings.set_RestrictSoftDropIfMeterConnected(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("AllowedSpeedForMessaging"))
							try {
								tempResponsePullParser.generalSettings.set_AllowedSpeedForMessaging(Float.parseFloat(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_AllowedSpeedForMessaging(Float.parseFloat("100"));
							}
						else if (tagname.equalsIgnoreCase("TimerForCradleLogout"))
							try {
								tempResponsePullParser.generalSettings.set_TimerForCradleLogout(Long.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_TimerForCradleLogout(Long.valueOf("6000"));
							}
						else if (tagname.equalsIgnoreCase("AllowableCallOutDistance"))
							try {
								tempResponsePullParser.generalSettings.set_AllowableCallOutDistance(Double.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_AllowableCallOutDistance(Long.valueOf("0.0"));
							}
						else if (tagname.equalsIgnoreCase("DeviceMessageScreenConfig"))
							tempResponsePullParser.generalSettings.set_DeviceMessageScreenConfig(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDMaxAllowedBreaksInOneDay"))
							try {
								tempResponsePullParser.generalSettings.set_SDMaxAllowedBreaksInOneDay(Integer.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_SDMaxAllowedBreaksInOneDay(Integer.valueOf("1"));
							}
						else if (tagname.equalsIgnoreCase("WallTripSortingOption"))
							try {
								tempResponsePullParser.generalSettings.set_WallTripDistanceByGoogle(Integer.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_WallTripDistanceByGoogle(Integer.valueOf("1"));
							}
						else if (tagname.equalsIgnoreCase("ShowReceiptPrintingDialog"))
							tempResponsePullParser.generalSettings.set_ShowReceiptPrintingDialog(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("EnableDialiePackageOnDevice"))
							tempResponsePullParser.generalSettings.set_EnableDialiePackageOnDevice(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("bShuttleAgentScreen"))
							tempResponsePullParser.generalSettings.set_bShuttleAgentScreen(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDShowFlaggerConfirmation"))
							tempResponsePullParser.generalSettings.set_SDShowFlaggerConfirmation(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDMaxLengthOfTripList"))
							tempResponsePullParser.generalSettings.set_SDTripListSize(textBtwTags);
						else if (tagname.equalsIgnoreCase("ShowEstdCostOnSDByFundingSource")) {
							try {
								String[] current_Value = textBtwTags.split(",");
								for (int i = 0; i < current_Value.length; i++)
									tempResponsePullParser.generalSettings.set_ShowEstdCostOnSDByFundingSource(current_Value[i]);
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_ShowEstdCostOnSDByFundingSource("");
							}
						} else if (tagname.equalsIgnoreCase("ShowSDDriverPhoto"))
							tempResponsePullParser.generalSettings.set_ShowSDDriverPhoto(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDAVLOnStatus"))
							tempResponsePullParser.generalSettings.set_ShowSDAVLOnStatus(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDStandRankOnStatus"))
							tempResponsePullParser.generalSettings.set_ShowSDStandRankOnStatus(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDTaxiMeterOnStatus"))
							tempResponsePullParser.generalSettings.set_ShowSDTaxiMeterOnStatus(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDBackSeatOnStatus"))
							tempResponsePullParser.generalSettings.set_ShowSDBackSeatOnStatus(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDApartmentOnTripDetail"))
							tempResponsePullParser.generalSettings.set_ShowSDApartmentOnTripDetail(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDFundingSourceOnTripDetail"))
							tempResponsePullParser.generalSettings.set_ShowSDFundingSourceOnTripDetail(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDPaymentTypeOnTripDetail"))
							tempResponsePullParser.generalSettings.set_ShowSDPaymentTypeOnTripDetail(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDCoPayOnTripDetail"))
							tempResponsePullParser.generalSettings.set_ShowSDCoPayOnTripDetail(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowSDOnlyFareOnPaymentScreen"))
							tempResponsePullParser.generalSettings.set_ShowSDOnlyFareOnPaymentScreen(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowStandsOnSD"))
							tempResponsePullParser.generalSettings.set_ShowStandsOnSD(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowNearZoneFeatureOnSD"))
							tempResponsePullParser.generalSettings.set_ShowNearZoneFeatureOnSD(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDDefaultLanguageSelection"))
							tempResponsePullParser.generalSettings.set_SDDefaultLanguageSelection(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDDropNavigationWithMap"))
							tempResponsePullParser.generalSettings.set_SDDropNavigationWithMap(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowClientPhoneNoOnReceipt"))
							tempResponsePullParser.generalSettings.set_ShowClientPhoneNoOnReceipt(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ResponseIDToRemoveTripFromWall"))
							tempResponsePullParser.generalSettings.set_ResponseIDToRemoveTripFromWall(textBtwTags);
						else if (tagname.equalsIgnoreCase("bArabClient"))
							tempResponsePullParser.generalSettings.set_bArabClient(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowTogglePickUpDropOffBTN"))
							tempResponsePullParser.generalSettings.set_ShowTogglePickUpDropOffBTN(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("HEXColor"))
							tempResponsePullParser.generalSettings.set_HEXColor(textBtwTags);
						else if (tagname.equalsIgnoreCase("Allow_Book_In_AZ"))
							tempResponsePullParser.generalSettings.set_Allow_Book_In_AZ(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("ShowHandShakeButtonOnLogin"))
							tempResponsePullParser.generalSettings.set_ShowHandShakeButtonOnLogin(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SendDriverPicToVeriFone"))
							tempResponsePullParser.generalSettings.set_SendDriverPicToVeriFone(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("AllowDetailedLogInFileAndSQL"))
							tempResponsePullParser.generalSettings.set_AllowDetailedLogInFileAndSQL(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDFlaggerButtonSize")) {
							float size = 35;
							try {
								size = Float.parseFloat(textBtwTags);
							} catch (Exception ex) {
							}
							tempResponsePullParser.generalSettings.set_SDFlaggerButtonSize(size);
						}

						else if (tagname.equalsIgnoreCase("SDShowLanguageChangeOption"))
							tempResponsePullParser.generalSettings.set_SDShowLanguageChangeOption(Boolean.valueOf(textBtwTags));

						else if (tagname.equalsIgnoreCase("SDUnitOfDistance"))
							tempResponsePullParser.generalSettings.set_SDUnitOfDistance(textBtwTags);

						else if (tagname.equalsIgnoreCase("SDUnitOfCurrency"))
							tempResponsePullParser.generalSettings.set_SDUnitOfCurrency(textBtwTags);

						else if (tagname.equalsIgnoreCase("PPV_UsePPVModule"))
							tempResponsePullParser.generalSettings.set_PPV_UsePPVModule(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDBreakEmergencyPPTPosition"))
							tempResponsePullParser.generalSettings.set_SDBreakEmergencyPPTPosition(textBtwTags);
						else if (tagname.equalsIgnoreCase("EnableAudioForMessageUtility"))
							tempResponsePullParser.generalSettings.set_EnableAudioForMessageUtility(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDShowProceedToPickupOnTripOffer"))
							tempResponsePullParser.generalSettings.set_SDShowProceedToPickupOnTripOffer(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDReprintTimeOutSec"))
							try {
								tempResponsePullParser.generalSettings.set_SDReprintTimeOutSec(Integer.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_SDReprintTimeOutSec(Integer.valueOf("15"));
							}
						else if (tagname.equalsIgnoreCase("SDVFCashVoucherDialogTimeOutSec"))
							try {
								tempResponsePullParser.generalSettings.set_SDVFCashVoucherDialogTimeOutSec(Integer.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_SDVFCashVoucherDialogTimeOutSec(Integer.valueOf("15"));
							}
						else if (tagname.equalsIgnoreCase("SDEnableVoiceIfNewTripAddedOnWall"))
							tempResponsePullParser.generalSettings.set_SDEnableVoiceIfNewTripAddedOnWall(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDShowPassengerNameOnWall"))
							tempResponsePullParser.generalSettings.set_SDShowPassengerNameOnWall(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDBreakActionOnSingleTap"))
							tempResponsePullParser.generalSettings.set_SDBreakActionOnSingleTap(Boolean.valueOf(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDBreakColor"))
							tempResponsePullParser.generalSettings.set_SDBreakColor(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDResumeColor"))
							tempResponsePullParser.generalSettings.set_SDResumeColor(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDShowPUDateTimeOnTripDetail"))
							tempResponsePullParser.generalSettings.set_SDShowPUDateTimeOnTripDetail(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDShowVoucherButton"))
							tempResponsePullParser.generalSettings.set_SDShowVoucherButton(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("CreditCardFeature"))
							tempResponsePullParser.generalSettings.set_CreditCardFeature(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnableTripListSynchronization"))
							tempResponsePullParser.generalSettings.set_SDEnableTripListSynchronization(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDShowMileageOnStatusTab"))
							tempResponsePullParser.generalSettings.set_SDShowMileageOnStatusTab(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnableSignatureFeature"))
							tempResponsePullParser.generalSettings.set_SDEnableSignatureFeature(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDCentralizedAsteriskService"))
							tempResponsePullParser.generalSettings.set_SDCentralizedAsteriskService(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDEnableCentralizedAsteriskService"))
							tempResponsePullParser.generalSettings.set_SDEnableCentralizedAsteriskService(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("ASCS_HelpLine_Number"))
							tempResponsePullParser.generalSettings.set_ASCS_HelpLine_Number(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDEmergencyConfirmation"))
							tempResponsePullParser.generalSettings.set_SDEmergencyConfirmation(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("NotAllowActionIfAway"))
							tempResponsePullParser.generalSettings.set_NotAllowActionIfAway(textBtwTags);
						else if (tagname.equalsIgnoreCase("MessageTypeIfActionNotAllowed"))
							tempResponsePullParser.generalSettings.set_MessageTypeIfActionNotAllowed(textBtwTags);
						else if (tagname.equalsIgnoreCase("ShowAddressOnWall"))
							tempResponsePullParser.generalSettings.set_ShowAddressOnWall(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("CompanyName_Receipt"))
							tempResponsePullParser.generalSettings.set_CompanyName_Receipt(textBtwTags);
						else if (tagname.equalsIgnoreCase("CompanyURL"))
							tempResponsePullParser.generalSettings.set_CompanyURL(textBtwTags);
						else if (tagname.equalsIgnoreCase("SDEnableManualFlagger"))
							tempResponsePullParser.generalSettings.set_SDEnableManualFlagger(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnableTwoStepPaymentProcessing"))
							tempResponsePullParser.generalSettings.set_SDEnableTwoStepPaymentProcessing(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDPaymentButtonCaptionFor2ndStep"))
							tempResponsePullParser.generalSettings.set_SDPaymentButtonCaptionFor2ndStep(textBtwTags);
						else if (tagname.equalsIgnoreCase("Allow_Promotion_In_MARS_SDApp_Both"))
							try {
								tempResponsePullParser.generalSettings.set_Allow_Promotion_In_MARS_SDApp_Both(Integer.parseInt(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_Allow_Promotion_In_MARS_SDApp_Both(Integer.parseInt("1"));
							}
						else if (tagname.equalsIgnoreCase("SDShowFontChangeOption"))
							tempResponsePullParser.generalSettings.set_SDShowFontChangeOption(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDEnableMeterLocking"))
							tempResponsePullParser.generalSettings.set_SDEnableMeterLocking(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDRingerCountForTripOffer"))
							try {
								tempResponsePullParser.generalSettings.set_SDRingerCountForTripOffer(Integer.valueOf(textBtwTags));
							} catch (Exception e) {
								tempResponsePullParser.generalSettings.set_SDRingerCountForTripOffer(Integer.valueOf("10"));
							}
						else if (tagname.equalsIgnoreCase("SDShowServiceID"))
							tempResponsePullParser.generalSettings.set_SDShowServiceID(Boolean.parseBoolean(textBtwTags));
						else if (tagname.equalsIgnoreCase("SDShowPhoneandIMEI"))
							tempResponsePullParser.generalSettings.set_SDShowPhoneandIMEI(Boolean.parseBoolean(textBtwTags));
                        else if (tagname.equalsIgnoreCase("SDAsteriskExt"))
                            tempResponsePullParser.generalSettings.set_SDAsteriskExt(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SDAsteriskPwd"))
                            tempResponsePullParser.generalSettings.set_SDAsteriskPwt(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SDAsteriskServer"))
                            tempResponsePullParser.generalSettings.set_SDAsteriskServer(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SDAsteriskHangUpTime"))
                            tempResponsePullParser.generalSettings.set_SDAsteriskHangUpTime(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SDAsteriskDispatcherExt"))
                            tempResponsePullParser.generalSettings.set_SDAsteriskDispatcherExt(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SDEnableAsteriskExtension"))
                            tempResponsePullParser.generalSettings.set_SDEnableAsteriskExtension(Boolean.parseBoolean(textBtwTags));
                        else if (tagname.equalsIgnoreCase("MARS_HelpLine_Number"))
                            tempResponsePullParser.generalSettings.set_MARS_HelpLine_Number(textBtwTags);
                        else if (tagname.equalsIgnoreCase("TSPID"))
                            tempResponsePullParser.generalSettings.set_TSPID(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SIPExtPattern"))
                            tempResponsePullParser.generalSettings.set_SIPExtPattern(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SIPPwdPattern"))
                            tempResponsePullParser.generalSettings.set_SIPPwdPattern(textBtwTags);
                        else if (tagname.equalsIgnoreCase("ITCInloadAPI"))
                            tempResponsePullParser.generalSettings.set_InLoadAPI_URL(textBtwTags);
                        else if (tagname.equalsIgnoreCase("SDOnlyNearZoneMode"))
                            tempResponsePullParser.generalSettings.set_SDOnlyNearZoneMode(Boolean.parseBoolean(textBtwTags));
                        else if (tagname.equalsIgnoreCase("WallRefreshTimer"))
                            try {
                                tempResponsePullParser.generalSettings.set_WallRefreshTimer(Integer.parseInt(textBtwTags));
                            } catch (Exception e) {
                                tempResponsePullParser.generalSettings.set_WallRefreshTimer(Integer.valueOf("40"));
                            }
                        else if (tagname.equalsIgnoreCase("SDEnableOdometerInput"))
                            try {
                                tempResponsePullParser.generalSettings.set_SDEnableOdometerInput(Integer.parseInt(textBtwTags));
                            } catch (Exception e) {
                                tempResponsePullParser.generalSettings.set_SDEnableOdometerInput(Integer.valueOf("0"));
                            }
                        else if (tagname.equalsIgnoreCase("SDEnableReceiptEmail"))
                            tempResponsePullParser.generalSettings.set_SDEnableReceiptEmail(Boolean.parseBoolean(textBtwTags));


					}// if CalculateRouteByStreetAddressResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("TopupCustomerBalanceFromBookinAppResult")) {
						if (tagname.equalsIgnoreCase("Amount"))
							tempResponsePullParser.topupCustomerBalance.set_Amount(textBtwTags);
						else if (tagname.equalsIgnoreCase("ResultMsg"))
							tempResponsePullParser.topupCustomerBalance.set_ResultMsg(textBtwTags);
						else if (tagname.equalsIgnoreCase("CurrentBalance"))
							tempResponsePullParser.topupCustomerBalance.set_CurrentBalance(textBtwTags);

					}// if ReverseGeoCodeBylatlngResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("CalculateRouteByStreetAddressWithCostEstimatesResult")) {
						if (tagname.equalsIgnoreCase("Cost"))
							tempResponsePullParser.fareEstimation.set_fare(textBtwTags);
						if (tagname.equalsIgnoreCase("Distance"))
							tempResponsePullParser.fareEstimation.set_distance(textBtwTags);
						if (tagname.equalsIgnoreCase("Time"))
							tempResponsePullParser.fareEstimation.set_time(textBtwTags);

					}// if ReverseGeoCodeBylatlngResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("ReverseGeoCodeBylatlngResult")) {
						if (tagname.equalsIgnoreCase("ReverseGeoCodeBylatlngResult"))
							tempResponsePullParser.addressString = textBtwTags == null ? "" : textBtwTags;

					}
					// if GetManifestRouteInfoSummaryResult
					else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetManifestSummaryInfoResult")) {
						if (tagname.equalsIgnoreCase("ManifestNumber"))
							tempResponsePullParser.mwt.ManifestNumber = textBtwTags;
						else if (tagname.equalsIgnoreCase("PickZoneName"))
							tempResponsePullParser.mwt.PickupZone = textBtwTags;
						else if (tagname.equalsIgnoreCase("DropZoneName"))
							tempResponsePullParser.mwt.DropZone = textBtwTags;
						else if (tagname.equalsIgnoreCase("RouteStartTime"))
							tempResponsePullParser.mwt.RouteStartTime = textBtwTags;
						else if (tagname.equalsIgnoreCase("RouteEndTime"))
							tempResponsePullParser.mwt.RouteEndTime = textBtwTags;
						else if (tagname.equalsIgnoreCase("stRouteStartTime"))
							try {
								tempResponsePullParser.mwt.stRouteStartTime = MRMS_DateFormat.parse(textBtwTags);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						else if (tagname.equalsIgnoreCase("stRouteEndTime"))
							try {
								tempResponsePullParser.mwt.stRouteEndTime = MRMS_DateFormat.parse(textBtwTags);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						else if (tagname.equalsIgnoreCase("MaxWC"))
							tempResponsePullParser.mwt.MaxWC = textBtwTags;
						else if (tagname.equalsIgnoreCase("MaxAmbulatory"))
							tempResponsePullParser.mwt.MaxAmbulatory = textBtwTags;
						else if (tagname.equalsIgnoreCase("TotalNoOfTrip"))
							tempResponsePullParser.mwt.TotalNoOfTrip = textBtwTags;
						else if (tagname.equalsIgnoreCase("TotalDistMile"))
							tempResponsePullParser.mwt.TotalDistMile = textBtwTags;
						else if (tagname.equalsIgnoreCase("TotalRouteMin"))
							tempResponsePullParser.mwt.TotalRouteMin = textBtwTags;
						else if (tagname.equalsIgnoreCase("TotalCost"))
							tempResponsePullParser.mwt.TotalCost = textBtwTags;

						else if (tagname.equalsIgnoreCase("RouteSummary")) {
							temp_mwt = new ManifestWallTrip();
							temp_mwt.ManifestNumber = tempResponsePullParser.mwt.ManifestNumber;
							temp_mwt.PickupZone = tempResponsePullParser.mwt.PickupZone;
							temp_mwt.DropZone = tempResponsePullParser.mwt.DropZone;
							temp_mwt.RouteStartTime = tempResponsePullParser.mwt.RouteStartTime;
							temp_mwt.RouteEndTime = tempResponsePullParser.mwt.RouteEndTime;
							temp_mwt.DropZone = tempResponsePullParser.mwt.DropZone;
							temp_mwt.stRouteStartTime = tempResponsePullParser.mwt.stRouteStartTime;
							temp_mwt.stRouteEndTime = tempResponsePullParser.mwt.stRouteEndTime;
							temp_mwt.MaxWC = tempResponsePullParser.mwt.MaxWC;
							temp_mwt.MaxAmbulatory = tempResponsePullParser.mwt.MaxAmbulatory;
							temp_mwt.TotalNoOfTrip = tempResponsePullParser.mwt.TotalNoOfTrip;
							temp_mwt.TotalDistMile = tempResponsePullParser.mwt.TotalDistMile;
							temp_mwt.TotalRouteMin = tempResponsePullParser.mwt.TotalRouteMin;
							temp_mwt.TotalCost = tempResponsePullParser.mwt.TotalCost;
							tempResponsePullParser.manifestWallTrips.add(temp_mwt);
							Log.w("ManifestWallTrip", temp_mwt.toString());
						}

					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetLiveMiscInfoResult")) {
						if (tagname.equalsIgnoreCase("TotalBreakCounts"))
							tempResponsePullParser.DriverTakenBreaks = textBtwTags;

					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetPersonBalanceResult")) {
						if (tagname.equalsIgnoreCase("GetPersonBalanceResult"))
							tempResponsePullParser.Balance = textBtwTags;

					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetBalanceAndBlackListStatusResult")) {
						if (tagname.equalsIgnoreCase("GetBalanceAndBlackListStatusResult"))
							tempResponsePullParser.BalanceAndBlackList = textBtwTags;

					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("GetSDZoneListResult")) {
						if (tagname.equalsIgnoreCase("vName"))
							tempResponsePullParser.sdZoneList.Zonename = textBtwTags;
						else if (tagname.equalsIgnoreCase("vDescription")) {
							tempResponsePullParser.sdZoneList.Zonedescription = textBtwTags;
							tempResponsePullParser.map_zonelist.put(tempResponsePullParser.sdZoneList.Zonename, tempResponsePullParser.sdZoneList.Zonedescription);
						}

					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("PPV_CheckallowedBalanceOnTripCompletionResult")) {
						if (tagname.equalsIgnoreCase("Result")) {
							tempResponsePullParser.balancecalculation.set_Result(Boolean.parseBoolean(textBtwTags));
						}

					} else if (tempResponsePullParser.responseType.equalsIgnoreCase("ClassOfServiceRates")){
                        if (tagname.equalsIgnoreCase("ADC"))
                            tempResponsePullParser.smr._ADC = textBtwTags;
                        else if (tagname.equalsIgnoreCase("ADU"))
                            tempResponsePullParser.smr._ADU = textBtwTags;
                        else if (tagname.equalsIgnoreCase("APF"))
                                tempResponsePullParser.smr._APF = textBtwTags;
                        else if (tagname.equalsIgnoreCase("ATC"))
                            tempResponsePullParser.smr._ATC = textBtwTags;
                        else if (tagname.equalsIgnoreCase("ATU"))
                            tempResponsePullParser.smr._ATU = textBtwTags;
                        else if (tagname.equalsIgnoreCase("PUM"))
                            tempResponsePullParser.smr._PUM = textBtwTags;
                        else if (tagname.equalsIgnoreCase("CPC"))
                            tempResponsePullParser.smr._CPC = textBtwTags;
                        else if (tagname.equalsIgnoreCase("PUT"))
                            tempResponsePullParser.smr._PUT = textBtwTags;
                        else if (tagname.equalsIgnoreCase("APC"))
                            tempResponsePullParser.smr._APC = textBtwTags;
                        else if (tagname.equalsIgnoreCase("iClassOfServiceID"))
                            tempResponsePullParser.smr._ClassOfServiceID = textBtwTags;
                        else if (tagname.equalsIgnoreCase("bDefaultClassofService"))
                            tempResponsePullParser.smr._DefaultClassOfService = textBtwTags;

                        else if (tagname.equalsIgnoreCase("row")) {
                            temp_smr = new WS_Response.ClassofService();
                            temp_smr._ADC = tempResponsePullParser.smr._ADC;
                            temp_smr._ADU = tempResponsePullParser.smr._ADU;
                            temp_smr._APF = tempResponsePullParser.smr._APF;
                            temp_smr._ATC = tempResponsePullParser.smr._ATC;
                            temp_smr._ATU = tempResponsePullParser.smr._ATU;
                            temp_smr._PUM = tempResponsePullParser.smr._PUM;
                            temp_smr._CPC = tempResponsePullParser.smr._CPC;
                            temp_smr._PUT = tempResponsePullParser.smr._PUT;
                            temp_smr._APC = tempResponsePullParser.smr._APC;
                            temp_smr._ClassOfServiceID = tempResponsePullParser.smr._ClassOfServiceID;
                            temp_smr._DefaultClassOfService = tempResponsePullParser.smr._DefaultClassOfService;

                            tempResponsePullParser.softmeterrates.add(temp_smr);

                        }

                    } else if (tempResponsePullParser.responseType.equalsIgnoreCase("SendPaymentReceiptToCustomer")){
                        if (tagname.equalsIgnoreCase("row"))
                            tempResponsePullParser.re.response = Boolean.parseBoolean(textBtwTags);
                    }


					break;

				default :
					break;
			}
			eventType = xpp.next();
		}

		return tempResponsePullParser;
	}
}
