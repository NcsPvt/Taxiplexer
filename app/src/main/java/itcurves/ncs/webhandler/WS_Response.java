package itcurves.ncs.webhandler;

import itcurves.ncs.CannedMessage;
import itcurves.ncs.Constants;
import itcurves.ncs.ManifestWallTrip;
import itcurves.ncs.WallTrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WS_Response {

	public boolean error = false;
	public String errorString = "";
	public String responseType;
	public KillApps killAppsResp;
	public CCProcessingCompany CCProcessor;
	public Map<String, CCProcessingCompany> cardProcessorList;
	public CCMappings CCMapping;
	public Map<String, CCMappings> CardMappingList;
	public WallTrip wt;
	public ManifestWallTrip mwt;
	public ArrayList<WallTrip> wallTrips;
	public ArrayList<String> tripList;
	public CannedMessage cm;
	public ArrayList<CannedMessage> cannedMessages;
	public ArrayList<String> cannedMsgsList;
	public ArrayList<String> ZoneNameList;
	public ArrayList<String> AdjZoneNameList;
	public CC_AuthorizeDotNet_MJM cc_AuthorizeDotNet_MJM;
	public String responseString;
	public GeneralSettings generalSettings;
	public TopupCustomerBalance topupCustomerBalance;
	public FareEstimation fareEstimation;
	public String addressString;
	public ArrayList<ManifestWallTrip> manifestWallTrips;
	public String DriverTakenBreaks;
	public String Balance;
	public String BalanceAndBlackList;
	public SdZoneList sdZoneList;
	// public ArrayList<SdZoneList> arrsdZoneList;
	public Map<String, String> map_zonelist;
	public VehicleMilage updateVehicleMilage;
	public BalanceCalculationForVoucher balancecalculation;
    public ClassofService smr;
    public ArrayList<ClassofService> softmeterrates;
    public ReceiptEmail re;

	public WS_Response(String respType) {

		this.responseType = respType;

		if (respType != null) {
			if (respType.equalsIgnoreCase("getSmartDeviceApplicationsResult"))
				killAppsResp = new KillApps();
			else if (respType.equalsIgnoreCase("GetCreditCardProcessingCompanyResult")) {
				cardProcessorList = new HashMap<String, CCProcessingCompany>();
				CCProcessor = new CCProcessingCompany();
			} else if (respType.equalsIgnoreCase("GetCreditCardMappingsResult")) {
				CardMappingList = new HashMap<String, CCMappings>();
				CCMapping = new CCMappings("", "");
			} else if (respType.equalsIgnoreCase("GetSpecializedWallTripsResult")) {
				wallTrips = new ArrayList<WallTrip>();
				wt = new WallTrip();
			} else if (respType.equalsIgnoreCase("GetWallTripsResult")) {
				wallTrips = new ArrayList<WallTrip>();
				wt = new WallTrip();
			} else if (respType.equalsIgnoreCase("GetMessageHistoryResult")) {
				cannedMessages = new ArrayList<CannedMessage>();
				cm = new CannedMessage();
			} else if (respType.equalsIgnoreCase("GetCannedMessagesResult"))
				cannedMsgsList = new ArrayList<String>();
			else if (respType.equalsIgnoreCase("Process_SaleResult") || respType.equalsIgnoreCase("Process_PreAuthResult")
					|| respType.equalsIgnoreCase("Process_PostAuthResult")
					|| respType.equalsIgnoreCase("Process_InquiryResult"))
				cc_AuthorizeDotNet_MJM = new CC_AuthorizeDotNet_MJM();
			else if (respType.equalsIgnoreCase("UpdateSmartDeviceMeterInfoResult"))
				responseString = "";
			else if (respType.equalsIgnoreCase("SDGetGeneralSettingsResult"))
				generalSettings = new GeneralSettings();
			else if (respType.equalsIgnoreCase("SDGetAdjacentZonesResult")) {
				ZoneNameList = new ArrayList<String>();
				AdjZoneNameList = new ArrayList<String>();
			} else if (respType.equalsIgnoreCase("CalculateRouteByStreetAddressWithCostEstimatesResult"))
				fareEstimation = new FareEstimation();
			else if (respType.equalsIgnoreCase("ReverseGeoCodeBylatlngResult"))
				addressString = "";
			else if (respType.equalsIgnoreCase("GetLiveMiscInfoResult"))
				DriverTakenBreaks = "";
			else if (respType.equalsIgnoreCase("GetManifestSummaryInfoResult")) {
				manifestWallTrips = new ArrayList<ManifestWallTrip>();
				mwt = new ManifestWallTrip();
			} else if (respType.equalsIgnoreCase("GetAssignedAndPendingTripsInStringResult"))
				tripList = new ArrayList<String>();
			else if (respType.equalsIgnoreCase("GetPersonBalanceResult"))
				Balance = "";
			else if (respType.equalsIgnoreCase("TopupCustomerBalanceFromBookinAppResult"))
				topupCustomerBalance = new TopupCustomerBalance();
			else if (respType.equalsIgnoreCase("GetSDZoneListResult")) {
				// arrsdZoneList = new ArrayList<SdZoneList>();
				map_zonelist = new HashMap<String, String>();
				sdZoneList = new SdZoneList();
			} else if (respType.equalsIgnoreCase("GetBalanceAndBlackListStatusResult"))
				BalanceAndBlackList = "";
			else if (respType.equalsIgnoreCase("VehicleMileageResponse"))
				updateVehicleMilage = new VehicleMilage();
			else if (respType.equalsIgnoreCase("PPV_CheckallowedBalanceOnTripCompletionResult"))
                balancecalculation = new BalanceCalculationForVoucher();
            else if (respType.equalsIgnoreCase("ClassOfServiceRates")) {
                softmeterrates = new ArrayList<ClassofService>();
                smr = new ClassofService();
            }
            else if (respType.equalsIgnoreCase("SendPaymentReceiptToCustomer"))
                re = new ReceiptEmail();

		}
	}
	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------------ KillApps Class ----------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class KillApps {
		public boolean kill_apps;
		public ArrayList<String> process_Name = new ArrayList<String>();

		public KillApps() {
			this.kill_apps = false;
		}

		public void setpName(String pName) {
			process_Name.add(pName);
		}
		public void setExcluded(boolean appType) {
			kill_apps = appType;
		}
		public boolean isExcluded() {
			return kill_apps;
		}
	}

	// KillApps Class

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------------ BalanceFetch Class ----------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public static class BalanceFetch {

		private String _balance;

		public BalanceFetch() {
			// this._string = "";
		}

		public String get_string() {
			return _balance;
		}

		public void set_string(String balance) {
			this._balance = balance;
		}

	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------------ VehicleMilage Class ----------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class VehicleMilage {

		private String _bMileageUpdated;
		private String _FileMilage;
		private String _FailureMsg;

		public VehicleMilage() {
			this._bMileageUpdated = "0";
			this._FileMilage = "";
			this._FailureMsg = "";

		}

		public String get_bMileageUpdated() {
			return _bMileageUpdated;
		}

		public void set_bMileageUpdated(String bMileageUpdated) {
			this._bMileageUpdated = bMileageUpdated;
		}

		public String get_FileMilage() {
			return _FileMilage;
		}

		public void set_FileMilage(String FileMilage) {
			this._FileMilage = FileMilage;
		}

		public String get_FailureMsg() {
			return _FailureMsg;
		}

		public void set_FailureMsg(String FailureMsg) {
			this._FailureMsg = FailureMsg;
		}

	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------------ CCMappings Class ----------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class CCMappings {

		private String CreditCardType;
		private String CreditCardCompany;

		public CCMappings(String cardType, String processingCompany) {
			CreditCardType = cardType;
			CreditCardCompany = processingCompany;
		}

		public String getCreditCardType() {
			return CreditCardType.trim();
		}

		public void setCreditCardType(String cardType) {
			CreditCardType = cardType;
		}

		public String getCardProcessingCompany() {
			return CreditCardCompany.trim();
		}

		public void setCardProcessingCompany(String cardCompany) {
			CreditCardCompany = cardCompany;
		}

		@Override
		public String toString() {
			return CreditCardType + "|" + CreditCardCompany;
		}
	}// CCMappings Class

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *---------------------------------------------------- CCProcessingCompanies Class --------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class CCProcessingCompany {

		private String _CompanyAbbreviation;
		private String _ServiceLink;
		private String _UserName;
		private String _AccountPassword;
		private String _MerchantId;
		private String _BankId;
		private String _Others;
		private String _iGatewayVariable2;
		private String _iGatewayVariable3;
		private String _vVersion;
		private String _vProduct;
		private String _vKey;
		private boolean _isTestCAWKey;
		private String _PaymentProcessingMethod;
		private String _MultiStepScenario;

		public CCProcessingCompany() {

			this._CompanyAbbreviation = "";
			this._ServiceLink = "";
			this._UserName = "0";
			this._AccountPassword = "";
			this._MerchantId = "";
			this._BankId = "";
			this._Others = "";
			this._iGatewayVariable2 = "0";
			this._iGatewayVariable3 = "0";
			this._vVersion = "1.0";
			this._vProduct = "";
			this._vKey = "";
			this._isTestCAWKey = false;
			this._PaymentProcessingMethod = "-1";
			this._MultiStepScenario = "1";
		}

		public String get_CompanyAbbreviation() {
			return _CompanyAbbreviation.trim();
		}

		public void set_CompanyAbbreviation(String CompanyAbbreviation) {
			this._CompanyAbbreviation = CompanyAbbreviation;
		}

		public String get_ServiceLink() {
			return _ServiceLink.trim();
		}

		public void set_ServiceLink(String ServiceLink) {
			this._ServiceLink = ServiceLink;
		}

		public String get_UserName() {
			return _UserName.trim();
		}

		public void set_UserName(String UserName) {
			this._UserName = UserName;
		}

		public String get_AccountPassword() {
			return _AccountPassword.trim();
		}

		public void set_AccountPassword(String AccountPassword) {
			this._AccountPassword = AccountPassword;
		}

		public String get_MerchantId() {
			return _MerchantId.trim();
		}

		public void set_MerchantId(String MerchantId) {
			this._MerchantId = MerchantId;
		}

		public String get_BankId() {
			return _BankId.trim();
		}

		public void set_BankId(String BankId) {
			this._BankId = BankId;
		}

		public String get_iGatewayVariable2() {
			return _iGatewayVariable2.trim();
		}

		public void set_iGatewayVariable2(String iGatewayVariable2) {
			this._iGatewayVariable2 = iGatewayVariable2;
		}

		public String get_vKey() {
			return _vKey.trim();
		}

		public void set_vKey(String vKey) {
			this._vKey = vKey;
		}

		public String get_vProduct() {
			return _vProduct.trim();
		}

		public void set_vProduct(String vProduct) {
			this._vProduct = vProduct;
		}

		public String get_vVersion() {
			return _vVersion.trim();
		}

		public void set_vVersion(String vVersion) {
			this._vVersion = vVersion;
		}

		public String get_iGatewayVariable3() {
			return _iGatewayVariable3.trim();
		}

		public void set_iGatewayVariable3(String iGatewayVariable3) {
			this._iGatewayVariable3 = iGatewayVariable3;
		}

		public String get_Others() {
			return _Others.trim();
		}

		public void set_Others(String Others) {
			this._Others = Others;
		}

		public boolean get_isTestCAWKey() {
			return _isTestCAWKey;
		}

		public void set_isTestCAWKey(boolean isTestCAWKey) {
			this._isTestCAWKey = isTestCAWKey;
		}

		public String get_PaymentProcessingMethod() {
			return _PaymentProcessingMethod.trim();
		}

		public void set_PaymentProcessingMethod(String PaymentProcessingMethod) {
			this._PaymentProcessingMethod = PaymentProcessingMethod;
		}

		public String get_MultiStepScenario() {
			return _MultiStepScenario.trim();
		}

		public void set_MultiStepScenario(String MultiStepScenario) {
			this._MultiStepScenario = MultiStepScenario;
		}

		@Override
		public String toString() {
			return _CompanyAbbreviation + "|" + _ServiceLink + "|" + _UserName + "|" + _AccountPassword + "|" + _MerchantId + "|" + _BankId + "|" + _Others;
		}
	}// CCProcessingCompany Class

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *---------------------------------------------------- CC_AuthorizeDotNet_MJM Class --------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class CC_AuthorizeDotNet_MJM {

		private String _TransactionId;
		private String _Amount1;
		private String _Message;
		private String _AuthCode;
		private String _JobId;
		private String _ResponseType;
		private String _ResultCode;
		private String _Balance;
		private String _MaxCardCharge;

		public CC_AuthorizeDotNet_MJM() {

			this._TransactionId = "";
			this._Amount1 = "";
			this._Message = "";
			this._AuthCode = "";
			this._JobId = "";
			this._ResponseType = "";
			this._ResultCode = "";
			this._Balance = "";
			this._MaxCardCharge = "";
		}

		public String get_TransactionId() {
			return _TransactionId.trim();
		}

		public void set_TransactionId(String TransactionId) {
			this._TransactionId = TransactionId;
		}

		public String get_Amount1() {
			return _Amount1.trim();
		}

		public void set_Amount1(String Amount1) {
			this._Amount1 = Amount1;
		}

		public String get_Message() {
			return _Message.trim();
		}

		public void set_Message(String Message) {
			this._Message = Message;
		}

		public String get_AuthCode() {
			return _AuthCode.trim();
		}

		public void set_AuthCode(String AuthCode) {
			this._AuthCode = AuthCode;
		}

		public String get_JobId() {
			return _JobId.trim();
		}

		public void set_JobId(String JobId) {
			this._JobId = JobId;
		}

		public String get_ResponseType() {
			return _ResponseType.trim();
		}

		public void set_ResponseType(String ResponseType) {
			this._ResponseType = ResponseType;
		}

		public String get_ResultCode() {
			return _ResultCode.trim();
		}

		public void set_ResultCode(String ResultCode) {
			this._ResultCode = ResultCode;
		}

		public String get_BalanceAmt() {
			return _Balance.trim();
		}

		public void set_BalanceAmt(String Balance) {
			this._Balance = Balance;
		}
		public String get_MaxCardCharge() {
			return _MaxCardCharge.trim();
		}

		public void set_MaxCardCharge(String MaxCardCharge) {
			this._MaxCardCharge = MaxCardCharge;
		}
	}// CCAuthorizeDotNet Class

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *---------------------------------------------------- Trip Class --------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class Trip {
		private String _ConfirmationsNumber;
		private String _TripState;
		private String _PickupLattitude;
		private String _PickupLongitude;
		private String _PickupDate;
		private String _PickupTime;
		private String _dDropLatitude;
		private String _dDropLongitude;
		private String _TripType;
		private String _Veh_LATITUDE;
		private String _Veh_LONGITUDE;
		private String _Driver_Name;
		private String _Driver_Phone;
		private String _Driver_Number;
		private String _ServiceID;
		private String _PUPerson;
		private String _vPickupPOIName;
		private String _vDropPOIName;
		private String _PULocation;
		private String _DOLocation;
		private String _PZONE;
		private String _DZONE;
		private String _schPUTime;
		private String _schDOTime;
		private String _OtherDetail;
		private String _Copay;
		private String _DISP;
		private String _TripRequestStatus;
		private String _bShowPhoneNumOnDevice;
		private String _PhoneNumber;
		private String _EstimatedDistance;
		private String _MFST;
		private String _TransactionID;
		private String _AuthCode;
		private String _JobID;
		private String _DeviceID;
		private String _RequestID;
		private String _EstimatedCost;
		private String _Funding_Source;
		private String _PaymentMethod;
		private String _CardType;
		private String _PaymentAmt;
		private String _GatewayReferenceNo;
		private String _vPickRemarks;
		private String _vDropRemarks;
		private String _vPickApartmentNo;
		private String _vDropApartmentNo;
		private String _vCallType;
		private String _bIsShuttle;
		private String _bTipApply;
		private String _bMaxTipInPercentage;
		private String _dMaxTip;
		private String _dTipAmount1;
		private String _dTipAmount2;
		private String _dTipAmount3;
		private String _dTipAmount4;
		private String _Actual_Distance;
		private String _Actual_Fare;
		private String _Actual_Extras;
		private String _Actual_Tip;
		private String _Actual_Total;
		private String _MJM_Balance;
		private String _Actual_Payment;
		private String _Credit_Card_Number;
		private String _TCRExpiry;

		public Trip() {
			this._ConfirmationsNumber = "";
			this._TripState = "";
			this._PickupLattitude = "";
			this._PickupLongitude = "";
			this._PickupDate = "";
			this._PickupTime = "";
			this._dDropLatitude = "";
			this._dDropLongitude = "";
			this._TripType = "";
			this._Veh_LATITUDE = "";
			this._Veh_LONGITUDE = "";
			this._Driver_Name = "";
			this._Driver_Phone = "";
			this._Driver_Number = "";
			this._ServiceID = "";
			this._PUPerson = "";
			this._vPickupPOIName = "";
			this._vDropPOIName = "";
			this._PULocation = "";
			this._DOLocation = "";
			this._PZONE = "";
			this._DZONE = "";
			this._schPUTime = "";
			this._schDOTime = "";
			this._OtherDetail = "";
			this._Copay = "";
			this._DISP = "";
			this._TripRequestStatus = "";
			this._bShowPhoneNumOnDevice = "";
			this._PhoneNumber = "";
			this._EstimatedDistance = "";
			this._MFST = "";
			this._TransactionID = "";
			this._AuthCode = "";
			this._JobID = "";
			this._DeviceID = "";
			this._RequestID = "";
			this._EstimatedCost = "";
			this._Funding_Source = "";
			this._PaymentMethod = "";
			this._CardType = "";
			this._PaymentAmt = "";
			this._GatewayReferenceNo = "";
			this._vPickRemarks = "";
			this._vDropRemarks = "";
			this._vPickApartmentNo = "";
			this._vDropApartmentNo = "";
			this._vCallType = "";
			this._bIsShuttle = "";
			this._bTipApply = "";
			this._bMaxTipInPercentage = "";
			this._dMaxTip = "";
			this._dTipAmount1 = "";
			this._dTipAmount2 = "";
			this._dTipAmount3 = "";
			this._dTipAmount4 = "";
			this._Actual_Distance = "";
			this._Actual_Fare = "";
			this._Actual_Extras = "";
			this._Actual_Tip = "";
			this._Actual_Total = "";
			this._MJM_Balance = "";
			this._Actual_Payment = "";
			this._Credit_Card_Number = "";
			this._TCRExpiry = "";
		}

		public String get_ConfirmationsNumber() {
			return _ConfirmationsNumber;
		}
		public String get_TripState() {
			return _TripState;
		}
		public String get_PickupLattitude() {
			return _PickupLattitude;
		}
		public String get_PickupLongitude() {
			return _PickupLongitude;
		}
		public String get_PickupDate() {
			return _PickupDate;
		}
		public String get_PickupTime() {
			return _PickupTime;
		}
		public String get_dDropLatitude() {
			return _dDropLatitude;
		}
		public String get_dDropLongitude() {
			return _dDropLongitude;
		}
		public String get_TripType() {
			return _TripType;
		}
		public String get_Veh_LATITUDE() {
			return _Veh_LATITUDE;
		}
		public String get_Veh_LONGITUDE() {
			return _Veh_LONGITUDE;
		}
		public String get_Driver_Name() {
			return _Driver_Name;
		}
		public String get_Driver_Phone() {
			return _Driver_Phone;
		}
		public String get_Driver_Number() {
			return _Driver_Number;
		}
		public String get_ServiceID() {
			return _ServiceID;
		}
		public String get_PUPerson() {
			return _PUPerson;
		}
		public String get_vPickupPOIName() {
			return _vPickupPOIName;
		}
		public String get_vDropPOIName() {
			return _vDropPOIName;
		}
		public String get_PULocation() {
			return _PULocation;
		}
		public String get_DOLocation() {
			return _DOLocation;
		}
		public String get_PZONE() {
			return _PZONE;
		}
		public String get_DZONE() {
			return _DZONE;
		}
		public String get_schPUTime() {
			return _schPUTime;
		}
		public String get_schDOTime() {
			return _schDOTime;
		}
		public String get_OtherDetail() {
			return _OtherDetail;
		}
		public String get_Copay() {
			return _Copay;
		}
		public String get_DISP() {
			return _DISP;
		}
		public String get_TripRequestStatus() {
			return _TripRequestStatus;
		}
		public String get_bShowPhoneNumOnDevice() {
			return _bShowPhoneNumOnDevice;
		}
		public String get_PhoneNumber() {
			return _PhoneNumber;
		}
		public String get_EstimatedDistance() {
			return _EstimatedDistance;
		}
		public String get_MFST() {
			return _MFST;
		}
		public String get_TransactionID() {
			return _TransactionID;
		}
		public String get_AuthCode() {
			return _AuthCode;
		}
		public String get_JobID() {
			return _JobID;
		}
		public String get_DeviceID() {
			return _DeviceID;
		}
		public String get_RequestID() {
			return _RequestID;
		}
		public String get_EstimatedCost() {
			return _EstimatedCost;
		}
		public String get_Funding_Source() {
			return _Funding_Source;
		}
		public String get_PaymentMethod() {
			return _PaymentMethod;
		}
		public String get_CardType() {
			return _CardType;
		}
		public String get_PaymentAmt() {
			return _PaymentAmt;
		}
		public String get_GatewayReferenceNo() {
			return _GatewayReferenceNo;
		}
		public String get_vPickRemarks() {
			return _vPickRemarks;
		}
		public String get_vDropRemarks() {
			return _vDropRemarks;
		}
		public String get_vPickApartmentNo() {
			return _vPickApartmentNo;
		}
		public String get_vDropApartmentNo() {
			return _vDropApartmentNo;
		}
		public String get_vCallType() {
			return _vCallType;
		}
		public String get_bIsShuttle() {
			return _bIsShuttle;
		}
		public String get_bTipApply() {
			return _bTipApply;
		}
		public String get_bMaxTipInPercentage() {
			return _bMaxTipInPercentage;
		}
		public String get_dMaxTip() {
			return _dMaxTip;
		}
		public String get_dTipAmount1() {
			return _dTipAmount1;
		}
		public String get_dTipAmount2() {
			return _dTipAmount2;
		}
		public String get_dTipAmount3() {
			return _dTipAmount3;
		}
		public String get_dTipAmount4() {
			return _dTipAmount4;
		}
		public String get_Actual_Distance() {
			return _Actual_Distance;
		}
		public String get_Actual_Fare() {
			return _Actual_Fare;
		}
		public String get_Actual_Extras() {
			return _Actual_Extras;
		}
		public String get_Actual_Tip() {
			return _Actual_Tip;
		}
		public String get_Actual_Total() {
			return _Actual_Total;
		}
		public String get_MJM_Balance() {
			return _MJM_Balance;
		}
		public String get_Actual_Payment() {
			return _Actual_Payment;
		}
		public String get_Credit_Card_Number() {
			return _Credit_Card_Number;
		}
		public String get_TCRExpiry() {
			return _TCRExpiry;
		}

		public void set_ConfirmationsNumber(String ConfirmationsNumber) {
			this._ConfirmationsNumber = ConfirmationsNumber;
		}
		public void set_TripState(String TripState) {
			this._TripState = TripState;
		}
		public void set_PickupLattitude(String PickupLattitude) {
			this._PickupLattitude = PickupLattitude;
		}
		public void set_PickupLongitude(String PickupLongitude) {
			this._PickupLongitude = PickupLongitude;
		}
		public void set_PickupDate(String PickupDate) {
			this._PickupDate = PickupDate;
		}
		public void set_PickupTime(String PickupDate) {
			this._PickupTime = PickupDate;
		}
		public void set_dDropLatitude(String dDropLatitude) {
			this._dDropLatitude = dDropLatitude;
		}
		public void set_dDropLongitude(String dDropLongitude) {
			this._dDropLongitude = dDropLongitude;
		}
		public void set_TripType(String TripType) {
			this._TripType = TripType;
		}
		public void set_Veh_LATITUDE(String Veh_LATITUDE) {
			this._Veh_LATITUDE = Veh_LATITUDE;
		}
		public void set_Veh_LONGITUDE(String Veh_LONGITUDE) {
			this._Veh_LONGITUDE = Veh_LONGITUDE;
		}
		public void set_Driver_Name(String Driver_Name) {
			this._Driver_Name = Driver_Name;
		}
		public void set_Driver_Phone(String Driver_Phone) {
			this._Driver_Phone = Driver_Phone;
		}
		public void set_Driver_Number(String Driver_Number) {
			this._Driver_Number = Driver_Number;
		}
		public void set_ServiceID(String Driver_Number) {
			this._ServiceID = Driver_Number;
		}
		public void set_PUPerson(String PUPerson) {
			this._PUPerson = PUPerson;
		}
		public void set_vPickupPOIName(String vPickupPOIName) {
			this._vPickupPOIName = vPickupPOIName;
		}
		public void set_vDropPOIName(String vDropPOIName) {
			this._vDropPOIName = vDropPOIName;
		}
		public void set_PULocation(String PULocation) {
			this._PULocation = PULocation;
		}
		public void set_DOLocation(String DOLocation) {
			this._DOLocation = DOLocation;
		}
		public void set_PZONE(String PZONE) {
			this._PZONE = PZONE;
		}
		public void set_DZONE(String DZONE) {
			this._DZONE = DZONE;
		}
		public void set_schPUTime(String schPUTime) {
			this._schPUTime = schPUTime;
		}
		public void set_schDOTime(String schDOTime) {
			this._schDOTime = schDOTime;
		}
		public void set_OtherDetail(String OtherDetail) {
			this._OtherDetail = OtherDetail;
		}
		public void set_Copay(String Copay) {
			this._Copay = Copay;
		}
		public void set_DISP(String DISP) {
			this._DISP = DISP;
		}
		public void set_TripRequestStatus(String TripRequestStatus) {
			this._TripRequestStatus = TripRequestStatus;
		}
		public void set_bShowPhoneNumOnDevice(String bShowPhoneNumOnDevice) {
			this._bShowPhoneNumOnDevice = bShowPhoneNumOnDevice;
		}
		public void set_PhoneNumber(String PhoneNumber) {
			this._PhoneNumber = PhoneNumber;
		}
		public void set_EstimatedDistance(String EstimatedDistance) {
			this._EstimatedDistance = EstimatedDistance;
		}
		public void set_MFST(String MFST) {
			this._MFST = MFST;
		}
		public void set_TransactionID(String TransactionID) {
			this._TransactionID = TransactionID;
		}
		public void set_AuthCode(String AuthCode) {
			this._AuthCode = AuthCode;
		}
		public void set_JobID(String JobID) {
			this._JobID = JobID;
		}
		public void set_DeviceID(String DeviceID) {
			this._DeviceID = DeviceID;
		}
		public void set_RequestID(String RequestID) {
			this._RequestID = RequestID;
		}
		public void set_EstimatedCost(String EstimatedCost) {
			this._EstimatedCost = EstimatedCost;
		}
		public void set_Funding_Source(String Funding_Source) {
			this._Funding_Source = Funding_Source;
		}
		public void set_PaymentMethod(String aymentMethod) {
			this._PaymentMethod = aymentMethod;
		}
		public void set_CardType(String CardType) {
			this._CardType = CardType;
		}
		public void set_PaymentAmt(String PaymentAmt) {
			this._PaymentAmt = PaymentAmt;
		}
		public void set_GatewayReferenceNo(String GatewayReferenceNo) {
			this._GatewayReferenceNo = GatewayReferenceNo;
		}
		public void set_vPickRemarks(String vPickRemarks) {
			this._vPickRemarks = vPickRemarks;
		}
		public void set_vDropRemarks(String vDropRemarks) {
			this._vDropRemarks = vDropRemarks;
		}
		public void set_vPickApartmentNo(String vPickApartmentNo) {
			this._vPickApartmentNo = vPickApartmentNo;
		}
		public void set_vDropApartmentNo(String vDropApartmentNo) {
			this._vDropApartmentNo = vDropApartmentNo;
		}
		public void set_vCallType(String vCallType) {
			this._vCallType = vCallType;
		}
		public void set_bIsShuttle(String bIsShuttle) {
			this._bIsShuttle = bIsShuttle;
		}
		public void set_bTipApply(String bTipApply) {
			this._bTipApply = bTipApply;
		}
		public void set_bMaxTipInPercentage(String bMaxTipInPercentage) {
			this._bMaxTipInPercentage = bMaxTipInPercentage;
		}
		public void set_dMaxTip(String dMaxTip) {
			this._dMaxTip = dMaxTip;
		}
		public void set_dTipAmount1(String dTipAmount1) {
			this._dTipAmount1 = dTipAmount1;
		}
		public void set_dTipAmount2(String dTipAmount2) {
			this._dTipAmount2 = dTipAmount2;
		}
		public void set_dTipAmount3(String dTipAmount3) {
			this._dTipAmount3 = dTipAmount3;
		}
		public void set_dTipAmount4(String dTipAmount4) {
			this._dTipAmount4 = dTipAmount4;
		}
		public void set_Actual_Distance(String Actual_Distance) {
			this._Actual_Distance = Actual_Distance;
		}
		public void set_Actual_Fare(String Actual_Fare) {
			this._Actual_Fare = Actual_Fare;
		}
		public void set_Actual_Extras(String Actual_Extras) {
			this._Actual_Extras = Actual_Extras;
		}
		public void set_Actual_Tip(String Actual_Tip) {
			this._Actual_Tip = Actual_Tip;
		}
		public void set_Actual_Total(String Actual_Total) {
			this._Actual_Total = Actual_Total;
		}
		public void set_MJM_Balance(String MJM_Balance) {
			this._MJM_Balance = MJM_Balance;
		}
		public void set_Actual_Payment(String Actual_Payment) {
			this._Actual_Payment = Actual_Payment;
		}
		public void set_Credit_Card_Number(String Credit_Card_Number) {
			this._Credit_Card_Number = Credit_Card_Number;
		}
		public void set_TCRExpiry(String TCRExpiry) {
			this._TCRExpiry = TCRExpiry;
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *---------------------------------------------------- GeneralSettings Class --------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class GeneralSettings {

		private boolean _AllowBTMeterOnSDStartUp;
		private boolean _AllowCentrodyneMeterOnSDStartUp;
		private boolean _AllowCMTVivotechOnSDStartUp;
		private boolean _AllowBlueBamboOnSDStartUp;
		private boolean _AllowAudioCommandsOnSDStartUp;
		private boolean _ShowEstMilesOnDevice;
		private boolean _SDEnableBreak;
		private String _GoogleMapAPIKey;
		private String _SDCallOutRequestPrompt;
		private String _SDCallOutRequestPrompt_ar;
		private boolean _SDEnableCalcEstOnDropped;
		private boolean _SDCalcEstViaMRMSService;
		private boolean _TipVoiceEnabled;
		private final ArrayList<String> _HideCostOnSDByFundingSource;
		private boolean _ReverseGeocodeFromMRMSService;
		private boolean _ShowManifestWallOnSD;
		private boolean _SendBidOffers;
		private boolean _RestrictSoftDropIfMeterConnected;
		private float _AllowedSpeedForMessaging;
		private long _TimerForCradleLogout;
		private double _AllowableCallOutDistance;
		private String _DeviceMessageScreenConfig;
		private int _SDMaxAllowedBreaksInOneDay;
		private int _WallTripDistanceByGoogle;
		private boolean _ShowReceiptPrintingDialog;
		private boolean _EnableDialiePackageOnDevice;
		private boolean _bShuttleAgentScreen;
		private boolean _SDShowFlaggerConfirmation;
		private String _SDTripListSize;
		private boolean _ShowSDDriverPhoto;

		private boolean _ShowSDAVLOnStatus;
		private boolean _ShowSDStandRankOnStatus;
		private boolean _ShowSDTaxiMeterOnStatus;
		private boolean _ShowSDBackSeatOnStatus;
		private boolean _ShowSDApartmentOnTripDetail;
		private boolean _ShowSDFundingSourceOnTripDetail;
		private boolean _ShowSDPaymentTypeOnTripDetail;
		private boolean _ShowSDCoPayOnTripDetail;
		private boolean _ShowSDOnlyFareOnPaymentScreen;
		private boolean _ShowStandsOnSD;
		private boolean _ShowNearZoneFeatureOnSD;
		private boolean _SDDropNavigationWithMap;
		private boolean _ShowClientPhoneNoOnReceipt;
		private String _SDDefaultLanguageSelection;
		private String _ResponseIDToRemoveTripFromWall;
		private boolean _bArabClient;
		private boolean _ShowTogglePickUpDropOffBTN;
		private boolean _Allow_Book_In_AZ;
		private boolean _ShowHandShakeButtonOnLogin;
		private String _HEXColor;
		private boolean _SDEnablePPT, _SDEnableEmergency;
		private float _SDFlaggerButtonSize;
		private boolean _SendDriverPicToVeriFone;
		private boolean _AllowDetailedLogInFileAndSQL;
		private boolean _SDShowLanguageChangeOption;
		private String _SDUnitOfDistance;
		private String _SDUnitOfCurrency, _SDBreakEmergencyPPTPosition;
		private boolean _PPV_UsePPVModule;
		private final ArrayList<String> _ShowEstdCostOnSDByFundingSource;
		private boolean _EnableAudioForMessageUtility;
		private boolean _SDShowProceedToPickupOnTripOffer;
		private int _SDReprintTimeOutSec;
		private int _SDVFCashVoucherDialogTimeOutSec;
		private boolean _SDEnableVoiceIfNewTripAddedOnWall;
		private boolean _SDShowPassengerNameOnWall;
		private boolean _SDBreakActionOnSingleTap;
		private String _SDBreakColor;
		private String _SDResumeColor;
		private boolean _SDShowPUDateTimeOnTripDetail;
		private boolean _SDShowVoucherButton;
		private boolean _CreditCardFeature;
		private boolean _SDEnableTripListSynchronization;
		private boolean _SDShowMileageOnStatusTab;
		private boolean _SDEnableSignatureFeature;
		private String _SDCentralizedAsteriskService;
		private boolean _SDEnableCentralizedAsteriskService;
		private String _ASCS_HelpLine_Number;
		private boolean _SDEmergencyConfirmation;
		private String _NotAllowActionIfAway;
		private String _MessageTypeIfActionNotAllowed;
		private boolean _ShowAddressOnWall;
		private String _CompanyName_Receipt;
		private String _CompanyURL;
		private boolean _SDEnableManualFlagger;
		private boolean _SDEnableTwoStepPaymentProcessing;
		private String _SDPaymentButtonCaptionFor2ndStep;
		private int _Allow_Promotion_In_MARS_SDApp_Both;
		private boolean _SDShowFontChangeOption;
		private boolean _SDEnableMeterLocking;
		private int _SDRingerCountForTripOffer;
		private boolean _SDShowServiceID;
		private boolean _SDShowPhoneandIMEI;
        private String _SDAsteriskExt;
        private String _SDAsteriskPwd;
        private String _SDAsteriskServer;
        private String _SDAsteriskHangUpTime; // in seconds
        private String _SDAsteriskDispatcherExt;
        private boolean _SDEnableAsteriskExtension;
        private String _MARS_HelpLine_Number;
        private String _TSPID;
        private String _SIPExtPattern;
        private String _SIPPwdPattern;
        private String _InLoadAPI_URL;
        private boolean _SDOnlyNearZoneMode;
        private int _WallRefreshTimer;
        private int _SDEnableOdometerInput;
        private boolean _SDEnableReceiptEmail;
        private boolean _SDEnableStatsForVoip;

		public GeneralSettings() {
			this._AllowBTMeterOnSDStartUp = false;
			this._AllowCentrodyneMeterOnSDStartUp = false;
			this._AllowCMTVivotechOnSDStartUp = false;
			this._AllowBlueBamboOnSDStartUp = false;
			this._AllowAudioCommandsOnSDStartUp = false;
			this._ShowEstMilesOnDevice = false;
			this._GoogleMapAPIKey = "";
			this._SDCallOutRequestPrompt = "";
			this._SDCallOutRequestPrompt_ar = "";
			this._SDEnableBreak = true;
			this._SDEnableCalcEstOnDropped = false;
			this._SDCalcEstViaMRMSService = false;
			this._TipVoiceEnabled = false;
			this._HideCostOnSDByFundingSource = new ArrayList<String>();
			this._ReverseGeocodeFromMRMSService = false;
			this._ShowManifestWallOnSD = false;
			this._SendBidOffers = true;
			this._AllowedSpeedForMessaging = 100;
			this._TimerForCradleLogout = 60000;
			this._AllowableCallOutDistance = 0.0;
			this._DeviceMessageScreenConfig = "1111";
			this._SDMaxAllowedBreaksInOneDay = 1;
			this._WallTripDistanceByGoogle = 1;
			this._ShowReceiptPrintingDialog = false;
			this._EnableDialiePackageOnDevice = false;
			this._SDShowFlaggerConfirmation = true;
			this._bShuttleAgentScreen = false;
			this._SDTripListSize = "5";
			this._ShowEstdCostOnSDByFundingSource = new ArrayList<String>();
			this._ShowSDDriverPhoto = false;

			this._ShowSDDriverPhoto = false;
			this._ShowSDAVLOnStatus = true;
			this._ShowSDStandRankOnStatus = true;
			this._ShowSDTaxiMeterOnStatus = true;
			this._ShowSDBackSeatOnStatus = true;
			this._ShowSDApartmentOnTripDetail = true;
			this._ShowSDFundingSourceOnTripDetail = true;
			this._ShowSDPaymentTypeOnTripDetail = true;
			this._ShowSDCoPayOnTripDetail = true;
			this._ShowSDOnlyFareOnPaymentScreen = false;
			this._ShowStandsOnSD = true;
			this._ShowNearZoneFeatureOnSD = false;
			this._SDDefaultLanguageSelection = "en";
			this._SDDropNavigationWithMap = true;
			this._ShowClientPhoneNoOnReceipt = true;
			this._ResponseIDToRemoveTripFromWall = "-11,-12,-13,-14,-21";
			this._bArabClient = false;
			this._ShowTogglePickUpDropOffBTN = false;
			this._HEXColor = "#DEDEDE";
			this._Allow_Book_In_AZ = true;
			this._ShowHandShakeButtonOnLogin = false;
			this._SDEnablePPT = true;
			this._SDEnableEmergency = true;
			this._SDFlaggerButtonSize = 35;
			// change by hamza
			this._SDShowLanguageChangeOption = false;
			this._SendDriverPicToVeriFone = false;
			this._AllowDetailedLogInFileAndSQL = false;
			this._SDUnitOfDistance = "Mile";
			this._SDUnitOfCurrency = "$";
			this._SDBreakEmergencyPPTPosition = "peb";// bep
			this._EnableAudioForMessageUtility = false;
			this._SDShowProceedToPickupOnTripOffer = true;
			this._SDReprintTimeOutSec = 15;
			this._SDVFCashVoucherDialogTimeOutSec = 15;
			this._SDEnableVoiceIfNewTripAddedOnWall = false;
			this._SDShowPassengerNameOnWall = true;
			this._SDBreakActionOnSingleTap = true;
			this._SDBreakColor = "#009900";
			this._SDResumeColor = "#FFFF00";
			this._SDShowPUDateTimeOnTripDetail = false;
			this._SDShowVoucherButton = true;
			this._CreditCardFeature = true;
			this._SDEnableTripListSynchronization = true;
			this._SDShowMileageOnStatusTab = false;
			this._SDEnableSignatureFeature = false;
			this._SDCentralizedAsteriskService = "http://192.168.10.26:8080/jax/Asteriskservice?wsdl";
			this._SDEnableCentralizedAsteriskService = false;
			this._ASCS_HelpLine_Number = "";
			this._SDEmergencyConfirmation = false;
			this._NotAllowActionIfAway = "001110";
			this._MessageTypeIfActionNotAllowed = "110010";
			this._ShowAddressOnWall = false;
			this._CompanyName_Receipt = "";
			this._CompanyURL = "www.itcurves.net";
			this._SDEnableManualFlagger = false;
			this._SDEnableTwoStepPaymentProcessing = false;
			this._SDPaymentButtonCaptionFor2ndStep = "Tip Add/Update & Complete";
			this._Allow_Promotion_In_MARS_SDApp_Both = 1;
			this._SDShowFontChangeOption = false;
			this._SDEnableMeterLocking = false;
			this._SDRingerCountForTripOffer = 10;
			this._SDShowServiceID = false;
			this._SDShowPhoneandIMEI = false;
            this._SDAsteriskExt = "4275";
            this._SDAsteriskPwd = "1211";
            this._SDAsteriskServer = "192.168.4.100";
            this._SDAsteriskHangUpTime = "120";
            this._SDAsteriskDispatcherExt = "4277";
            this._SDEnableAsteriskExtension = false;
            this._MARS_HelpLine_Number = "3012082222";
            this._TSPID = "10";
            this._SIPExtPattern = "45";
            this._SIPPwdPattern = "9876n4321" ;
            this._InLoadAPI_URL = "http://192.168.4.80:84/api";
            this._SDOnlyNearZoneMode = false;
            this._WallRefreshTimer = 40;
            this._SDEnableOdometerInput = 0;
            this._SDEnableReceiptEmail = false;
            this._SDEnableStatsForVoip = true;
		}
		public boolean get_AllowBTMeterOnSDStartUp() {
			return _AllowBTMeterOnSDStartUp;
		}

		public void set_AllowBTMeterOnSDStartUp(Boolean AllowBTMeterOnSDStartUp) {
			this._AllowBTMeterOnSDStartUp = AllowBTMeterOnSDStartUp;
		}

		public boolean get_AllowCentrodyneMeterOnSDStartUp() {
			return _AllowCentrodyneMeterOnSDStartUp;
		}

		public void set_AllowCentrodyneMeterOnSDStartUp(Boolean AllowCentrodyneMeterOnSDStartUp) {
			this._AllowCentrodyneMeterOnSDStartUp = AllowCentrodyneMeterOnSDStartUp;
		}

		public boolean get_AllowCMTVivotechOnSDStartUp() {
			return _AllowCMTVivotechOnSDStartUp;
		}

		public void set_AllowCMTVivotechOnSDStartUp(Boolean AllowCMTVivotechOnSDStartUp) {
			this._AllowCMTVivotechOnSDStartUp = AllowCMTVivotechOnSDStartUp;
		}

		public boolean get_AllowBlueBamboOnSDStartUp() {
			return _AllowBlueBamboOnSDStartUp;
		}

		public void set_AllowBlueBamboOnSDStartUp(Boolean AllowBlueBamboOnSDStartUp) {
			this._AllowBlueBamboOnSDStartUp = AllowBlueBamboOnSDStartUp;
		}

		public boolean get_AllowAudioCommandsOnSDStartUp() {
			return _AllowAudioCommandsOnSDStartUp;
		}

		public void set_AllowAudioCommandsOnSDStartUp(Boolean AllowAudioCommandsOnSDStartUp) {
			this._AllowAudioCommandsOnSDStartUp = AllowAudioCommandsOnSDStartUp;
		}

		public boolean get_ShowEstMilesOnDevice() {
			return _ShowEstMilesOnDevice;
		}

		public void set_ShowEstMilesOnDevice(Boolean ShowEstMilesOnDevice) {
			this._ShowEstMilesOnDevice = ShowEstMilesOnDevice;
		}
		public String get_GoogleMapAPIKey() {
			return _GoogleMapAPIKey;
		}

		public void set_GoogleMapAPIKey(String GoogleMapAPIKey) {
			this._GoogleMapAPIKey = GoogleMapAPIKey;
		}

		public String get_SDCallOutRequestPrompt() {
			return _SDCallOutRequestPrompt;
		}

		public void set_SDCallOutRequestPrompt(String SDCallOutRequestPrompt) {
			this._SDCallOutRequestPrompt = SDCallOutRequestPrompt;
		}

		public String get_SDCallOutRequestPrompt_ar() {
			return _SDCallOutRequestPrompt_ar;
		}

		public void set_SDCallOutRequestPrompt_ar(String SDCallOutRequestPrompt_ar) {
			this._SDCallOutRequestPrompt_ar = SDCallOutRequestPrompt_ar;
		}

		public boolean get_SDEnableBreak() {
			return _SDEnableBreak;
		}

		public void set_SDEnableBreak(Boolean SDEnableBreak) {
			this._SDEnableBreak = SDEnableBreak;
		}

		public boolean get_SDEnableCalcEstOnDropped() {
			return _SDEnableCalcEstOnDropped;
		}

		public void set_SDEnableCalcEstOnDropped(Boolean SDEnableCalcEstOnDropped) {
			this._SDEnableCalcEstOnDropped = SDEnableCalcEstOnDropped;
		}

		public boolean get_SDCalcEstViaMRMSService() {
			return _SDCalcEstViaMRMSService;
		}

		public void set_SDCalcEstViaMRMSService(Boolean SDCalcEstViaMRMSService) {
			this._SDCalcEstViaMRMSService = SDCalcEstViaMRMSService;
		}

		public boolean get_TipVoiceEnabled() {
			return _TipVoiceEnabled;
		}

		public void set_TipVoiceEnabled(Boolean TipVoiceEnabled) {
			this._TipVoiceEnabled = TipVoiceEnabled;
		}
		public ArrayList<String> get_HideCostOnSDByFundingSource() {
			return _HideCostOnSDByFundingSource;
		}

		public void set_HideCostOnSDByFundingSource(String HideCostOnSDByFundingSource) {
			this._HideCostOnSDByFundingSource.add(HideCostOnSDByFundingSource);
		}

		public boolean get_ReverseGeocodeFromMRMSService() {
			return _ReverseGeocodeFromMRMSService;
		}

		public void set_ReverseGeocodeFromMRMSService(Boolean ReverseGeocodeFromMRMSService) {
			this._ReverseGeocodeFromMRMSService = ReverseGeocodeFromMRMSService;
		}

		public boolean get_ShowManifestWallOnSD() {
			return _ShowManifestWallOnSD;
		}

		public void set_ShowManifestWallOnSD(Boolean ShowManifestWallOnSD) {
			this._ShowManifestWallOnSD = ShowManifestWallOnSD;
		}

		public boolean get_SendBidOffers() {
			return _SendBidOffers;
		}

		public void set_SendBidOffers(Boolean SendBidOffers) {
			this._SendBidOffers = SendBidOffers;
		}

		public boolean get_RestrictSoftDropIfMeterConnected() {
			return _RestrictSoftDropIfMeterConnected;
		}

		public void set_RestrictSoftDropIfMeterConnected(Boolean RestrictSoftDropIfMeterConnected) {
			this._RestrictSoftDropIfMeterConnected = RestrictSoftDropIfMeterConnected;
		}

		public float get_AllowedSpeedForMessaging() {
			return _AllowedSpeedForMessaging;
		}

		public void set_AllowedSpeedForMessaging(float AllowedSpeedForMessaging) {
			this._AllowedSpeedForMessaging = AllowedSpeedForMessaging;
		}
		public long get_TimerForCradleLogout() {
			return _TimerForCradleLogout;
		}

		public void set_TimerForCradleLogout(long TimerForCradleLogout) {
			this._TimerForCradleLogout = TimerForCradleLogout;
		}

		public double get_AllowableCallOutDistance() {
			return _AllowableCallOutDistance;
		}

		public void set_AllowableCallOutDistance(double AllowableCallOutDistance) {
			this._AllowableCallOutDistance = AllowableCallOutDistance;
		}

		public String get_DeviceMessageScreenConfig() {
			return _DeviceMessageScreenConfig;
		}

		public void set_DeviceMessageScreenConfig(String DeviceMessageScreenConfig) {
			this._DeviceMessageScreenConfig = DeviceMessageScreenConfig;
		}

		public int get_SDMaxAllowedBreaksInOneDay() {
			return _SDMaxAllowedBreaksInOneDay;
		}

		public void set_SDMaxAllowedBreaksInOneDay(int SDMaxAllowedBreaksInOneDay) {
			this._SDMaxAllowedBreaksInOneDay = SDMaxAllowedBreaksInOneDay;
		}

		public int get_WallTripDistanceByGoogle() {
			return _WallTripDistanceByGoogle;
		}

		public void set_WallTripDistanceByGoogle(int WallTripDistanceByGoogle) {
			this._WallTripDistanceByGoogle = WallTripDistanceByGoogle;
		}

		public Boolean get_ShowReceiptPrintingDialog() {
			return _ShowReceiptPrintingDialog;
		}

		public void set_ShowReceiptPrintingDialog(Boolean ShowReceiptPrintingDialog) {
			this._ShowReceiptPrintingDialog = ShowReceiptPrintingDialog;
		}

		public Boolean get_EnableDialiePackageOnDevice() {
			return _EnableDialiePackageOnDevice;
		}

		public void set_EnableDialiePackageOnDevice(Boolean EnableDialiePackageOnDevice) {
			this._EnableDialiePackageOnDevice = EnableDialiePackageOnDevice;
		}

		public Boolean get_bShuttleAgentScreen() {
			return _bShuttleAgentScreen;
		}

		public void set_bShuttleAgentScreen(Boolean bShuttleAgentScreen) {
			this._bShuttleAgentScreen = bShuttleAgentScreen;
		}

		public Boolean get_SDShowFlaggerConfirmation() {
			return _SDShowFlaggerConfirmation;
		}

		public void set_SDShowFlaggerConfirmation(Boolean SDShowFlaggerConfirmation) {
			this._SDShowFlaggerConfirmation = SDShowFlaggerConfirmation;
		}

		public String get_SDTripListSize() {
			return _SDTripListSize;
		}

		public void set_SDTripListSize(String SDTripListSize) {
			this._SDTripListSize = SDTripListSize;
		}

		public ArrayList<String> get_ShowEstdCostOnSDByFundingSource() {
			return _ShowEstdCostOnSDByFundingSource;
		}

		public void set_ShowEstdCostOnSDByFundingSource(String ShowEstdCostOnSDByFundingSource) {
			this._ShowEstdCostOnSDByFundingSource.add(ShowEstdCostOnSDByFundingSource);
		}

		public Boolean get_ShowSDDriverPhoto() {
			return _ShowSDDriverPhoto;
		}

		public void set_ShowSDDriverPhoto(Boolean ShowSDDriverPhoto) {
			this._ShowSDDriverPhoto = ShowSDDriverPhoto;
		}

		public Boolean get_ShowSDAVLOnStatus() {
			return _ShowSDAVLOnStatus;
		}

		public void set_ShowSDAVLOnStatus(Boolean ShowSDAVLOnStatus) {
			this._ShowSDAVLOnStatus = ShowSDAVLOnStatus;
		}

		public Boolean get_ShowSDStandRankOnStatus() {
			return _ShowSDStandRankOnStatus;
		}

		public void set_ShowSDStandRankOnStatus(Boolean ShowSDStandRankOnStatus) {
			this._ShowSDStandRankOnStatus = ShowSDStandRankOnStatus;
		}

		public Boolean get_ShowSDTaxiMeterOnStatus() {
			return _ShowSDTaxiMeterOnStatus;
		}

		public void set_ShowSDTaxiMeterOnStatus(Boolean ShowSDTaxiMeterOnStatus) {
			this._ShowSDTaxiMeterOnStatus = ShowSDTaxiMeterOnStatus;
		}

		public Boolean get_ShowSDBackSeatOnStatus() {
			return _ShowSDBackSeatOnStatus;
		}

		public void set_ShowSDBackSeatOnStatus(Boolean ShowSDBackSeatOnStatus) {
			this._ShowSDBackSeatOnStatus = ShowSDBackSeatOnStatus;
		}

		public Boolean get_ShowSDApartmentOnTripDetail() {
			return _ShowSDApartmentOnTripDetail;
		}

		public void set_ShowSDApartmentOnTripDetail(Boolean ShowSDApartmentOnTripDetail) {
			this._ShowSDApartmentOnTripDetail = ShowSDApartmentOnTripDetail;
		}

		public Boolean get_ShowSDFundingSourceOnTripDetail() {
			return _ShowSDFundingSourceOnTripDetail;
		}

		public void set_ShowSDFundingSourceOnTripDetail(Boolean ShowSDFundingSourceOnTripDetail) {
			this._ShowSDFundingSourceOnTripDetail = ShowSDFundingSourceOnTripDetail;
		}

		public Boolean get_ShowSDPaymentTypeOnTripDetail() {
			return _ShowSDPaymentTypeOnTripDetail;
		}

		public void set_ShowSDPaymentTypeOnTripDetail(Boolean ShowSDPaymentTypeOnTripDetail) {
			this._ShowSDPaymentTypeOnTripDetail = ShowSDPaymentTypeOnTripDetail;
		}

		public Boolean get_ShowSDCoPayOnTripDetail() {
			return _ShowSDCoPayOnTripDetail;
		}

		public void set_ShowSDCoPayOnTripDetail(Boolean ShowSDCoPayOnTripDetail) {
			this._ShowSDCoPayOnTripDetail = ShowSDCoPayOnTripDetail;
		}

		public Boolean get_ShowSDOnlyFareOnPaymentScreen() {
			return _ShowSDOnlyFareOnPaymentScreen;
		}

		public void set_ShowSDOnlyFareOnPaymentScreen(Boolean ShowSDOnlyFareOnPaymentScreen) {
			this._ShowSDOnlyFareOnPaymentScreen = ShowSDOnlyFareOnPaymentScreen;
		}

		public Boolean get_ShowStandsOnSD() {
			return _ShowStandsOnSD;
		}

		public void set_ShowStandsOnSD(Boolean ShowStandsOnSD) {
			this._ShowStandsOnSD = ShowStandsOnSD;
		}

		public Boolean get_ShowNearZoneFeatureOnSD() {
			return _ShowNearZoneFeatureOnSD;
		}

		public void set_ShowNearZoneFeatureOnSD(Boolean ShowNearZoneFeatureOnSD) {
			this._ShowNearZoneFeatureOnSD = ShowNearZoneFeatureOnSD;
		}

		public String get_SDDefaultLanguageSelection() {
			return _SDDefaultLanguageSelection;
		}

		public void set_SDDefaultLanguageSelection(String SDDefaultLanguageSelection) {
			if (SDDefaultLanguageSelection.equalsIgnoreCase("en") || SDDefaultLanguageSelection.equalsIgnoreCase("ar"))
				this._SDDefaultLanguageSelection = SDDefaultLanguageSelection;
			else
				this._SDDefaultLanguageSelection = "en";
		}

		public Boolean get_SDDropNavigationWithMap() {
			return _SDDropNavigationWithMap;
		}

		public void set_SDDropNavigationWithMap(Boolean SDDropNavigationWithMap) {
			this._SDDropNavigationWithMap = SDDropNavigationWithMap;
		}

		public Boolean get_ShowClientPhoneNoOnReceipt() {
			return _ShowClientPhoneNoOnReceipt;
		}

		public void set_ShowClientPhoneNoOnReceipt(Boolean ShowClientPhoneNoOnReceipt) {
			this._ShowClientPhoneNoOnReceipt = ShowClientPhoneNoOnReceipt;
		}

		public String get_ResponseIDToRemoveTripFromWall() {
			return _ResponseIDToRemoveTripFromWall;
		}

		public void set_ResponseIDToRemoveTripFromWall(String ResponseIDToRemoveTripFromWall) {
			this._ResponseIDToRemoveTripFromWall = ResponseIDToRemoveTripFromWall;
		}

		public Boolean get_bArabClient() {
			return _bArabClient;
		}

		public void set_bArabClient(Boolean bArabClient) {
			this._bArabClient = bArabClient;
		}

		public Boolean get_ShowTogglePickUpDropOffBTN() {
			return _ShowTogglePickUpDropOffBTN;
		}

		public void set_ShowTogglePickUpDropOffBTN(Boolean ShowTogglePickUpDropOffBTN) {
			this._ShowTogglePickUpDropOffBTN = ShowTogglePickUpDropOffBTN;
		}

		public String get_HEXColor() {
			return _HEXColor;
		}

		public void set_HEXColor(String HEXColor) {

			if (HEXColor.contains("#")) {
				if (HEXColor.trim().length() > 2)
					this._HEXColor = HEXColor;
			} else
				this._HEXColor = "#DEDEDE";
		}

		public Boolean get_Allow_Book_In_AZ() {
			return _Allow_Book_In_AZ;
		}

		public void set_Allow_Book_In_AZ(Boolean Allow_Book_In_AZ) {
			this._Allow_Book_In_AZ = Allow_Book_In_AZ;
		}

		public Boolean get_ShowHandShakeButtonOnLogin() {
			return _ShowHandShakeButtonOnLogin;
		}

		public void set_ShowHandShakeButtonOnLogin(Boolean ShowHandShakeButtonOnLogin) {
			this._ShowHandShakeButtonOnLogin = ShowHandShakeButtonOnLogin;
		}

		public Boolean get_SDEnablePPT() {
			return _SDEnablePPT;
		}

		public void set_SDEnablePPT(Boolean SDEnablePPT) {
			this._SDEnablePPT = SDEnablePPT;
		}

		public Boolean get_SDEnableEmergency() {
			return _SDEnableEmergency;
		}

		public void set_SDEnableEmergency(Boolean SDEnableEmergency) {
			this._SDEnableEmergency = SDEnableEmergency;
		}

		public float get_SDFlaggerButtonSize() {
			return _SDFlaggerButtonSize;
		}

		public void set_SDFlaggerButtonSize(float SDFlaggerButtonSize) {
			this._SDFlaggerButtonSize = SDFlaggerButtonSize;
		}

		// change by hamza
		public Boolean get_SDShowLanguageChangeOption() {
			return _SDShowLanguageChangeOption;
		}

		public void set_SDShowLanguageChangeOption(Boolean SDShowLanguageChangeOption) {
			this._SDShowLanguageChangeOption = SDShowLanguageChangeOption;
		}

		public String get_SDUnitOfDistance() {
			return _SDUnitOfDistance;
		}

		public void set_SDUnitOfDistance(String SDUnitOfDistance) {

			if (SDUnitOfDistance != "") {
				this._SDUnitOfDistance = SDUnitOfDistance;
			} else {
				this._SDUnitOfDistance = "Mile";
			}
		}

		public String get_SDUnitOfCurrency() {
			return _SDUnitOfCurrency;
		}

		public void set_SDUnitOfCurrency(String SDUnitOfCurrency) {
			if (SDUnitOfCurrency != "") {
				this._SDUnitOfCurrency = SDUnitOfCurrency;
			} else {
				this._SDUnitOfCurrency = "$";
			}
		}

		public Boolean get_PPV_UsePPVModule() {
			return _PPV_UsePPVModule;
		}

		public void set_PPV_UsePPVModule(Boolean PPV_UsePPVModule) {
			this._PPV_UsePPVModule = PPV_UsePPVModule;
		}

		public Boolean get_SendDriverPicToVeriFone() {
			return _SendDriverPicToVeriFone;
		}

		public void set_SendDriverPicToVeriFone(Boolean SendDriverPicToVeriFone) {
			this._SendDriverPicToVeriFone = SendDriverPicToVeriFone;
		}

		public Boolean get_AllowDetailedLogInFileAndSQL() {
			return _AllowDetailedLogInFileAndSQL;
		}

		public void set_AllowDetailedLogInFileAndSQL(Boolean AllowDetailedLogInFileAndSQL) {
			this._AllowDetailedLogInFileAndSQL = AllowDetailedLogInFileAndSQL;
		}

		public String get_SDBreakEmergencyPPTPosition() {
			return _SDBreakEmergencyPPTPosition;
		}

		public void set_SDBreakEmergencyPPTPosition(String SDBreakEmergencyPPTPosition) {
			this._SDBreakEmergencyPPTPosition = SDBreakEmergencyPPTPosition;
		}

		public Boolean get_EnableAudioForMessageUtility() {
			return _EnableAudioForMessageUtility;
		}

		public void set_EnableAudioForMessageUtility(Boolean enableAudioForMessageUtility) {
			this._EnableAudioForMessageUtility = enableAudioForMessageUtility;
		}

		public Boolean get_SDShowProceedToPickupOnTripOffer() {
			return _SDShowProceedToPickupOnTripOffer;
		}

		public void set_SDShowProceedToPickupOnTripOffer(Boolean SDShowProceedToPickupOnTripOffer) {
			this._SDShowProceedToPickupOnTripOffer = SDShowProceedToPickupOnTripOffer;
		}

		public int get_SDReprintTimeOutSec() {
			return _SDReprintTimeOutSec;
		}

		public void set_SDReprintTimeOutSec(int SDReprintTimeOutSec) {
			if (SDReprintTimeOutSec > 0)
				this._SDReprintTimeOutSec = SDReprintTimeOutSec;
			else
				this._SDReprintTimeOutSec = 15;
		}

		public int get_SDVFCashVoucherDialogTimeOutSec() {
			return _SDVFCashVoucherDialogTimeOutSec;
		}

		public void set_SDVFCashVoucherDialogTimeOutSec(int SDVFCashVoucherDialogTimeOutSec) {
			if (SDVFCashVoucherDialogTimeOutSec > 0)
				this._SDVFCashVoucherDialogTimeOutSec = SDVFCashVoucherDialogTimeOutSec;
			else
				this._SDVFCashVoucherDialogTimeOutSec = 15;
		}

		public boolean get_SDEnableVoiceIfNewTripAddedOnWall() {
			return _SDEnableVoiceIfNewTripAddedOnWall;
		}

		public void set_SDEnableVoiceIfNewTripAddedOnWall(boolean SDEnableVoiceIfNewTripAddedOnWall) {
			this._SDEnableVoiceIfNewTripAddedOnWall = SDEnableVoiceIfNewTripAddedOnWall;
		}

		public boolean get_SDShowPassengerNameOnWall() {
			return _SDShowPassengerNameOnWall;
		}

		public void set_SDShowPassengerNameOnWall(boolean SDShowPassengerNameOnWall) {
			this._SDShowPassengerNameOnWall = SDShowPassengerNameOnWall;
		}

		public boolean get_SDBreakActionOnSingleTap() {
			return _SDBreakActionOnSingleTap;
		}

		public void set_SDBreakActionOnSingleTap(boolean SDBreakActionOnSingleTap) {
			this._SDBreakActionOnSingleTap = SDBreakActionOnSingleTap;
		}

		public String get_SDBreakColor() {
			return _SDBreakColor;
		}

		public void set_SDBreakColor(String SDBreakColor) {

			if (SDBreakColor.contains("#")) {
				if (SDBreakColor.trim().length() > 2)
					this._SDBreakColor = SDBreakColor;
			} else
				this._SDBreakColor = "#009900";
		}

		public String get_SDResumeColor() {
			return _SDResumeColor;
		}

		public void set_SDResumeColor(String SDResumeColor) {

			if (SDResumeColor.contains("#")) {
				if (SDResumeColor.trim().length() > 2)
					this._SDResumeColor = SDResumeColor;
			} else
				this._SDResumeColor = "#FFFF00";
		}

		public boolean get_SDShowPUDateTimeOnTripDetail() {
			return _SDShowPUDateTimeOnTripDetail;
		}

		public void set_SDShowPUDateTimeOnTripDetail(boolean SDShowPUDateTimeOnTripDetail) {
			this._SDShowPUDateTimeOnTripDetail = SDShowPUDateTimeOnTripDetail;
		}

		public boolean get_SDShowVoucherButton() {
			return _SDShowVoucherButton;
		}

		public void set_SDShowVoucherButton(boolean SDShowVoucherButton) {
			this._SDShowVoucherButton = SDShowVoucherButton;
		}

		public boolean get_CreditCardFeature() {
			return _CreditCardFeature;
		}

		public void set_CreditCardFeature(boolean CreditCardFeature) {
			this._CreditCardFeature = CreditCardFeature;
		}

		public boolean get_SDEnableTripListSynchronization() {
			return _SDEnableTripListSynchronization;
		}

		public void set_SDEnableTripListSynchronization(boolean SDEnableTripListSynchronization) {
			this._SDEnableTripListSynchronization = SDEnableTripListSynchronization;
		}

		public boolean get_SDShowMileageOnStatusTab() {
			return _SDShowMileageOnStatusTab;
		}

		public void set_SDShowMileageOnStatusTab(boolean SDShowMileageOnStatusTab) {
			this._SDShowMileageOnStatusTab = SDShowMileageOnStatusTab;
		}

		public boolean get_SDEnableSignatureFeature() {
			return _SDEnableSignatureFeature;
		}

		public void set_SDEnableSignatureFeature(boolean SDEnableSignatureFeature) {
			this._SDEnableSignatureFeature = SDEnableSignatureFeature;
		}

		public String get_SDCentralizedAsteriskService() {
			return _SDCentralizedAsteriskService;
		}

		public void set_SDCentralizedAsteriskService(String SDCentralizedAsteriskService) {
			this._SDCentralizedAsteriskService = SDCentralizedAsteriskService;
		}

		public boolean get_SDEnableCentralizedAsteriskService() {
			return _SDEnableCentralizedAsteriskService;
		}

		public void set_SDEnableCentralizedAsteriskService(boolean SDEnableCentralizedAsteriskService) {
			this._SDEnableCentralizedAsteriskService = SDEnableCentralizedAsteriskService;
		}

		public String get_ASCS_HelpLine_Number() {
			return _ASCS_HelpLine_Number;
		}

		public void set_ASCS_HelpLine_Number(String ASCS_HelpLine_Number) {
			this._ASCS_HelpLine_Number = ASCS_HelpLine_Number;
		}

		public boolean get_SDEmergencyConfirmation() {
			return _SDEmergencyConfirmation;
		}

		public void set_SDEmergencyConfirmation(boolean SDEmergencyConfirmation) {
			this._SDEmergencyConfirmation = SDEmergencyConfirmation;
		}

		public String get_NotAllowActionIfAway() {
			return _NotAllowActionIfAway;
		}

		public void set_NotAllowActionIfAway(String NotAllowActionIfAway) {
			this._NotAllowActionIfAway = NotAllowActionIfAway;
		}

		public String get_MessageTypeIfActionNotAllowed() {
			return _MessageTypeIfActionNotAllowed;
		}

		public void set_MessageTypeIfActionNotAllowed(String MessageTypeIfActionNotAllowed) {
			this._MessageTypeIfActionNotAllowed = MessageTypeIfActionNotAllowed;
		}

		public Boolean get_ShowAddressOnWall() {
			return _ShowAddressOnWall;
		}

		public void set_ShowAddressOnWall(boolean ShowAddressOnWall) {
			this._ShowAddressOnWall = ShowAddressOnWall;
		}

		public String get_CompanyName_Receipt() {
			return _CompanyName_Receipt;
		}

		public void set_CompanyName_Receipt(String CompanyName_Receipt) {
			this._CompanyName_Receipt = CompanyName_Receipt;
		}

		public String get_CompanyURL() {
			return _CompanyURL;
		}

		public void set_CompanyURL(String CompanyURL) {
			this._CompanyURL = CompanyURL;
		}
		public boolean get_SDEnableManualFlagger() {
			return _SDEnableManualFlagger;
		}

		public void set_SDEnableManualFlagger(boolean SDEnableManualFlagger) {
			this._SDEnableManualFlagger = SDEnableManualFlagger;
		}

		public void set_SDEnableTwoStepPaymentProcessing(boolean SDEnableTwoStepPaymentProcessing) {
			this._SDEnableTwoStepPaymentProcessing = SDEnableTwoStepPaymentProcessing;
		}

		public boolean get_SDEnableTwoStepPaymentProcessing() {
			return _SDEnableTwoStepPaymentProcessing;
		}

		public String get_SDPaymentButtonCaptionFor2ndStep() {
			return _SDPaymentButtonCaptionFor2ndStep;
		}

		public void set_SDPaymentButtonCaptionFor2ndStep(String _SDPaymentButtonCaptionFor2ndStep) {
			this._SDPaymentButtonCaptionFor2ndStep = _SDPaymentButtonCaptionFor2ndStep;
		}

		public int get_Allow_Promotion_In_MARS_SDApp_Both() {
			return _Allow_Promotion_In_MARS_SDApp_Both;
		}

		public void set_Allow_Promotion_In_MARS_SDApp_Both(int Allow_Promotion_In_MARS_SDApp_Both) {
			this._Allow_Promotion_In_MARS_SDApp_Both = Allow_Promotion_In_MARS_SDApp_Both;
		}

		public boolean get_SDShowFontChangeOption() {
			return _SDShowFontChangeOption;
		}

		public void set_SDShowFontChangeOption(boolean SDShowFontChangeOption) {
			this._SDShowFontChangeOption = SDShowFontChangeOption;
		}

		public boolean get_SDEnableMeterLocking() {
			return _SDEnableMeterLocking;
		}

		public void set_SDEnableMeterLocking(boolean SDEnableMeterLocking) {
			this._SDEnableMeterLocking = SDEnableMeterLocking;
		}

		public int get__SDRingerCountForTripOffer() {
			return this._SDRingerCountForTripOffer;
		}

		public void set_SDRingerCountForTripOffer(int SDRingerCountForTripOffer) {
			this._SDRingerCountForTripOffer = SDRingerCountForTripOffer;
		}

		public boolean get_SDShowServiceID() {
			return _SDShowServiceID;
		}

		public void set_SDShowServiceID(Boolean SDShowServiceID) {
			this._SDShowServiceID = SDShowServiceID;
		}

		public boolean get_SDShowPhoneandIMEI() {
			return _SDShowPhoneandIMEI;
		}

		public void set_SDShowPhoneandIMEI(Boolean SDShowPhoneandIMEI) {
			this._SDShowPhoneandIMEI = SDShowPhoneandIMEI;
		}
        public String get_SDAsteriskExt() {
            return _SDAsteriskExt;
        }
        public void set_SDAsteriskExt(String _SDAsteriskExt) {
            this._SDAsteriskExt = _SDAsteriskExt;
        }
        public String get_SDAsteriskPwd() {
            return _SDAsteriskPwd;
        }
        public void set_SDAsteriskPwt(String _SDAsteriskPwt) {
            this._SDAsteriskPwd = _SDAsteriskPwt;
        }
        public String get_SDAsteriskServer() {
            return _SDAsteriskServer;
        }
        public void set_SDAsteriskServer(String _SDAsteriskServer) {
            this._SDAsteriskServer = _SDAsteriskServer;
        }
        public String get_SDAsteriskHangUpTime() {
            return _SDAsteriskHangUpTime;
        }
        public void set_SDAsteriskHangUpTime(String _SDAsteriskHangUpTime) {
            this._SDAsteriskHangUpTime = _SDAsteriskHangUpTime;
        }
        public String get_SDAsteriskDispatcherExt() {
            return _SDAsteriskDispatcherExt;
        }
        public void set_SDAsteriskDispatcherExt(String _SDAsteriskDispatcherExt) {
            this._SDAsteriskDispatcherExt = _SDAsteriskDispatcherExt;
        }

        public boolean get_SDEnableAsteriskExtension() {
            return _SDEnableAsteriskExtension;
        }

        public void set_SDEnableAsteriskExtension(boolean _SDEnableAsteriskExtension) {
            this._SDEnableAsteriskExtension = _SDEnableAsteriskExtension;
        }

        public String get_MARS_HelpLine_Number() {
            return _MARS_HelpLine_Number;
        }

        public void set_MARS_HelpLine_Number(String _MARS_HelpLine_Number) {
            this._MARS_HelpLine_Number = _MARS_HelpLine_Number;
        }

        public String get_SIPExtPattern() {
            return _SIPExtPattern;
        }

        public void set_SIPExtPattern(String _SIPExtPattern) {
            this._SIPExtPattern = _SIPExtPattern;
        }

        public String get_TSPID() {
            return _TSPID;
        }

        public void set_TSPID(String _TSPID) {
            this._TSPID = _TSPID;
        }

        public String get_SIPPwdPattern() {
            return _SIPPwdPattern;
        }

        public void set_SIPPwdPattern(String _SIPPwdPattern) {
            this._SIPPwdPattern = _SIPPwdPattern;
        }
		public String get_InLoadAPI_URL() {
			return _InLoadAPI_URL;
		}
		public void set_InLoadAPI_URL(String _InLoadAPI_URL) {
			this._InLoadAPI_URL = _InLoadAPI_URL;
		}

        public boolean get_SDOnlyNearZoneMode() {
            return _SDOnlyNearZoneMode;
        }

        public void set_SDOnlyNearZoneMode(Boolean SDOnlyNearZoneMode) {
            this._SDOnlyNearZoneMode = SDOnlyNearZoneMode;
        }

        public int get_WallRefreshTimer() {
            return _WallRefreshTimer;
        }

        public void set_WallRefreshTimer(int WallRefreshTimer) {
            this._WallRefreshTimer = WallRefreshTimer;
        }
        public int get_SDEnableOdometerInput() {
            return _SDEnableOdometerInput;
        }

        public void set_SDEnableOdometerInput(int SDEnableOdometerInput) {
            this._SDEnableOdometerInput = SDEnableOdometerInput;
        }
        public boolean get_SDEnableReceiptEmail() {
            return _SDEnableReceiptEmail;
        }

        public void set_SDEnableReceiptEmail(Boolean SDEnableReceiptEmail) {
            this._SDEnableReceiptEmail = SDEnableReceiptEmail;
        }

        public boolean get_SDEnableStatsForVoip() {
            return _SDEnableStatsForVoip;
        }

        public void set_SDEnableStatsForVoip(Boolean SDEnableStatsForVoip) {
            this._SDEnableStatsForVoip = SDEnableStatsForVoip;
        }


	}// GeneralSettings Class

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *---------------------------------------------------- TopupCustomerBalance Class --------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */

	public static class TopupCustomerBalance {

		private String _ResultMsg;
		private String _Amount;
		private String _CurrentBalance;

		public TopupCustomerBalance() {
			this._ResultMsg = "";
			this._Amount = "";
			this._CurrentBalance = "";
		}

		public String get_Amount() {
			return _Amount;
		}

		public void set_Amount(String Amount) {
			this._Amount = Amount;
		}

		public String get_ResultMsg() {
			return _ResultMsg;
		}

		public void set_ResultMsg(String ResultMsg) {
			this._ResultMsg = ResultMsg;
		}

		public String get_CurrentBalance() {
			return _CurrentBalance;
		}

		public void set_CurrentBalance(String CurrentBalance) {
			this._CurrentBalance = CurrentBalance;
		}

	}// TopupCustomerBalance Class

	/*-------------------------------------------------------GetSDZoneList class------------------------------------------------------------*/

	public class SdZoneList {
		public String Zonename;
		public String Zonedescription;

		public SdZoneList() {
			Zonename = "";
			Zonedescription = "";
		}

		@Override
		public String toString() {
			return Zonename + "|" + Zonedescription;
		}

	}

	/*-------------------------------------------------------End of GetSDZoneList class------------------------------------------------------------*/

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------------ FareEstimation Class ----------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	public static class FareEstimation {

		private String _fare;
		private String _distance;
		private String _time;

		public FareEstimation() {
			this._fare = "";
		}

		public String get_fare() {
			return _fare.trim();
		}

		public void set_fare(String fare) {
			this._fare = fare;
		}

		public String get_distance() {
			return _distance.trim();
		}

		public void set_distance(String distance) {
			this._distance = distance;
		}

		public String get_time() {
			return _time.trim();
		}

		public void set_time(String time) {
			this._time = time;
		}

	}

	/*----------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------BalanceCalculationForVoucher----------------------------------------------------
	 *---------------------------------------------------------------------------------------------------------------------*/

	public static class BalanceCalculationForVoucher {
		private boolean _Result;

		public void BalanceCalculationForVoucher() {
			this._Result = false;
		}

		public void set_Result(boolean Result) {
			this._Result = Result;
		}

		public boolean get_Result() {
			return this._Result;
		}
	}

    public static class ClassofService {
        public String _APF;
        public String _PUM;
        public String _ADU;
        public String _ADC;
        public String _ATU;
        public String _ATC;
        public String _PUT;
        public String _APC;
        public String _CPC;
        public String _ClassOfServiceID;
        public String _DefaultClassOfService;

        public ClassofService() {
            this._APF = "";
            this._PUM = "";
            this._ADU = "";
            this._ADC = "";
            this._ATU = "";
            this._ATC = "";
            this._PUT = "";
            this._APC = "";
            this._CPC = "";
            this._ClassOfServiceID = "";
            this._DefaultClassOfService = "";
        }

        public String get_APF() {
            return _APF;
        }

        public void set_APF(String _APF) {
            this._APF = _APF;
        }

        public String get_PUM() {
            return _PUM;
        }

        public void set_PUM(String _PUM) {
            this._PUM = _PUM;
        }

        public String get_ADU() {
            return _ADU;
        }

        public void set_ADU(String _ADU) {
            this._ADU = _ADU;
        }

        public String get_ADC() {
            return _ADC;
        }

        public void set_ADC(String _ADC) {
            this._ADC = _ADC;
        }

        public String get_ATU() {
            return _ATU;
        }

        public void set_ATU(String _ATU) {
            this._ATU = _ATU;
        }

        public String get_ATC() {
            return _ATC;
        }

        public void set_ATC(String _ATC) {
            this._ATC = _ATC;
        }

        public String get_PUT() {
            return _PUT;
        }

        public void set_PUT(String _PUT) {
            this._PUT = _PUT;
        }

        public String get_APC() {
            return _APC;
        }

        public void set_APC(String _APC) {
            this._APC = _APC;
        }

        public String get_CPC() {
            return _CPC;
        }

        public void set_CPC(String _CPC) {
            this._CPC = _CPC;
        }

        public String get_ClassOfServiceID() {
            return _ClassOfServiceID;
        }

        public void set_ClassOfServiceID(String _ClassOfServiceID) {
            this._ClassOfServiceID = _ClassOfServiceID;
        }

        public String get_DefaultClassOfService() {
            return _DefaultClassOfService;
        }

        public void set_DefaultClassOfService(String _DefaultClassOfService) {
            this._DefaultClassOfService = _DefaultClassOfService;
        }

        public String toString(){
            String temps = null;

            temps = this._ClassOfServiceID + Character.toString(Constants.COLSEPARATOR) + this._APF
                    + Character.toString(Constants.COLSEPARATOR) + this._PUM
                    + Character.toString(Constants.COLSEPARATOR) + this._PUT
                    + Character.toString(Constants.COLSEPARATOR) + this._ADU
                    + Character.toString(Constants.COLSEPARATOR) + this._ADC
                    + Character.toString(Constants.COLSEPARATOR) +  this._ATU
                    + Character.toString(Constants.COLSEPARATOR) + this._ATC
                    + Character.toString(Constants.COLSEPARATOR) + this._APC
                    + Character.toString(Constants.COLSEPARATOR) + this._CPC
                    + Character.toString(Constants.COLSEPARATOR) + this._DefaultClassOfService;


            return temps;
        }
    }

    public static class ReceiptEmail {
        boolean response = false;

        public ReceiptEmail() {
            response = false;
        }

        public boolean isResponse() {
            return response;
        }

        public void setResponse(boolean response) {
            this.response = response;
        }
    }



}// WS_Response Class
