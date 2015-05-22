package itcurves.ncs;

import itcurves.ncs.bluebamboo.StringUtil;
import itcurves.ncs.webhandler.CallingWS;
import itcurves.ncs.webhandler.WS_Response;
import itcurves.ncs.webhandler.WS_Response.CCMappings;
import itcurves.ncs.webhandler.WS_Response.CCProcessingCompany;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class AVL_Service extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	static int servPort = 2015;
	static int avlPort = 2014;

	static InetAddress serverAddress;
    static String serverAddress1;
	static DatagramSocket socket = null;
	static DatagramPacket tmpPacket, outPacket, inPacket = null;
	public static String address = "";
	public int CountToCheckMeterStatus = 0;

	public static SharedPreferences pref;
	public static SharedPreferences pref_restrictedP;
	final DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static  SimpleDateFormat dateFormat;
	private final NetworkServiceBinder networkServiceBinder = new NetworkServiceBinder();

	private static final int TWO_MINUTES = 120000;

	protected Location lastLocation = null;
	private Thread msgSenderThread;
	private Thread msgReciverThread;
    public static Thread SoftMeterThread;
	static LocationManager locManager;
	public static TelephonyManager tm;
	private static ConnectivityManager cnn;
	private static WifiManager wifiMan;

	static String partialStr, msgPreviousValue;
	Calendar cal = Calendar.getInstance();
	private long msgID = 0;
	public static boolean isNetworkReachable = false;
	public static boolean loggedIn;
	public static boolean showEstMiles;
	public static boolean btMeterAvailable;
	public static boolean centrodyneMeterAvailable;
	public static boolean vivotechAvailable;
	public static boolean blueBambooAvailable;
	public static boolean enableAudioCommands;
	public static boolean sdEnableBreak, sdEnablePPT, sdEnableEmergency;
	public static boolean sdEnableCalcEstOnDropped;
	public static boolean sdCalcEstViaMRMSService;
	public static boolean tipVoiceEnabled;
	public static boolean reverseGeocodeFromMRMSService;
	public static boolean showManifestWallOnSD;
	public static boolean sendBidOffers; // To Hide the Bid Tab
	public static boolean restrictSoftDropIfMeterConnected;
	public static boolean enableDialiePackageOnDevice;
	public static boolean bShuttle;
	public static boolean SDShowFlaggerConfirmation;
	public static boolean AllowDetailedLogInFileAndSQL;

	public static boolean ShowSDDriverPhoto;
	public static boolean ShowSDAVLOnStatus;
	public static boolean ShowSDStandRankOnStatus;
	public static boolean ShowSDTaxiMeterOnStatus;
	public static boolean ShowSDBackSeatOnStatus;
	public static boolean ShowSDApartmentOnTripDetail;
	public static boolean ShowSDFundingSourceOnTripDetail;
	public static boolean ShowSDPaymentTypeOnTripDetail;
	public static boolean ShowSDCoPayOnTripDetail;
	public static boolean ShowSDOnlyFareOnPaymentScreen;
	public static boolean ShowStandsOnSD;
	public static boolean ShowNearZoneFeatureOnSD;
	public static boolean ShowClientPhoneNoOnReceipt;
	public static boolean SDDropNavigationWithMap;
	public static boolean bArabClient;
	public static boolean ShowTogglePickUpDropOffBTN;
	public static boolean Allow_Book_In_AutoZone;
	public static boolean SDShowPUDateTimeOnTripDetail;
	private boolean isLogOffRespPending = false;

	public static Float allowedSpeedForMessaging;
	public static long timerForCradleLogout;
	public static double allowableCallOutDistance;
	public static String sDMaxLengthOfTripList;
	public static String HEXColor;
	public static boolean ShowHandShakeButtonOnLogin;
	public static boolean showReceiptPrintingDialog;
	public static int sdMaxAllowedBreaksInOneDay;
	public static int sdTotalBreaksTaken;
	private static boolean IsAddressValid = false;
	public static ArrayList<String> hideCostOnSDByFundingSource;
	public static ArrayList<String> showEstdCostOnSDByFundingSource;
	public static String googleMapAPIKey;
	public static String callOutRequestPrompt;
	public static String callOutRequestPrompt_ar;
	public static String[] DeviceMessageScreenConfig;
	public static int WallTripDistanceByGoogle;
	public static String SDDefaultLanguageSelection;
	public static boolean SendDriverPicToVeriFone;
	public static String[] ResponseIDToRemoveTripFromWall;
	public static float SDFlaggerButtonSize;
	// change by hamza
	public static boolean SDShowLanguageChangeOption;
	public static String SDUnitOfDistance;
	public static String SDUnitOfCurrency, SDBreakEmergencyPPTPosition;
	public static boolean PPV_UsePPVModule;
	public static boolean EnableAudioForMessageUtility;
	public static boolean SDShowProceedToPickupOnTripOffer;
	public static int SDReprintTimeOutSec;
	public static int SDVFCashVoucherDialogTimeOutSec;
	public static boolean SDEnableVoiceIfNewTripAddedOnWall;
	public static boolean SDShowPassengerNameOnWall;
	public static boolean SDBreakActionOnSingleTap;
	public static String SDBreakColor;
	public static String SDResumeColor;
	public static boolean SDShowVoucherButton;
	public static boolean CreditCardFeature;
	public static boolean SDEnableTripListSynchronization;
	public static boolean SDShowMileageOnStatusTab;
	public static boolean SDEnableSignatureFeature;
	public static String SDCentralizedAsteriskService;
	public static boolean SDEnableCentralizedAsteriskService;
	public static String ASCS_HelpLine_Number;
	public static boolean SDEmergencyConfirmation;
	public static String NotAllowActionIfAway;
	public static String MessageTypeIfActionNotAllowed;
	public static boolean ShowAddressOnWall;
	public static String CompanyName_Receipt;
	public static String CompanyURL;
	public static boolean SDEnableManualFlagger;
	public static boolean SDEnableTwoStepPaymentProcessing;
	public static String SDPaymentButtonCaptionFor2ndStep;
	public static int Allow_Promotion_In_MARS_SDApp_Both;
	public static boolean SDShowFontChangeOption;
	public static boolean SDEnableMeterLocking;
	public static int SDRingerCountForTripOffer;
	public static boolean SDShowServiceID;
	public static boolean SDShowPhoneandIMEI;
	public static long LocationAge;
    public static String SDAsteriskExt;
    public static String SDAsteriskPwd;
    public static String SDAsteriskServer;
    public static String SDAsteriskHangUpTime;
    public static String SDAsteriskDispatcherExt;
    public static boolean SDEnableAsteriskExtension;
    public static String  MARS_HelpLine_Number;
    public static String SIPExtPattern;
    public static String SIPPwdPattern;
    public static String TSPID;
    public static String InLoadAPI_URL;
    public static boolean SDOnlyNearZoneMode;
    public static int WallRefreshTimer;
    public static int SDEnableOdometerInput;
    public static boolean SDEnableReceiptEmail;
    public static boolean SDEnableStatsForVoip;

	// public static String addressFromMRMS;
	protected static String packetRcvd;
	protected static String deviceID;
	public static Handler msgHandler;
	private boolean locationUpdates = false;
	private boolean network_exception = false;
	private boolean previousNetworkStateConnected = true;
	private boolean fetchTripListDue = false;

	public static PackageInfo pInfo;
	public static String appVersion;

	ActivityManager Appmgr;
	protected List<ActivityManager.RunningAppProcessInfo> applications;
	protected PackageManager pm;
	CharSequence c = null;

	ArrayList<String> deviceApps = new ArrayList<String>();
	String listString;
	static String webServiceURL = "http://70.90.90.129:88/Service.asmx";
	static String slimCD_URL = "https://trans.slimcd.com/wswebservices/transact.asmx";
	static final String soapAction_killApps = "http://Itcurves.net/getSmartDeviceApplications";
	static final String soapAction_CCProcessors = "http://Itcurves.net/GetCreditCardProcessingCompany";
	static final String soapAction_CCMappings = "http://Itcurves.net/GetCreditCardMappings";
	static final String soapAction_SpecializedWallTrips = "http://Itcurves.net/GetSpecializedWallTrips";
	static final String soapAction_WallTrips = "http://Itcurves.net/GetWallTrips";
	static final String soapAction_MessageHistory = "http://Itcurves.net/GetMessageHistory";
	static final String soapAction_TripDetail = "http://Itcurves.net/GetAssignedAndPendingTripsInString";
	static final String soapAction_cannedMsgs = "http://Itcurves.net/GetCannedMessages";
	static final String soapAction_ProcessSale = "http://Itcurves.net/Process_Sale";
	static final String soapAction_ProcessPreAuth = "http://Itcurves.net/Process_PreAuth";
	static final String soapAction_ProcessPostAuth = "http://Itcurves.net/Process_PostAuth";
	static final String soapAction_Process_Inquiry = "http://Itcurves.net/Process_Inquiry";
	static final String soapAction_UpdateSmartDeviceMeterInfo = "http://Itcurves.net/UpdateSmartDeviceMeterInfo";
	static final String soapAction_SDGetGeneralSettings = "http://Itcurves.net/SDGetGeneralSettings";
	static final String soapAction_fetchAssignedAndPendingTrips = "http://Itcurves.net/GetAssignedAndPendingTrips";
	static final String soapAction_SDGetAdjacentZones = "http://Itcurves.net/SDGetAdjacentZones";
	static final String soapAction_CalculateRouteByStreetAddress = "http://Itcurves.net/CalculateRouteByStreetAddressWithCostEstimates";
	static final String soapAction_ReverseGeoCodeBylatlng = "http://Itcurves.net/ReverseGeoCodeBylatlng";
	static final String soapAction_ManifestWallTrips = "http://Itcurves.net/GetManifestSummaryInfo";
	static final String soapAction_DriverTakenBreakStats = "http://Itcurves.net/GetLiveMiscInfo";
	static final String soapAction_driverGetPersonBalance = "http://Itcurves.net/GetPersonBalance";
	static final String soapAction_GetBalanceAndBlackListStatus = "http://Itcurves.net/GetBalanceAndBlackListStatus";
	static final String soapAction_UpdateVehicleMileage = "http://Itcurves.net/UpdateVehicleMileage";
	static final String soapAction_UploadSignature = "http://Itcurves.net/UploadSignature";
	static final String soapAction_TopupCustomerBalanceFromBookinApp = "http://Itcurves.net/TopupCustomerBalanceFromBookinApp";
	static final String soapAction_GetSdZoneList = "http://Itcurves.net/GetSDZoneList";
	static final String soapAction_PostXML = "http://tempuri.org/TransGateway/Transact/PostXML";
	static final String soapAction_CalculateDiscount = "http://Itcurves.net/CheckPromotionValidity";
    static final String soapAction_CalculateBalance = "http://Itcurves.net/PPV_CheckallowedBalanceOnTripCompletion";
    static final String soapAction_ClassofService = "http://Itcurves.net/GetClassOfServiceRates";
    static final String soapAction_SendEmail = "http://Itcurves.net/SendPaymentReceiptToCustomer";

	TelephonyManager tM;
	ActivityManager servMng;
	PackageManager pMng;

	static Intent intent = null;

	public static Map<String, CCProcessingCompany> CCProcessorList;
	public static Map<String, CCMappings> CardMappings;
	public static Map<String, IMessageListener> msg_listeners = new HashMap<String, IMessageListener>(); // Updated
	protected static Map<String, IOMessage> msg_list = new Hashtable<String, IOMessage>();
	public static final Map<String, String> server_list = new HashMap<String, String>();
	public static Map<String, String> tempMap;

	ActivityManager mgr;
	protected List<ActivityManager.RunningTaskInfo> apps;
	protected String signalStrength;

	WS_Response.KillApps restrictedApps;
	public static String[] cmessages_Array;
	public static String[] Zone_NAMES;
	public static String[] Adjacent_ZoneNAMES;
	long connectivityResumeTime = 0;
	public static float current_Direction;
    public static ArrayList<WS_Response.ClassofService> softMeterRates;

    protected GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;


    static void callTaxiPlexerWall(){
        for (IMessageListener list : AVL_Service.msg_listeners.values())
            list.receivedWallTrips(TaxiPlexer.WALLTrips);
    }

    static void callTaxiPlexerManifestWall(ArrayList<ManifestWallTrip> manifestWallTripArray){
        for (IMessageListener list : AVL_Service.msg_listeners.values())
            list.receivedManifestWallTrips(manifestWallTripArray);
    }
	/*--------------------------------------------------------------connectToServer-----------------------------------------------------------------------------*/
	public static boolean connectToServer(String address) {

		try {
			// server_url = address;
            InetAddress[] InetArray = InetAddress.getAllByName(address);// server address;
            serverAddress1 = address;
			if (InetArray.length > 0)
				serverAddress = InetArray[0];
			else {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.invalidServerIP("Invalid Server IP");
				return false;
			}

			if (socket == null)
				socket = new DatagramSocket(servPort);
			isNetworkReachable = true;
			return true;
		} catch (UnknownHostException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.invalidServerIP("Invalid Server IP");
			return false;

		} catch (SocketException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values()) {
				list.invalidServerIP("Socket Exception");
				list.exception(e.getClass() + "| ConnectToServer(): " + e.getLocalizedMessage());
			}
			return false;
		} catch (Exception e) {

			return false;
		}
	}

	/*--------------------------------------------------------------isBackgroundDataEnabled-----------------------------------------------------------------------------*/
	public boolean isNetworkConnected() {
		try {
			cnn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cnn.getActiveNetworkInfo() != null ? cnn.getActiveNetworkInfo().isConnected() : false)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	/*--------------------------------------------------------------onCreate-------------------------------------------------------------------------------*/
	@Override
	public void onCreate() {

		server_list.put("test", "70.88.142.138");// 70.88.142.138
		server_list.put("tlpa", "192.168.9.12");
		server_list.put("infonetmytaxi", "86.51.177.115"); // server_list.put("test", "192.168.4.40");
		server_list.put("ncstest", "192.168.4.150");
		server_list.put("mars", "sdhs.mars.itcurves.us:84");
		server_list.put("vango", "72.84.192.8");
		server_list.put("ycsacramento", "sdhs.itc.ycabsac.com");
		server_list.put("ycmemphis", "sdhs.itc.premierofmemphis.com");
		server_list.put("exec2000", "sdhs.exec2000.itcurves.us");
		server_list.put("regencytest", "192.168.10.202"); // 6.33_08
		server_list.put("regencycab", "sdhs.regency.itcurves.us");
		server_list.put("regencydulles", "sdhs.dullesregency.itcurves.us");
		server_list.put("ycapwc", "sdhs.itc.yellowcabpw.com");
		server_list.put("sas", "sdhs.sas.itcurves.us");
		server_list.put("chtaxi", "mobile.dispatch.syntransys.com");
		server_list.put("unitedcab", "sdhs.united.itcurves.us");
		server_list.put("e2taxi", "sdhs.e2taxi.itcurves.us");
		server_list.put("valley", "SDHS.Valley.itcurves.us");
		server_list.put("ycfrederick", "sdhs.ycf.itcurves.us");
		server_list.put("ventura", "sdhs.vds.itcurves.us");
		server_list.put("ycv", "sdhs.ycv.itcurves.us");
		server_list.put("mobilebay", "sdhs.mobile.itcurves.us");
		server_list.put("firstcom", "sdhs.firstcom.itcurves.net");
		server_list.put("gentlecare", "sdhs.itc.azgentlecaretransport.com");
		server_list.put("greencab", "sdhs.greencab.itcurves.us");
		server_list.put("ycindy", "sdhs.itc.ycindy.com");
		server_list.put("grovetransit", "sdhs.grovetransit.com");
		server_list.put("onthemove", "sdhs.otm.itcurves.us");
		server_list.put("diamond", "sdhs.diamond.itcurves.us");
		server_list.put("demo", "sdhs.demo.itcurves.us");
		server_list.put("uniontaxi", "sdhs.uniontaxi.us"); // 6.34_01
		server_list.put("hcc", "sdhs.hcc.itcurves.us");
		server_list.put("tlc", "sdhs.tlc.itcurves.us");
        server_list.put("cindytest", "70.88.142.138");

		Appmgr = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		pm = this.getPackageManager();

		mgr = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);

		dateFormat = new SimpleDateFormat("MMddyyyyHHmmssSSS", Locale.US);

		PhoneStateListener signalListener = new PhoneStateListener() {
			@Override
			public void onSignalStrengthsChanged(SignalStrength signal) {
				if (signal.isGsm())
					signalStrength = Integer.toString(signal.getGsmSignalStrength() * 100 / 31);
				super.onSignalStrengthsChanged(signal);
			}
		};

		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(signalListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

		wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		cnn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		pref = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
		pref_restrictedP = getSharedPreferences(Constants.PREFS_RP, MODE_PRIVATE);

		registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
			appVersion = pInfo.versionName;
		} catch (NameNotFoundException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getClass() + "| onCreate()| " + e.getLocalizedMessage());
			appVersion = "6.21";
		}

		address = pref.getString("Address", "Unknown Address");

		AVL_Service.msgHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case MsgType.HandShakeResp :

						startLocationListeners();
                        mGoogleApiClient.connect();
						final String[] handshakeResp = (String[]) msg.obj;
						new Thread() {
							@Override
							public void run() {

								if (fetchGeneralSettings())
									if (pref.getBoolean("AllowCreditCard", true)) {
										if (fetchCCProcessorsList())
											for (IMessageListener list : AVL_Service.msg_listeners.values())
												list.receivedHandshakeResponse(handshakeResp);
									} else
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedHandshakeResponse(handshakeResp);
								if (AVL_Service.DeviceMessageScreenConfig != null) {
									if (AVL_Service.DeviceMessageScreenConfig[3].equalsIgnoreCase("1"))
										fetchCannedMsgsList();
								}
								fetchGetSdZoneList();

								if (pref.getBoolean("isCompanyProperty", false))
									fetchBlockedAppsList();
								else
									msgHandler.removeMessages(MsgType.kill);
							}// run

						}.start();

						msgHandler.sendMessageDelayed(obtainMessage(MsgType.ReverseGeoCode, Float.MAX_VALUE), 10000);
						msgHandler.sendMessageDelayed(obtainMessage(MsgType.SDHEARTBEAT), 1000);

						break;

					case MsgType.Login_Resp :

						new Thread() {
							@Override
							public void run() {
								if (fetchDriverTakenBreakStats())
									fetchAdjacentZones();
							}// run

						}.start();

						break;
					case MsgType.ReverseGeoCode :
						final Float dist = (Float) msg.obj;
						msgHandler.removeMessages(MsgType.ReverseGeoCode);
						new Thread() {
							@Override
							public void run() {
								if ((lastLocation != null) && (isNetworkReachable)) {
									String streetAddress = getStreetAddress();
									streetAddress = TaxiPlexer.is_GPS_AVAILABLE ? streetAddress : "GPS FAIL - " + streetAddress;
									if (dist > pref.getFloat(Constants.PREF_GPSAt, 600))
										msgHandler.obtainMessage(MsgType.AVL, streetAddress).sendToTarget();
								} else {
									if (dist > pref.getFloat(Constants.PREF_GPSAt, 600))
										msgHandler.obtainMessage(MsgType.AVL, address).sendToTarget();
								}
							}// run

						}.start();
						break;

					case MsgType.SDHEARTBEAT :
						msgHandler.removeMessages(MsgType.SDHEARTBEAT);
						msgHandler.sendMessageDelayed(obtainMessage(MsgType.SDHEARTBEAT), pref.getLong("HeartBeatTimer", 10000));
						networkServiceBinder.sendHeartBeat();
						break;

					case MsgType.AVL :
						address = (String) msg.obj;
						if (address.contains("GPS FAIL") || address.contains("GPS OFF")) {
							String add = address.substring(10);
							pref.edit().putString("Address", add).commit();
						} else {
							pref.edit().putString("Address", address).commit();
						}

						if (loggedIn)
							for (IMessageListener list : AVL_Service.msg_listeners.values())
								list.receivedLocationChange(address);

						networkServiceBinder.sendAVLData();

						msgHandler.removeMessages(MsgType.AVL);
						msgHandler.sendMessageDelayed(obtainMessage(MsgType.ReverseGeoCode, Float.MAX_VALUE), pref.getLong(Constants.PREF_GPS_Atleast, 90000));

						break;

					case MsgType.kill :
						if (pref.getBoolean("isCompanyProperty", false)) {
							handleKillMsg();
							msgHandler.sendMessageDelayed(obtainMessage(MsgType.kill), 3000);
						}
						break;
				}// Switch

			}
		};
	}// onCreate
	/*------------------------------------------------getStreetAddress------------------------------------------------------------------------*/
	protected String getStreetAddress() {
		Geocoder myGeocoder = new Geocoder(this);
		List<Address> addressList = null;
		int retry = 3;
		String[] tempAddress = new String[3];
		try {
			while (retry-- > 0) {
				// addressList = myGeocoder.getFromLocation(41.55999, -72.65820, 1);
				addressList = myGeocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);

				if ((addressList.get(0).getAddressLine(0) != null) && (addressList.get(0).getAddressLine(1) != null) && (addressList.get(0).getAddressLine(2) != null)) {
					tempAddress[0] = addressList.get(0).getAddressLine(1).split(",")[0];
					try {
						tempAddress[1] = addressList.get(0).getAddressLine(1).split(",")[1].trim().split(" ")[0];
						tempAddress[2] = addressList.get(0).getAddressLine(1).split(",")[1].trim().split(" ")[1];

					} catch (Exception e) {
						// TODO: handle exception
					}

					if (tempAddress[2] != null) {
						IsAddressValid = true;
						break;
					} else
						IsAddressValid = false;
				} else
					IsAddressValid = false;
			}
			return (addressList.get(0).getAddressLine(0) + ", " + addressList.get(0).getAddressLine(1) + ", " + addressList.get(0).getAddressLine(2));
		} catch (Exception ex) {
			if (retry == 1)
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception(ex.getClass() + "| ReverseGeocode(): " + ex.getLocalizedMessage() + "|");
		}
		return "Unknown Address";
	}

	/*------------------------------------------------fetchCCProcessorsList------------------------------------------------------------------------*/
	protected boolean fetchCCProcessorsList() {

		try {

			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching CC Processors");

			if (CCProcessorList != null)
				CCProcessorList.clear();
			if (CardMappings != null)
				CardMappings.clear();

			StringBuffer envelope = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetCreditCardProcessingCompany xmlns=\"http://Itcurves.net/\"><DeviceID>")
				.append(deviceID)
				.append("</DeviceID></GetCreditCardProcessingCompany></soap:Body></soap:Envelope>");

			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_CCProcessors, envelope.toString());
			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("GetCreditCardProcessingCompanyResult")) {
					CCProcessorList = wsResponse.cardProcessorList;
					if (fetchCCMapping()) {

						for (IMessageListener list1 : AVL_Service.msg_listeners.values())
							list1.hideProgressDialog();

						return true;
					}
				} else
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("Fetch CCProcessorsList Failed. " + wsResponse.errorString);
			else
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("Fetch CCProcessorsList Failed");

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getClass() + "| fetchCCProcessorsList() " + e.getLocalizedMessage());
		}

		for (IMessageListener list1 : AVL_Service.msg_listeners.values())
			list1.hideProgressDialog();

		return false;
	}
	/*------------------------------------------------fetchCCMapping------------------------------------------------------------------------*/
	private boolean fetchCCMapping() {

		try {
			StringBuffer envelope = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetCreditCardMappings xmlns=\"http://Itcurves.net/\"><DeviceID>")
				.append(deviceID)
				.append("</DeviceID></GetCreditCardMappings></soap:Body></soap:Envelope>");
			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching CC Mapings");
			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_CCMappings, envelope.toString());
			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("GetCreditCardMappingsResult")) {
					CardMappings = wsResponse.CardMappingList;
					return true;
				} else {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("FetchCCMapping Failed. " + wsResponse.errorString);
					return false;
				}
			else {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception("FetchCCMapping Failed");
				return false;
			}

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getClass() + "| fetchCCMapping() " + e.getLocalizedMessage());
		}

		return false;

	}

	/*------------------------------------------------startLocationListeners-------------------------------------------------------------------------------*/
    protected void startLocationListeners() {

        if (!locationUpdates) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        locationUpdates = true;
    }


	/*--------------------------------------------------------------handleKillMsg-------------------------------------------------------------------------------*/
	protected void handleKillMsg() { // Updated

		String restrictedApp = "";
		if (restrictedApps.isExcluded()) {

			// for (ActivityManager.RunningAppProcessInfo RunningP : applications) {

			if (restrictedApps.process_Name.contains(mgr.getRunningTasks(30).get(0).topActivity.getPackageName())) {
				restrictedApp = mgr.getRunningTasks(30).get(0).topActivity.getPackageName();
				if (restrictedApp.equalsIgnoreCase("com.android.launcher") // when home key is pressed
						|| restrictedApp.equalsIgnoreCase("com.dell.launcher")) {
					TaxiPlexer.SHOW_GPS_PAGE = false;
					Toast.makeText(this, "Killing: " + restrictedApp, Toast.LENGTH_SHORT).show();
					Appmgr.restartPackage(restrictedApp);
				}

				else if (restrictedApp.equalsIgnoreCase("com.android.settings") && TaxiPlexer.SHOW_GPS_PAGE == true) { // when GPS page is displayed
					Toast.makeText(this, "Allowing: " + restrictedApp, Toast.LENGTH_SHORT).show();
				}

				else { // any restricted app launched

					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(startMain);

					Toast.makeText(this, "Killing: " + restrictedApp, Toast.LENGTH_SHORT).show();

					Appmgr.restartPackage(restrictedApp);

					if (msg_listeners.size() > 0) {
						Intent dialogIntent = new Intent(getBaseContext(), TaxiPlexer.class);
						dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplication().startActivity(dialogIntent);
					}

				}
			}// if
				// }// for
		}

	}// handleKillMsg

	/*--------------------------------------------------------------Sender Runnable-----------------------------------------------------------------------*/
	private final Runnable sender_Runnable = new Runnable() {

		public void run() {

			while (!Thread.interrupted()) {

				synchronized (msg_list) { // msg_list is a linklist of IOMessage Class Objects
					if (!msg_list.isEmpty()) {
						if (isNetworkConnected()) {
							try {

								for (IOMessage msg : msg_list.values()) {
									if (((System.currentTimeMillis() / 1000) - (msg.getSendTime() / 1000)) > MsgValidity.Short) {
										msg.setSendTime(System.currentTimeMillis()); // Update the timmer before sending again
										outPacket = msg._outPacket;
										try {
											socket.send(outPacket);
											if (network_exception || !isNetworkReachable) {
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.exception("[Connectivity Resumed][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable]");
												network_exception = false;
												isNetworkReachable = true;
											}
											Log.w("Sending " + MsgType.getDesc(msg.getType()) + " Msg", "Tag " + msg.getTag() + " Msg= " + msg.getBody());
											if (msg.getType() == MsgType.SDHEARTBEAT) {
												msg_list.remove(msg.getTag());
												break;
											}

											if (msg.getType() == MsgType.LogoffReq) {
												isLogOffRespPending = true;
											}

											if (AVL_Service.loggedIn)
												if (!previousNetworkStateConnected) {
													fetchTripListDue = true;
													connectivityResumeTime = 0;
													previousNetworkStateConnected = true;
												}

											if (SDEnableTripListSynchronization) {
												if (fetchTripListDue == true && connectivityResumeTime == 0) {
													if (!msg_list.containsValue(MsgType.TripResponse))
														connectivityResumeTime = System.currentTimeMillis();
												} else if (fetchTripListDue == true && ((System.currentTimeMillis() / 1000 - connectivityResumeTime / 1000) > 0)) {
													connectivityResumeTime = 0;
													fetchTripListDue = false;
													for (IMessageListener list : AVL_Service.msg_listeners.values())
														list.fetchAssignedAndPendingTrips();
												}
											}

										} catch (IOException io) {
											isNetworkReachable = false;
											if (network_exception)
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.exceptionToast("[Connectivity Stopped][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable] " + io.getLocalizedMessage());
											else if (!network_exception) {
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.exception("[Connectivity Stopped][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable] " + io.getLocalizedMessage());
												network_exception = true;
											}
											Log.w("Sender Thread", io.getLocalizedMessage());

											if (isNetworkConnected()) {
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.receivedSetNetworkStatus(true);
											} else {
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.receivedSetNetworkStatus(false);
											}
											if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.receivedSetGPSstatus(true);
											} else {
												for (IMessageListener list : AVL_Service.msg_listeners.values())
													list.receivedSetGPSstatus(false);
											}

										}
									}
									// msg_list.notifyAll();
									// break;
								}// for

							} catch (ConcurrentModificationException e) {
								for (IMessageListener list : AVL_Service.msg_listeners.values())
									list.exceptionToast("[Connectivity Stopped][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable] " + e.getLocalizedMessage());
								break;
							}
						} else {
							if (isNetworkReachable) {
								for (IMessageListener list : AVL_Service.msg_listeners.values())
									list.exception("[Connectivity Stopped][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable] ");
								isNetworkReachable = false;
							}
							// if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
							// for (IMessageListener list : AVL_Service.msg_listeners.values())
							// list.exception("[Exception in Sender Thread] [onProviderDisabled]" + " [GPS OFF]");
							// // isNetworkReachable = false;
							// }
							previousNetworkStateConnected = false;
							fetchTripListDue = false;
							connectivityResumeTime = 0;
						}
						msg_list.notifyAll();
						try {
							msg_list.wait(1000);
						} catch (InterruptedException e) {
							for (IMessageListener list : AVL_Service.msg_listeners.values())
								list.exception("[Connectivity Stopped][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable] " + e.getLocalizedMessage());
						}
					} else
						// if sending list is empty, then wait for any message to be added by sendMessageToServer() method
						try {
							msg_list.wait();
						} catch (InterruptedException e) {
							for (IMessageListener list : AVL_Service.msg_listeners.values())
								list.exception("[Connectivity Stopped][" + msg_list.values().size() + " msgs in queue to SDHS | sender_Runnable] " + e.getLocalizedMessage());
							Log.w("wait on send list", e.getLocalizedMessage());
						}
				}// synchronized
			}// while
		}
	}; // network_Runnable

	/*--------------------------------------------------------------Msg Reciver Runnable---------------------------------------------------------------*/
	private final Runnable reciver_Runnable = new Runnable() {
		public void run() {
			inPacket = new DatagramPacket(new byte[Constants.MAXRECEIVEBUFFERSIZE], Constants.MAXRECEIVEBUFFERSIZE);
			while (!Thread.interrupted()) {
				try {
					inPacket.setLength(Constants.MAXRECEIVEBUFFERSIZE);
					socket.receive(inPacket); // Thread Pauses here until a packet is received
					tmpPacket = inPacket;

					packetRcvd = MessageReader.getMessage(tmpPacket);
					if (packetRcvd != null) {
						IOMessage msgRcvd = new IOMessage(packetRcvd);
						if (Integer.valueOf(msgRcvd.getAckType()) == AckType.NEW) {

							String[] columns, rows;
							switch (Integer.valueOf(msgRcvd.getType())) {

								case MsgType.HandShakeResp :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									columns = columns[1].split("\\" + Character.toString(Constants.COLSEPARATOR));
									if ((columns.length >= 18)) {

										webServiceURL = columns[7];

										if (columns.length == 18) {
											pref
												.edit()
												.putLong(Constants.PREF_GPS_Atleast, Long.parseLong(columns[0]))
												.putFloat(Constants.PREF_GPSAt, Float.parseFloat(columns[1]))
												.putString("Company", columns[2])
												.putLong(Constants.Allowable_Stand_Distance, Long.parseLong(columns[3]))
												.putBoolean("isCompanyProperty", Boolean.parseBoolean(columns[5]))
												.putBoolean("ShowDropZone", Boolean.parseBoolean(columns[6]))
												.putBoolean("ForceDeviceOnCradle", Boolean.parseBoolean(columns[8]))
												.putBoolean("AtLocationButton", Boolean.parseBoolean(columns[9]))
												.putBoolean("TaxiMileagePrompt", Boolean.parseBoolean(columns[10]))
												.putBoolean("ShowWallTrips", Boolean.parseBoolean(columns[11]))
												.putBoolean("PostAuthDependent", Boolean.parseBoolean(columns[12]))
												.putFloat("MaxCCAmount", Float.parseFloat(columns[13]))
												.putBoolean("ShowAddressOnOffer", Boolean.parseBoolean(columns[14]))
												.putBoolean("ShowWallEstimates", Boolean.parseBoolean(columns[15]))
												.putBoolean("AllowZoneBookin", Boolean.parseBoolean(columns[16]))
												.putBoolean("AllowCreditCard", Boolean.parseBoolean(columns[17]))
												.putLong("HeartBeatTimer", 10000)
												.commit();

										} else if (columns.length == 19) {
											pref
												.edit()
												.putLong(Constants.PREF_GPS_Atleast, Long.parseLong(columns[0]))
												.putFloat(Constants.PREF_GPSAt, Float.parseFloat(columns[1]))
												.putString("Company", columns[2])
												.putLong(Constants.Allowable_Stand_Distance, Long.parseLong(columns[3]))
												.putBoolean("isCompanyProperty", Boolean.parseBoolean(columns[5]))
												.putBoolean("ShowDropZone", Boolean.parseBoolean(columns[6]))
												.putBoolean("ForceDeviceOnCradle", Boolean.parseBoolean(columns[8]))
												.putBoolean("AtLocationButton", Boolean.parseBoolean(columns[9]))
												.putBoolean("TaxiMileagePrompt", Boolean.parseBoolean(columns[10]))
												.putBoolean("ShowWallTrips", Boolean.parseBoolean(columns[11]))
												.putBoolean("PostAuthDependent", Boolean.parseBoolean(columns[12]))
												.putFloat("MaxCCAmount", Float.parseFloat(columns[13]))
												.putBoolean("ShowAddressOnOffer", Boolean.parseBoolean(columns[14]))
												.putBoolean("ShowWallEstimates", Boolean.parseBoolean(columns[15]))
												.putBoolean("AllowZoneBookin", Boolean.parseBoolean(columns[16]))
												.putBoolean("AllowCreditCard", Boolean.parseBoolean(columns[17]))
												.putLong("HeartBeatTimer", Long.parseLong(columns[18]))
												.putInt("BtnDisableTime", 10)
												.commit();
										} else if (columns.length == 20) {
											pref
												.edit()
												.putLong(Constants.PREF_GPS_Atleast, Long.parseLong(columns[0]))
												.putFloat(Constants.PREF_GPSAt, Float.parseFloat(columns[1]))
												.putString("Company", columns[2])
												.putLong(Constants.Allowable_Stand_Distance, Long.parseLong(columns[3]))
												.putBoolean("isCompanyProperty", Boolean.parseBoolean(columns[5]))
												.putBoolean("ShowDropZone", Boolean.parseBoolean(columns[6]))
												.putBoolean("ForceDeviceOnCradle", Boolean.parseBoolean(columns[8]))
												.putBoolean("AtLocationButton", Boolean.parseBoolean(columns[9]))
												.putBoolean("TaxiMileagePrompt", Boolean.parseBoolean(columns[10]))
												.putBoolean("ShowWallTrips", Boolean.parseBoolean(columns[11]))
												.putBoolean("PostAuthDependent", Boolean.parseBoolean(columns[12]))
												.putFloat("MaxCCAmount", Float.parseFloat(columns[13]))
												.putBoolean("ShowAddressOnOffer", Boolean.parseBoolean(columns[14]))
												.putBoolean("ShowWallEstimates", Boolean.parseBoolean(columns[15]))
												.putBoolean("AllowZoneBookin", Boolean.parseBoolean(columns[16]))
												.putBoolean("AllowCreditCard", Boolean.parseBoolean(columns[17]))
												.putLong("HeartBeatTimer", Long.parseLong(columns[18]))
												.putInt("BtnDisableTime", Integer.parseInt(columns[19]))
												.putString("NTEPNumber", "12-XXP")
												.commit();
										} else {
											pref
												.edit()
												.putLong(Constants.PREF_GPS_Atleast, Long.parseLong(columns[0]))
												.putFloat(Constants.PREF_GPSAt, Float.parseFloat(columns[1]))
												.putString("Company", columns[2])
												.putLong(Constants.Allowable_Stand_Distance, Long.parseLong(columns[3]))
												.putBoolean("isCompanyProperty", Boolean.parseBoolean(columns[5]))
												.putBoolean("ShowDropZone", Boolean.parseBoolean(columns[6]))
												.putBoolean("ForceDeviceOnCradle", Boolean.parseBoolean(columns[8]))
												.putBoolean("AtLocationButton", Boolean.parseBoolean(columns[9]))
												.putBoolean("TaxiMileagePrompt", Boolean.parseBoolean(columns[10]))
												.putBoolean("ShowWallTrips", Boolean.parseBoolean(columns[11]))
												.putBoolean("PostAuthDependent", Boolean.parseBoolean(columns[12]))
												.putFloat("MaxCCAmount", Float.parseFloat(columns[13]))
												.putBoolean("ShowAddressOnOffer", Boolean.parseBoolean(columns[14]))
												.putBoolean("ShowWallEstimates", Boolean.parseBoolean(columns[15]))
												.putBoolean("AllowZoneBookin", Boolean.parseBoolean(columns[16]))
												.putBoolean("AllowCreditCard", Boolean.parseBoolean(columns[17]))
												.putLong("HeartBeatTimer", Long.parseLong(columns[18]))
												.putInt("BtnDisableTime", Integer.parseInt(columns[19]))
												.putString("NTEPNumber", columns[20])
												.commit();
										}

										if (!loggedIn)
											msgHandler.obtainMessage(MsgType.HandShakeResp, columns).sendToTarget();

									} else if (!loggedIn || columns.length == 3 || columns.length == 2 || columns.length == 1) {
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedHandshakeResponse(columns);
									}
									break;
								case MsgType.ZFT :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									rows = columns[1].split("\\" + Character.toString(Constants.ROWSEPARATOR));
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedZFT(rows);
									break;
								case MsgType.registerResp :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									columns = columns[1].split("\\" + Character.toString(Constants.COLSEPARATOR));

									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.receivedRegisterResponse(columns);
									break;
								case MsgType.Login_Resp :
									isLogOffRespPending = false;
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									columns = columns[1].split("\\" + Character.toString(Constants.COLSEPARATOR));
									msgHandler.obtainMessage(MsgType.Login_Resp, columns).sendToTarget();
									synchronized (msg_list) {
										for (IOMessage msg : msg_list.values())
											if (msg.getType() == MsgType.Login_Req) {
												msg_list.remove(msg.getTag());
												break;
											}

										for (IOMessage msg : msg_list.values())
											if (msg.getType() == MsgType.LogoffReq) {
												msg_list.remove(msg.getTag());
												break;
											}
										msg_list.notifyAll();
									}// synchronized

									pref.edit().putString("TripsPending", "0").putString("TripsIRTPU", "0").putString("TripsIRTDO", "0").commit();



									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.receivedLoginResponse(columns);

									break;
								case MsgType.LogoffResp :
									networkServiceBinder.sendACK(msgRcvd);

									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									columns = columns[1].split("\\" + Character.toString(Constants.COLSEPARATOR));

									if ((columns[0].equalsIgnoreCase("1") && loggedIn && isLogOffRespPending) || (columns[0].equalsIgnoreCase("1") && loggedIn && columns.length >= 3)) {
										isLogOffRespPending = false;
										// networkServiceBinder.setloggedIn(false);
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedLogoffResponse(columns);
									}
									break;
								case MsgType.BookInResponse :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedBookinResponse(packetRcvd);
									break;
								case MsgType.NoShowResp :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedNoShowResponse(packetRcvd);
									break;
								case MsgType.BID_Offer :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedBidUpdate(packetRcvd);
									break;
								case MsgType.TripDetails :
									networkServiceBinder.sendACK(msgRcvd);
									if (!loggedIn) {
										synchronized (reciver_Runnable) {
											reciver_Runnable.wait(5000);
										}
									}
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedTripDetails(packetRcvd);
									break;
								case MsgType.SDTRIPOFFER :
									networkServiceBinder.sendACK(msgRcvd);
									if (!loggedIn) {
										synchronized (reciver_Runnable) {
											reciver_Runnable.wait(5000);
										}
									}
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedTripOffer(packetRcvd);
									break;
								case MsgType.FLUSH_BID :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedFlushBid(packetRcvd);
									break;
								case MsgType.ClearTrip :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedClearTrip(packetRcvd);
									break;
								case MsgType.SDTRIPFARE :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedSDTripFare(packetRcvd);
									break;
								case MsgType.ORSDLOGGEDOUT :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedForcedLogout(columns[1]);
									break;
								case MsgType.AVLResp :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedAVLResp(packetRcvd);
									break;

								case MsgType.PaymentResponse :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedPaymentResp(packetRcvd);
									break;

								case MsgType.Manifest :
									networkServiceBinder.sendACK(msgRcvd);
									if (!loggedIn) {
										synchronized (reciver_Runnable) {
											reciver_Runnable.wait(3000);
										}
									}
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedManifest(packetRcvd);
									break;

								case MsgType.TripDetailUpdate :
									networkServiceBinder.sendACK(msgRcvd);
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedTripUpdate(packetRcvd);
									break;
								case MsgType.PopUpMessage :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									columns = columns[1].split("\\" + Character.toString(Constants.COLSEPARATOR));
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											if (columns.length == 2)
												list.receivedPopupMsg(columns[0], columns[1]);
											else
												list.receivedPopupMsg(columns[0], "P");
									break;
								case MsgType.TextMessage :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									if (loggedIn)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedTextMsg(columns[1], "Text From Dispatcher");
									break;
								case MsgType.newAppOnServer :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.receivedAppUpdate(columns[1]);
									break;
								case MsgType.EstimatedFareResp :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.receivedEstimatedFareResp(columns[1]);
									break;
								case MsgType.SDBREAKENDED :
									networkServiceBinder.sendACK(msgRcvd);
									columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
									for (IMessageListener list : AVL_Service.msg_listeners.values())
										list.receivedSDBreakEnded(columns[1]);
									break;
								case MsgType.SDINACTIVITYREQUEST :
									if (loggedIn && (deviceID.equalsIgnoreCase(msgRcvd.getDestID()))) {
										networkServiceBinder.sendACK(msgRcvd);
										columns = packetRcvd.split(Character.toString(Constants.BODYSEPARATOR));
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedSDInactiveRequest(columns[1]);
									}
									break;
							}// switch

						}// if
						if ((Integer.valueOf(msgRcvd.getAckType()) == AckType.SACK) && (msgRcvd.getType() != MsgType.Login_Req)) {
							synchronized (msg_list) {
								if (msg_list.containsKey(msgRcvd.getTag())) {
									if (msgRcvd.getType() == MsgType.SDINACTIVITYRESPONSE && msg_list.get(msgRcvd.getTag()).getBody().contains("n"))
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.SD_BookOut();

									if (msgRcvd.getType() == MsgType.Emergency)
										for (IMessageListener list : AVL_Service.msg_listeners.values())
											list.receivedEmergencyConfirmation();

									msg_list.remove(msgRcvd.getTag());
								}

								msg_list.notifyAll();
							}// synchronized
						}// if AckType.SACK

					}// if Packet not NULL

				} catch (Exception e) {
					if (!(e instanceof InterruptedException))
						for (IMessageListener list : AVL_Service.msg_listeners.values())
							list.exception(e.getStackTrace()[0].getFileName() + "| "
									+ e.getLocalizedMessage()
									+ " in "
									+ e.getStackTrace()[0].getMethodName()
									+ " Line "
									+ e.getStackTrace()[0].getLineNumber());
				}

			}// while(true)
		} // run
	}; // msg_reciver_Runnable

    static Socket s;
    static BufferedWriter out;
    private static final Runnable softMeter_Runnable = new Runnable() {

        public void run() {
            ServerSocket ss = null;
            final int TCP_SERVER_PORT = 21111;


            try {
                ss = new ServerSocket(TCP_SERVER_PORT);
                //ss.setSoTimeout(10000);
                //accept connections
                s = ss.accept();
                InputStream is = s.getInputStream();
                out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                out.write("OPEN" +  System.getProperty("line.separator"));
                out.flush();


            while (!Thread.interrupted()) {
                String incomingMsg = null;
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                 out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                incomingMsg = in.readLine();
                if(incomingMsg != null){
                    for (IMessageListener list : AVL_Service.msg_listeners.values())
                        list.receivedSoftMeterMessage(incomingMsg);

                } else {
                     s = ss.accept();
                     is = s.getInputStream();
                    out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    out.write("OPEN" +  System.getProperty("line.separator"));
                    out.flush();
                }

            }// while
                s.close();
            } catch (Exception e) {
                //if timeout occurs
                for (IMessageListener list : AVL_Service.msg_listeners.values())
                    list.exception(e.getStackTrace()[0].getFileName() + "| "
                            + e.getLocalizedMessage()
                            + " in "
                            + e.getStackTrace()[0].getMethodName()
                            + " Line "
                            + e.getStackTrace()[0].getLineNumber());
            }
        }
    }; // network_Runnable
	/*--------------------------------------------------------------isBetterLocation---------------------------------------------------------------*/
	/**
	 * Determines whether one Location reading is better than the current Location fix
	 * 
	 * @param newLocation The new Location that you want to evaluate
	 * @param lastBestLocation The current Location fix, to which you want to compare the new one
	 */
	protected boolean isBetterLocation(Location newLocation, Location lastBestLocation) {
		if (lastBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - lastBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - lastBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(), lastBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/*--------------------------------------------------------------isSameProvider---------------------------------------------------------------*/
	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	/*--------------------------------------------------------------Location Listener---------------------------------------------------------------*/
    LocationListener locationListener = new LocationListener() {

        public void onStatusChanged(String provider, int status, Bundle extras) {

            // called when the provider status changes. Possible status: OUT_OF_SERVICE,
            // TEMPORARILY_UNAVAILABLE or AVAILABLE.
            if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER))
                if (status == LocationProvider.AVAILABLE || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
                    if (!TaxiPlexer.is_GPS_AVAILABLE)
                        for (IMessageListener list : AVL_Service.msg_listeners.values())
                            list.exception("[GPS Accessible][onStatusChanged]");
                    TaxiPlexer.is_GPS_AVAILABLE = true;

                } else if (status == LocationProvider.OUT_OF_SERVICE) {

                    if (TaxiPlexer.is_GPS_AVAILABLE) {
                        for (IMessageListener list : AVL_Service.msg_listeners.values())
                            list.exception("[GPS Not Accessible][onStatusChanged]");
                    }
                    TaxiPlexer.is_GPS_AVAILABLE = false;

                    if (!address.contains("FAIL") && !address.contains("OFF")) {
                        address = "GPS FAIL - " + address;
                        // pref.edit().putString("Address", address).commit();
                    }

                    // isNetworkReachable = false;
                }

            Log.w(getClass().getSimpleName(), "Location onStatusChanged - Provider: " + provider + " Status: " + status);
        }

        public void onProviderEnabled(String provider) {

            if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER))
                TaxiPlexer.SHOW_GPS_PAGE = false;

            for (IMessageListener list : AVL_Service.msg_listeners.values())
                list.exception("[GPS ON][onProviderEnabled]");
        }

        public void onProviderDisabled(String provider) {

            // called when the provider is disabled by the user, if it's already disabled, it's called
            // immediately after requestLocationUpdates
            // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            // startActivityForResult(intent, Constants.GPS_REQUEST_CODE);

            if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER))
                TaxiPlexer.SHOW_GPS_PAGE = true;

            if (!address.contains("FAIL") && !address.contains("OFF")) {
                address = " GPS OFF - " + address;
                // pref.edit().putString("Address", address).commit();

                for (IMessageListener list : AVL_Service.msg_listeners.values())
                    list.exception("[GPS OFF][onProviderDisabled]");
                // isNetworkReachable = false;

            }
        }

        public void onLocationChanged(Location currentLocation) {

            if (currentLocation.getProvider().equalsIgnoreCase(LocationManager.GPS_PROVIDER))
                TaxiPlexer.is_GPS_AVAILABLE = true;
            else
                TaxiPlexer.is_GPS_AVAILABLE = false;

            if (lastLocation == null) {
                lastLocation = currentLocation;
                if (isNetworkReachable) {
                    msgHandler.obtainMessage(MsgType.ReverseGeoCode, Float.MAX_VALUE).sendToTarget();
                }// if network connected
            } else if (isBetterLocation(currentLocation, lastLocation)) {

                LocationAge = System.currentTimeMillis();
                pref.edit().putString("LastLatitude", Double.toString(currentLocation.getLatitude())).putString("LastLongitude", Double.toString(currentLocation.getLongitude())).putString(
                        "Accuracy",
                        StringUtil.truncate(decimalFormat.format(currentLocation.getAccuracy()), 4)).putString("Alt", "1").putString("Speed", decimalFormat.format(currentLocation.getSpeed() * 2.24)) // to
                        // miles
                        // per
                        // hour
                        .commit();

                current_Direction = currentLocation.getBearing();
                if (currentLocation.getBearing() < 15)
                    pref.edit().putString("Direction", "North").commit();
                else if (currentLocation.getBearing() < 55)
                    pref.edit().putString("Direction", "NE").commit();
                else if (currentLocation.getBearing() < 105)
                    pref.edit().putString("Direction", "East").commit();
                else if (currentLocation.getBearing() < 145)
                    pref.edit().putString("Direction", "SE").commit();
                else if (currentLocation.getBearing() < 195)
                    pref.edit().putString("Direction", "South").commit();
                else if (currentLocation.getBearing() < 235)
                    pref.edit().putString("Direction", "SW").commit();
                else if (currentLocation.getBearing() < 285)
                    pref.edit().putString("Direction", "West").commit();
                else if (currentLocation.getBearing() < 315)
                    pref.edit().putString("Direction", "NW").commit();
                else if (currentLocation.getBearing() < 360)
                    pref.edit().putString("Direction", "North").commit();

                if (currentLocation.distanceTo(lastLocation) > (pref.getFloat(Constants.PREF_GPSAt, 600) / 2)) {
                    float dist = currentLocation.distanceTo(lastLocation);
                    lastLocation = currentLocation;
                    if (isNetworkReachable) {
                        // reset the timer based AVL reporting
                        msgHandler.obtainMessage(MsgType.ReverseGeoCode, dist).sendToTarget();
                    }
                } else {
                    lastLocation = currentLocation;
                    if (loggedIn)
                        for (IMessageListener list : AVL_Service.msg_listeners.values())
                            list.receivedLocationChange(address);
                }

            }// if isBetterLocation
        } // onLocationChanged

    }; // LocationListener




     public static void sendMessageToSoftMeter(String msgBody, int msgType, String VehicleNum, String deviceID) {
        String msgTag = getDateTime();

            String header = msgType + "^" + msgTag + "^" + deviceID + "^" + VehicleNum ;

            String body = String.valueOf(msgBody);
            String msgToSend = header + Constants.BODYSEPARATOR + body + Constants.EOT;

        try {
            out.write(msgToSend +  System.getProperty("line.separator"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }// sendMessageToSoftMeter

	/*----------------------------------------------------------------getSignalStrength--------------------------------------------------------------------------------*/
	private String getSignalStrength() {

		return signalStrength == null ? "0" : signalStrength;
	}

	/*----------------------------------------------------------------getBatteryLevel--------------------------------------------------------------------------------*/
	private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {

			int rawlevel = intent.getIntExtra("level", -1);
			int scale = intent.getIntExtra("scale", -1);
			int level = -1; // percentage, or -1 for unknown
			if (rawlevel >= 0 && scale > 0)
				level = (rawlevel * 100) / scale;

			pref.edit().putInt("B-Level", level).commit();
		}
	};

	private String getBatteryLevel() {

		return Integer.toString(pref.getInt("B-Level", 0));
	}

	/*---------------------------------------------------------------onBind--------------------------------------------------------------------------------*/
	@Override
	public IBinder onBind(Intent arg0) {

		synchronized (decimalFormat) {
			try {
				decimalFormat.wait(5000);
			} catch (InterruptedException e) {

			}
		}
		return networkServiceBinder;
	}// onBind

	/*----------------------------------------------------------------onStart--------------------------------------------------------------------------------*/
	@Override
	public void onStart(Intent intent, int flags) {
		deviceID = getDeviceID();
		new Thread() {
			@Override
			public void run() {
				this.setName("onServiceStart");
				if (connectToServer(pref.getString("serverip", server_list.get(pInfo.packageName.split("\\.")[1])))) {
					if (msgReciverThread == null)
						msgReciverThread = new Thread(null, reciver_Runnable, "msg_reciver_thread");
					if (msgSenderThread == null)
						msgSenderThread = new Thread(null, sender_Runnable, "msg_sender_thread");
					if (!msgReciverThread.isAlive())
						msgReciverThread.start();
					if (!msgSenderThread.isAlive())
						msgSenderThread.start();

					synchronized (decimalFormat) {
						decimalFormat.notifyAll();
					}
				} else if (connectToServer("127.0.0.1")) {
					if (msgReciverThread == null)
						msgReciverThread = new Thread(null, reciver_Runnable, "msg_reciver_thread");
					if (msgSenderThread == null)
						msgSenderThread = new Thread(null, sender_Runnable, "msg_sender_thread");
					if (!msgReciverThread.isAlive())
						msgReciverThread.start();
					if (!msgSenderThread.isAlive())
						msgSenderThread.start();

					synchronized (decimalFormat) {
						decimalFormat.notifyAll();
					}
				}
			}// run

		}.start();
	}// onStart


    public static void softmeterthreadstart(){
        if(SoftMeterThread == null)
            SoftMeterThread = new Thread(null, softMeter_Runnable, "soft_meter_thread");
        if (!SoftMeterThread.isAlive())
            SoftMeterThread.start();
    }

	/*----------------------------------------------------------------onDestroy-----------------------------------------------------------------------------*/
	@Override
	public void onDestroy() {

		msgHandler.removeMessages(MsgType.HandShake);
		msgHandler = null;
		synchronized (msg_list) {
			msg_list.clear();
			msg_list.notifyAll();
		}

		msgReciverThread.interrupt();
		msgSenderThread.interrupt();
		super.onDestroy();
	}

	/*----------------------------------------------------------------getDateTime---------------------------------------------------------------------------*/
	private static String getDateTime() {

		Date date = Calendar.getInstance(Locale.US).getTime();
		return dateFormat.format(date);
	}

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /************************************************************************************************************************
	 ******************************************** CLASS NETWORK BINDER ****************************************************
	 ************************************************************************************************************************/

	private class NetworkServiceBinder extends Binder implements IAVL_Service {

		/*---------------------------------------------------------------addMessageListener--------------------------------------------------------------------*/
		@Override
		public void addMessageListener(IMessageListener listener) {

			if (!msg_listeners.containsKey(listener.getName())) {
				msg_listeners.put(listener.getName(), listener);
				Log.w(getClass().getSimpleName(), "Added new Message Listener.  Count: " + msg_listeners.size());
			} else {
				msg_listeners.remove(listener.getName()); // Updated

				msg_listeners.put(listener.getName(), listener);

				Log.w(getClass().getSimpleName(), "Message Listener already exists.  Count: " + msg_listeners.size());

			}
		}

		/*--------------------------------------------------------------updateServerAddress--------------------------------------------------------------------------*/
		@Override
		public boolean updateServerAddress(String address) {

			try {
                InetAddress[] InetArray = InetAddress.getAllByName(address);// server address;
                serverAddress1 = address;
				if (InetArray.length > 0)
					clearMsgQueues();
				else {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.invalidServerIP("Invalid Server IP");
					return false;
				}
				if (!loggedIn) {
					boolean check = false;
					serverAddress = InetArray[0];
					pref.edit().putString("serverip", address.trim()).commit();
					sendMessageToServer(AVL_Service.appVersion, "SDHS", MsgType.HandShake, AckType.NEW, MsgValidity.Short, MsgPriority.AboveNormal);
					// hamza
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						// list.invalidServerIP("Invalid Server IP");
						list.CheckButtonState(check);

				} else if (!pref.getString("serverip", server_list.get(pInfo.packageName.split("\\.")[1])).equalsIgnoreCase(address.trim())) {

					sendMessageToServer(
						pref.getString("LastLatitude", "0") + Constants.COLSEPARATOR + pref.getString("LastLongitude", "0"),
						"SDHS",
						MsgType.LogoffReq,
						AckType.NEW,
						MsgValidity.Medium,//
						MsgPriority.AboveNormal);

					serverAddress = InetArray[0];
					pref.edit().putString("serverip", address.trim()).commit();

					sendMessageToServer(AVL_Service.appVersion, "SDHS", MsgType.HandShake, AckType.NEW, MsgValidity.Short, MsgPriority.AboveNormal);
				}
				return true;

			} catch (UnknownHostException e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.invalidServerIP("Not a Valid Address");
				return false;

			} catch (Exception e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.invalidServerIP(e.getLocalizedMessage());
				return false;
			}
		}

		/*---------------------------------------------------------------loggedIn-------------------------------------------------------------------------*/
		@Override
		public boolean loggedIn() {

			return loggedIn;
		}

		/*---------------------------------------------------------------setloggedIn-------------------------------------------------------------------------*/
		@Override
		public void setloggedIn(boolean state) {

			loggedIn = state;
			synchronized (reciver_Runnable) {
				reciver_Runnable.notify();
			}

		}

		/*---------------------------------------------------------------removeMessageListener------------------------------------------------------------------*/
		@Override
		public void removeMessageListener(IMessageListener listener) {

			if (msg_listeners.containsKey(listener.getName()) == true) {
				msg_listeners.remove(listener.getName());

				Log.w(getClass().getSimpleName(), "Removed IMessageListener.  Count: " + msg_listeners.size());
			} else {
				Log.w(getClass().getSimpleName(), "IMessageListener is not a listener.  Count: " + msg_listeners.size());
			}
		}

		/*---------------------------------------------------------------sendMessageToServer----------------------------------------------------------------------*/
		@Override
		public void sendMessageToServer(String msgBody, String Dest, int msgType, int ackType, int validity, int priority) {
			char[] parialBody;
			int isMsgPreceding;
			String msgTag = getDateTime();
			msgID = (System.currentTimeMillis() / 1000);

			do {
				parialBody = "".toCharArray();// msgBody.toCharArray();

				if ((Constants.MAXHEADERLENGTH + msgBody.length()) > Constants.MAXSENDBUFFERSIZE) {
					parialBody = new char[Constants.MAXSENDBUFFERSIZE - Constants.MAXHEADERLENGTH];
					msgBody.getChars(0, Constants.MAXSENDBUFFERSIZE - Constants.MAXHEADERLENGTH - 1, parialBody, 0);
					msgBody = msgBody.substring(parialBody.length);
					isMsgPreceding = 1;
				} else {
					parialBody = msgBody.toCharArray();
					msgBody = "";
					isMsgPreceding = 0;
				}

				String header = msgType + "^" + msgTag + "^" + deviceID + "^" + Dest + "^" + msgID + "^" + ackType + "^" + validity + "^" + priority + "^" + isMsgPreceding + "^" + parialBody.length;

				String body = String.valueOf(parialBody);
				String msgToSend = header + Constants.BODYSEPARATOR + body + Constants.EOT;

				byte[] bytesToSend = new byte[msgToSend.length()];
				bytesToSend = msgToSend.getBytes();

				DatagramPacket packetToSend = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, servPort);
				IOMessage outMsg = new IOMessage(packetToSend, msgTag, msgID, msgType, body);
				outMsg.setSendTime(System.currentTimeMillis() - 16000);

				synchronized (msg_list) {
					for (IOMessage msg : msg_list.values()) {
						if (msg.getType() == MsgType.HandShake || msg.getType() == MsgType.Login_Req || msg.getType() == MsgType.ZFTReq || msg.getType() == MsgType.BookinReq) {
							msg_list.remove(msg.getTag());
							break;
						}
					}
					msg_list.put(msgTag, outMsg);
					msg_list.notifyAll();
				}
			} while (msgBody.length() > 0);

		}// sendMessageToServer

		/*----------------------------------------------------------------sendAVLData--------------------------------------------------------------------------------*/
		@Override
		public void sendAVLData() {
			try {

				new Thread(new Runnable() {

					@Override
					public void run() {
						if (reverseGeocodeFromMRMSService)
							if (address.contains("Unknown") || !IsAddressValid)
								if (lastLocation != null) {
									StringBuffer envelope = new StringBuffer(
										"<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ReverseGeoCodeBylatlng xmlns=\"http://Itcurves.net/\"><lat>")
										.append(String.valueOf(lastLocation.getLatitude()))
										.append("</lat><lng>")
										.append(lastLocation.getLongitude())
										.append("</lng></ReverseGeoCodeBylatlng></soap:Body></soap:Envelope>");

									// Calling Web Service and Parsing Response
									WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_ReverseGeoCodeBylatlng, envelope.toString());

									if (wsResponse != null)
										if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("ReverseGeoCodeBylatlngResult")) {

											address = wsResponse.addressString;
											pref.edit().putString("Address", address).commit();

										} else
											for (IMessageListener list1 : AVL_Service.msg_listeners.values())
												list1.exception("sendAVLData() Failed. " + wsResponse.errorString);
									else
										for (IMessageListener list1 : AVL_Service.msg_listeners.values())
											list1.exception("sendAVLData() Failed");
								}
						//@formatter:off
						String avlStr = pref.getString("LastLatitude", "0") + Constants.COLSEPARATOR
								+ pref.getString("LastLongitude", "0") + Constants.COLSEPARATOR
								+ pref.getString("Accuracy", "0.0")	+ Constants.COLSEPARATOR
								+ pref.getString("Alt", "1") + Constants.COLSEPARATOR
								+ pref.getString("Speed", "0.0") + Constants.COLSEPARATOR
								+ pref.getString("Direction", "0.0") + Constants.COLSEPARATOR
								+ address + Constants.COLSEPARATOR
								+ getSignalStrength() + Constants.COLSEPARATOR
								+ getBatteryLevel() + Constants.COLSEPARATOR
								+ pref.getString("TripsPending", "0") + Constants.COLSEPARATOR
								+ pref.getString("TripsIRTPU", "0") + Constants.COLSEPARATOR
								+ pref.getString("TripsIRTDO", "0") + Constants.COLSEPARATOR
								+ TaxiPlexer.isAppActive;
						//@formatter:on
						sendMessageToServer(avlStr, "SDHS", MsgType.AVL, AckType.NEW, MsgValidity.Short, MsgPriority.BelowNormal);
						TaxiPlexer.isAppActive = false;
					}
				}).start();

				if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.receivedTurnONGPS();

			}

			catch (Exception e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception(e.getClass() + "| sendAVLData(): " + e.getLocalizedMessage());
			}
		}

		/*----------------------------------------------------------------sendHeartBeat--------------------------------------------------------------------------------*/
		public void sendHeartBeat() {
			try {
				String heartBeatBody = pref.getString("LastLatitude", "0") + Constants.COLSEPARATOR
						+ pref.getString("LastLongitude", "0")
						+ Constants.COLSEPARATOR
						+ address
						+ Constants.COLSEPARATOR
						+ String.valueOf(loggedIn);

				sendMessageToServer(heartBeatBody, "SDHS", MsgType.SDHEARTBEAT, AckType.NEW, MsgValidity.Short, MsgPriority.BelowNormal);
				if (!AVL_Service.pref.getBoolean("VeriFoneDevice", false) && TaxiPlexer._isLoggedInOnce) {
					CountToCheckMeterStatus = CountToCheckMeterStatus + 1;
					if (CountToCheckMeterStatus == 5) {
						CountToCheckMeterStatus = 0;
						for (IMessageListener list : AVL_Service.msg_listeners.values())
							list.receivedMeterStatus();
					}
				}
				if (loggedIn) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.receivedHeartBeatChange();

					if (isNetworkConnected()) {
						for (IMessageListener list : AVL_Service.msg_listeners.values())
							list.receivedSetNetworkStatus(true);
					} else {
						for (IMessageListener list : AVL_Service.msg_listeners.values())
							list.receivedSetNetworkStatus(false);
					}

					if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
						for (IMessageListener list : AVL_Service.msg_listeners.values())
							list.receivedSetGPSstatus(true);
					} else {
						for (IMessageListener list : AVL_Service.msg_listeners.values())
							list.receivedSetGPSstatus(false);
					}

				}

			} catch (Exception e) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception(e.getClass() + "| sendHeartBeat(): " + e.getLocalizedMessage());
			}

		}

		/*---------------------------------------------------------------Send ACK----------------------------------------------------------------------*/
		public void sendACK(IOMessage msgRcvd) throws IOException {

			// Dest = msgRcvd.getSourceID();
			String header = msgRcvd.getType() + "^"
					+ msgRcvd.getTag()
					+ "^"
					+ deviceID
					+ "^"
					+ msgRcvd.getSourceID()
					+ "^"
					+ msgRcvd.getCounter()
					+ "^"
					+ AckType.DACK
					+ "^"
					+ msgRcvd.getValidity()
					+ "^"
					+ msgRcvd.getPriority()
					+ "^"
					+ msgRcvd.isMsgProceeding()
					+ "^"
					+ 0;
			String msgToSend;
			if (msgRcvd.getType() == MsgType.Manifest)// if msg is Manifest Msg
				msgToSend = header + Constants.BODYSEPARATOR + msgRcvd.getBody().split("\\~", 2)[0] + Constants.EOT;
			else if (msgRcvd.getType() == MsgType.BID_Offer)
				msgToSend = header + Constants.BODYSEPARATOR + msgRcvd.getBody().split("\\^")[0] + Constants.EOT;
			else
				msgToSend = header + Constants.BODYSEPARATOR + "DACK" + Constants.EOT;
			Log.w("ACK sent", msgToSend);
			byte[] bytesToSend = new byte[msgToSend.length()];
			bytesToSend = msgToSend.getBytes();

			DatagramPacket packetToSend = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, servPort);
			synchronized (socket) {
				socket.send(packetToSend);
				socket.notifyAll();
			}

		}// sendACK

		/*---------------------------------------------------------------clearMsgQueues----------------------------------------------------------------------*/
		@Override
		public void clearMsgQueues() {
			msgHandler.removeMessages(MsgType.HandShake);
			synchronized (msg_list) {
				msg_list.clear();
				msg_list.notifyAll();
			}
		}

	}// Class NetworkServiceBinder

	/************************************************************************************************************************
	 ********************************************* CLASS MessageReader ****************************************************
	 ************************************************************************************************************************/

	protected static class MessageReader {

		// InputStream myInputStream;

		int messageId;
		static int bytesRead;

		static String messageRecieved;

		static String[] msgArray;
		static String rcvdString;

		protected static Map<String, String> msg_tag = new HashMap<String, String>(); // ----------------------UPDATED------------------------

		public static String getMessage(DatagramPacket packet) throws IOException {

			bytesRead = packet.getLength();
			byte[] networkMessageBytes = new byte[bytesRead];

			System.arraycopy(packet.getData(), 0, networkMessageBytes, 0, bytesRead);

			messageRecieved = "";
			if (partialStr == null)
				partialStr = "";

			rcvdString = new String(networkMessageBytes);
			msgArray = rcvdString.split(Character.toString(Constants.EOT)); // remove EOT at the End

			IOMessage msgRcvd = new IOMessage(msgArray[0]);

			if (Integer.valueOf(msgRcvd.getAckType()) == AckType.NEW) {
				if (msgRcvd.isMsgProceeding().equalsIgnoreCase("0")) {
					if (msg_tag.containsKey(msgRcvd.getTag())) { // ----------------------UPDATED------------------------
						messageRecieved = msgRcvd.getHeader() + Character.toString(Constants.BODYSEPARATOR) + msg_tag.get(msgRcvd.getTag()) + msgRcvd.getBody();
						msg_tag.remove(msgRcvd.getTag());// optional
						msg_tag.put(msgRcvd.getTag(), messageRecieved);// optional
					} else
						messageRecieved = msgRcvd.getHeader() + Character.toString(Constants.BODYSEPARATOR) + msgRcvd.getBody();
					// ----------------------UPDATED------------------------
					partialStr = "";
					msgArray = null;
					Log.w(MsgType.getDesc(msgRcvd.getType()) + " Received: ", messageRecieved); // /////////////////////////////////////////////////////////////////////////////////
				} else if (msgRcvd.isMsgProceeding().equalsIgnoreCase("1")) {
					partialStr = partialStr + msgRcvd.getBody();
					msg_tag.remove(msgRcvd.getTag()); // ----------------------UPDATED------------------------
					msg_tag.put(msgRcvd.getTag(), partialStr); // ----------------------UPDATED------------------------
					Log.w("Partial Data received: ", msgRcvd.getBody());
					messageRecieved = null;
				}
			} else {
				messageRecieved = msgRcvd.getHeader() + Character.toString(Constants.BODYSEPARATOR) + partialStr + msgRcvd.getBody();
				msgArray = null;
				Log.w("ACK Received: ", messageRecieved);
			}
			rcvdString = null;
			return messageRecieved;

		} // getNextMessage

	} // Message Reader Class

	/*-------------------------------------------------------------fetchBlockedAppsList---------------------------------------------------------------*/
	protected void fetchBlockedAppsList() {
		try {

			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching Apps List");

			pMng = getApplicationContext().getPackageManager();
			List<ApplicationInfo> list = pMng.getInstalledApplications(0);
			for (int k = 0; k < list.size(); k++) {
				deviceApps.add(list.get(k).processName);
				deviceApps.add("~");
			}

			listString = "";

			for (String s : deviceApps) {
				listString += s;
			}

			String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><getSmartDeviceApplications xmlns=\"http://Itcurves.net/\"><DeviceID>" + deviceID
					+ "</DeviceID><DeviceApplications>"
					+ listString
					+ "</DeviceApplications></getSmartDeviceApplications></soap:Body></soap:Envelope>";

			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_killApps, envelope);

			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("getSmartDeviceApplicationsResult")) {
					restrictedApps = wsResponse.killAppsResp;
					msgHandler.obtainMessage(MsgType.kill).sendToTarget();
				} else
					for (IMessageListener list1 : AVL_Service.msg_listeners.values())
						list1.exception("fetchAppsList() Failed. " + wsResponse.errorString);
			else
				for (IMessageListener list1 : AVL_Service.msg_listeners.values())
					list1.exception("fetchAppsList() Failed");

			for (IMessageListener list2 : AVL_Service.msg_listeners.values())
				list2.hideProgressDialog();

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
		}
	}

	/*-------------------------------------------------------------fetchDriverTakenBreakStats-------------------------------------------------*/
	protected boolean fetchDriverTakenBreakStats() {
		boolean status = false;
		try {
			sdTotalBreaksTaken = -1;
			String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetLiveMiscInfo xmlns=\"http://Itcurves.net/\"><ProcID>1</ProcID><DriverID>" + AVL_Service.pref
				.getString("DriverID", "Unknown")
					+ "</DriverID></GetLiveMiscInfo></soap:Body></soap:Envelope>";

			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_DriverTakenBreakStats, envelope);

			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("GetLiveMiscInfoResult")) {
					// sdTotalBreaksTaken = wsResponse.DriverTakenBreaks;
					sdTotalBreaksTaken = Integer.valueOf(wsResponse.DriverTakenBreaks);
					status = true;
				}
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
		}
		return status;
	}

	/*-------------------------------------------------------------fetchCannedMsgsList---------------------------------------------------------------*/
	protected void fetchCannedMsgsList() {
		try {

			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching Canned Messages List");

			String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetCannedMessages xmlns=\"http://Itcurves.net/\" /></soap:Body></soap:Envelope>";

			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_cannedMsgs, envelope);

			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("GetCannedMessagesResult")) {

					cmessages_Array = wsResponse.cannedMsgsList.toArray(new String[wsResponse.cannedMsgsList.size()]);

				} else
					for (IMessageListener list1 : AVL_Service.msg_listeners.values())
						list1.exception("fetchCannedMsgsList() Failed. " + wsResponse.errorString);
			else
				for (IMessageListener list1 : AVL_Service.msg_listeners.values())
					list1.exception("fetchCannedMsgsList() Failed");

			for (IMessageListener list2 : AVL_Service.msg_listeners.values())
				list2.hideProgressDialog();

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
		}
	}

	/*--------------------------------------------------------------fetchGetSdZoneList----------------------------------------------------------------*/
	protected void fetchGetSdZoneList() {
		try {

			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching SDZoneList");

			String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetSDZoneList  xmlns=\"http://Itcurves.net/\" /></soap:Body></soap:Envelope>";
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_GetSdZoneList, envelope);
			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("GetSDZoneListResult")) {

					// String value = (new ArrayList<String>(wsResponse.map_zonelist.values())).get(1);
					// Map temp = new HashMap(wsResponse.map_zonelist);
					tempMap = new HashMap<String, String>(wsResponse.map_zonelist);
					tempMap.putAll(wsResponse.map_zonelist);
				}

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
		}
	}

    public static void fetchClassofServiceRates() {
        try {

            for (IMessageListener list1 : AVL_Service.msg_listeners.values())
                list1.showProgressDialog("Fetching Class Of Service Rates");

            String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetClassOfServiceRates  xmlns=\"http://Itcurves.net/\"><vVehicleNo>" + AVL_Service.pref
                    .getString("VehicleID", "Unknown")
                    + "</vVehicleNo></GetClassOfServiceRates ></soap:Body></soap:Envelope>";
            WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_ClassofService, envelope);
            if (wsResponse != null)
                if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("ClassOfServiceRates")) {
                    softMeterRates = wsResponse.softmeterrates;
                }

        } catch (Exception e) {
            for (IMessageListener list : AVL_Service.msg_listeners.values())
                list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
        }
    }

	/*-------------------------------------------------------------fetchGeneralSettings---------------------------------------------------------------*/
	protected boolean fetchGeneralSettings() {

		boolean status = false;

		try {
			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching General Settings");

			String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><SDGetGeneralSettings xmlns=\"http://Itcurves.net/\" /></soap:Body></soap:Envelope>";

			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_SDGetGeneralSettings, envelope);

			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("SDGetGeneralSettingsResult")) {
					btMeterAvailable = wsResponse.generalSettings.get_AllowBTMeterOnSDStartUp();
					centrodyneMeterAvailable = wsResponse.generalSettings.get_AllowCentrodyneMeterOnSDStartUp();
					vivotechAvailable = wsResponse.generalSettings.get_AllowCMTVivotechOnSDStartUp();
					blueBambooAvailable = wsResponse.generalSettings.get_AllowBlueBamboOnSDStartUp();
					enableAudioCommands = wsResponse.generalSettings.get_AllowAudioCommandsOnSDStartUp();
					showEstMiles = wsResponse.generalSettings.get_ShowEstMilesOnDevice();
					googleMapAPIKey = wsResponse.generalSettings.get_GoogleMapAPIKey();
					callOutRequestPrompt = wsResponse.generalSettings.get_SDCallOutRequestPrompt();
					callOutRequestPrompt_ar = wsResponse.generalSettings.get_SDCallOutRequestPrompt_ar();
					sdEnableBreak = wsResponse.generalSettings.get_SDEnableBreak();
					sdEnableCalcEstOnDropped = wsResponse.generalSettings.get_SDEnableCalcEstOnDropped();
					sdCalcEstViaMRMSService = wsResponse.generalSettings.get_SDCalcEstViaMRMSService();
					tipVoiceEnabled = wsResponse.generalSettings.get_TipVoiceEnabled();
					hideCostOnSDByFundingSource = wsResponse.generalSettings.get_HideCostOnSDByFundingSource();
					reverseGeocodeFromMRMSService = wsResponse.generalSettings.get_ReverseGeocodeFromMRMSService();
					showManifestWallOnSD = wsResponse.generalSettings.get_ShowManifestWallOnSD();
					sendBidOffers = wsResponse.generalSettings.get_SendBidOffers();
					restrictSoftDropIfMeterConnected = wsResponse.generalSettings.get_RestrictSoftDropIfMeterConnected();
					allowedSpeedForMessaging = wsResponse.generalSettings.get_AllowedSpeedForMessaging();
					timerForCradleLogout = wsResponse.generalSettings.get_TimerForCradleLogout();
					allowableCallOutDistance = wsResponse.generalSettings.get_AllowableCallOutDistance();
					DeviceMessageScreenConfig = wsResponse.generalSettings.get_DeviceMessageScreenConfig().split("");
					sdMaxAllowedBreaksInOneDay = Integer.valueOf(wsResponse.generalSettings.get_SDMaxAllowedBreaksInOneDay());
					showReceiptPrintingDialog = wsResponse.generalSettings.get_ShowReceiptPrintingDialog();
					WallTripDistanceByGoogle = Integer.valueOf(wsResponse.generalSettings.get_WallTripDistanceByGoogle());
					enableDialiePackageOnDevice = wsResponse.generalSettings.get_EnableDialiePackageOnDevice();
					bShuttle = wsResponse.generalSettings.get_bShuttleAgentScreen();
					SDShowFlaggerConfirmation = wsResponse.generalSettings.get_SDShowFlaggerConfirmation();
					sDMaxLengthOfTripList = wsResponse.generalSettings.get_SDTripListSize();
					showEstdCostOnSDByFundingSource = wsResponse.generalSettings.get_ShowEstdCostOnSDByFundingSource();

					ShowSDDriverPhoto = wsResponse.generalSettings.get_ShowSDDriverPhoto();
					ShowSDAVLOnStatus = wsResponse.generalSettings.get_ShowSDAVLOnStatus();
					ShowSDStandRankOnStatus = wsResponse.generalSettings.get_ShowSDStandRankOnStatus();
					ShowSDTaxiMeterOnStatus = wsResponse.generalSettings.get_ShowSDTaxiMeterOnStatus();
					ShowSDBackSeatOnStatus = wsResponse.generalSettings.get_ShowSDBackSeatOnStatus();
					ShowSDApartmentOnTripDetail = wsResponse.generalSettings.get_ShowSDApartmentOnTripDetail();
					ShowSDFundingSourceOnTripDetail = wsResponse.generalSettings.get_ShowSDFundingSourceOnTripDetail();
					ShowSDPaymentTypeOnTripDetail = wsResponse.generalSettings.get_ShowSDPaymentTypeOnTripDetail();
					ShowSDCoPayOnTripDetail = wsResponse.generalSettings.get_ShowSDCoPayOnTripDetail();
					ShowSDOnlyFareOnPaymentScreen = wsResponse.generalSettings.get_ShowSDOnlyFareOnPaymentScreen();
					ShowStandsOnSD = wsResponse.generalSettings.get_ShowStandsOnSD();
					ShowNearZoneFeatureOnSD = wsResponse.generalSettings.get_ShowNearZoneFeatureOnSD();
					SDDefaultLanguageSelection = wsResponse.generalSettings.get_SDDefaultLanguageSelection();
					SDDropNavigationWithMap = wsResponse.generalSettings.get_SDDropNavigationWithMap();
					ShowClientPhoneNoOnReceipt = wsResponse.generalSettings.get_ShowClientPhoneNoOnReceipt();
					ResponseIDToRemoveTripFromWall = wsResponse.generalSettings.get_ResponseIDToRemoveTripFromWall().split(",");
					bArabClient = wsResponse.generalSettings.get_bArabClient();
					ShowTogglePickUpDropOffBTN = wsResponse.generalSettings.get_ShowTogglePickUpDropOffBTN();
					HEXColor = wsResponse.generalSettings.get_HEXColor();
					Allow_Book_In_AutoZone = wsResponse.generalSettings.get_Allow_Book_In_AZ();
					ShowHandShakeButtonOnLogin = wsResponse.generalSettings.get_ShowHandShakeButtonOnLogin();
					sdEnablePPT = wsResponse.generalSettings.get_SDEnablePPT();
					sdEnableEmergency = wsResponse.generalSettings.get_SDEnableEmergency();
					SDFlaggerButtonSize = wsResponse.generalSettings.get_SDFlaggerButtonSize();
					SendDriverPicToVeriFone = wsResponse.generalSettings.get_SendDriverPicToVeriFone();
					AllowDetailedLogInFileAndSQL = wsResponse.generalSettings.get_AllowDetailedLogInFileAndSQL();
					SDShowLanguageChangeOption = wsResponse.generalSettings.get_SDShowLanguageChangeOption();
					SDUnitOfDistance = wsResponse.generalSettings.get_SDUnitOfDistance();
					SDUnitOfCurrency = wsResponse.generalSettings.get_SDUnitOfCurrency();
					PPV_UsePPVModule = wsResponse.generalSettings.get_PPV_UsePPVModule();
					SDBreakEmergencyPPTPosition = wsResponse.generalSettings.get_SDBreakEmergencyPPTPosition();
					EnableAudioForMessageUtility = wsResponse.generalSettings.get_EnableAudioForMessageUtility();
					SDShowProceedToPickupOnTripOffer = wsResponse.generalSettings.get_SDShowProceedToPickupOnTripOffer();
					SDReprintTimeOutSec = wsResponse.generalSettings.get_SDReprintTimeOutSec();
					SDVFCashVoucherDialogTimeOutSec = wsResponse.generalSettings.get_SDVFCashVoucherDialogTimeOutSec();
					SDEnableVoiceIfNewTripAddedOnWall = wsResponse.generalSettings.get_SDEnableVoiceIfNewTripAddedOnWall();
					SDShowPassengerNameOnWall = wsResponse.generalSettings.get_SDShowPassengerNameOnWall();
					SDBreakActionOnSingleTap = wsResponse.generalSettings.get_SDBreakActionOnSingleTap();
					SDBreakColor = wsResponse.generalSettings.get_SDBreakColor();
					SDResumeColor = wsResponse.generalSettings.get_SDResumeColor();
					SDShowPUDateTimeOnTripDetail = wsResponse.generalSettings.get_SDShowPUDateTimeOnTripDetail();
					SDShowVoucherButton = wsResponse.generalSettings.get_SDShowVoucherButton();
					CreditCardFeature = wsResponse.generalSettings.get_CreditCardFeature();
					SDEnableTripListSynchronization = wsResponse.generalSettings.get_SDEnableTripListSynchronization();
					SDShowMileageOnStatusTab = wsResponse.generalSettings.get_SDShowMileageOnStatusTab();
					SDEnableSignatureFeature = wsResponse.generalSettings.get_SDEnableSignatureFeature();
					SDCentralizedAsteriskService = wsResponse.generalSettings.get_SDCentralizedAsteriskService();
					SDEnableCentralizedAsteriskService = wsResponse.generalSettings.get_SDEnableCentralizedAsteriskService();
					ASCS_HelpLine_Number = wsResponse.generalSettings.get_ASCS_HelpLine_Number();
					SDEmergencyConfirmation = wsResponse.generalSettings.get_SDEmergencyConfirmation();
					NotAllowActionIfAway = wsResponse.generalSettings.get_NotAllowActionIfAway();
					MessageTypeIfActionNotAllowed = wsResponse.generalSettings.get_MessageTypeIfActionNotAllowed();
					ShowAddressOnWall = wsResponse.generalSettings.get_ShowAddressOnWall();
					CompanyName_Receipt = wsResponse.generalSettings.get_CompanyName_Receipt();
					CompanyURL = wsResponse.generalSettings.get_CompanyURL();
					SDEnableManualFlagger = wsResponse.generalSettings.get_SDEnableManualFlagger();
					SDEnableTwoStepPaymentProcessing = wsResponse.generalSettings.get_SDEnableTwoStepPaymentProcessing();
					SDPaymentButtonCaptionFor2ndStep = wsResponse.generalSettings.get_SDPaymentButtonCaptionFor2ndStep();
					Allow_Promotion_In_MARS_SDApp_Both = wsResponse.generalSettings.get_Allow_Promotion_In_MARS_SDApp_Both();
					SDShowFontChangeOption = wsResponse.generalSettings.get_SDShowFontChangeOption();
					SDEnableMeterLocking = wsResponse.generalSettings.get_SDEnableMeterLocking();
					SDRingerCountForTripOffer = wsResponse.generalSettings.get__SDRingerCountForTripOffer();
					SDShowServiceID = wsResponse.generalSettings.get_SDShowServiceID();
					SDShowPhoneandIMEI = wsResponse.generalSettings.get_SDShowPhoneandIMEI();

                    SDAsteriskServer = wsResponse.generalSettings.get_SDAsteriskServer();
                    SDAsteriskHangUpTime = wsResponse.generalSettings.get_SDAsteriskHangUpTime();
                    SDAsteriskDispatcherExt = wsResponse.generalSettings.get_SDAsteriskDispatcherExt();
                    SDEnableAsteriskExtension = wsResponse.generalSettings.get_SDEnableAsteriskExtension();
                    MARS_HelpLine_Number = wsResponse.generalSettings.get_MARS_HelpLine_Number();
                    TSPID =  wsResponse.generalSettings.get_TSPID();
                    SIPExtPattern = wsResponse.generalSettings.get_SIPExtPattern();
                    SIPPwdPattern = wsResponse.generalSettings.get_SIPPwdPattern();
                    InLoadAPI_URL = wsResponse.generalSettings.get_InLoadAPI_URL();
                    SDOnlyNearZoneMode = wsResponse.generalSettings.get_SDOnlyNearZoneMode();
                    WallRefreshTimer = wsResponse.generalSettings.get_WallRefreshTimer();
                    SDEnableOdometerInput = wsResponse.generalSettings.get_SDEnableOdometerInput();
                    SDEnableReceiptEmail = wsResponse.generalSettings.get_SDEnableReceiptEmail();
                    SDEnableStatsForVoip = wsResponse.generalSettings.get_SDEnableStatsForVoip();

					status = true;
				} else
					for (IMessageListener list1 : AVL_Service.msg_listeners.values())
						list1.exception("Fetch GeneralSettings Failed. " + wsResponse.errorString);

			else
				for (IMessageListener list1 : AVL_Service.msg_listeners.values())
					list1.exception("Fetch GeneralSettings Failed");

			// / change by hamza

			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.CheckButtonState(status);

			for (IMessageListener list2 : AVL_Service.msg_listeners.values())
				list2.hideProgressDialog();

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
		}

		return status;
	}
	/*-------------------------------------------------------------fetchAdjacentZones---------------------------------------------------------------*/
	protected boolean fetchAdjacentZones() {
		boolean status = false;

		try {
			for (IMessageListener list1 : AVL_Service.msg_listeners.values())
				list1.showProgressDialog("Fetching Adjacent Zones");

			String envelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><SDGetAdjacentZones xmlns=\"http://Itcurves.net/\" /></soap:Body></soap:Envelope>";

			// Calling Web Service and Parsing Response
			WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_SDGetAdjacentZones, envelope);

			if (wsResponse != null)
				if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("SDGetAdjacentZonesResult")) {
					Zone_NAMES = wsResponse.ZoneNameList.toArray(new String[wsResponse.ZoneNameList.size()]);
					Adjacent_ZoneNAMES = wsResponse.AdjZoneNameList.toArray(new String[wsResponse.AdjZoneNameList.size()]);
					status = true;
				} else
					for (IMessageListener list1 : AVL_Service.msg_listeners.values())
						list1.exception("Fetch Adjacent Zones Failed. " + wsResponse.errorString);
			else
				for (IMessageListener list1 : AVL_Service.msg_listeners.values())
					list1.exception("Fetch Adjacent Zones Failed");

			for (IMessageListener list2 : AVL_Service.msg_listeners.values())
				list2.hideProgressDialog();

		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " + e.getStackTrace()[0].getLineNumber());
		}

		return status;
	}

	/*-------------------------------------------------------------fetchAssignedAndPendingTrips----------------------------------------------------------*/
	protected boolean fetchAssignedAndPendingTrips() {
		boolean status = false;

		/*
		 * try {
		 * for (IMessageListener list1 : AVL_Service.msg_listeners.values())
		 * list1.showProgressDialog("Fetching General Settings");
		 * 
		 * String envelope =
		 * "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetAssignedAndPendingTrips xmlns=\"http://Itcurves.net/\" ><IDRIVERID>"
		 * + AVL_Service.pref
		 * .getString("DriverID", "Unknown")
		 * + "</IDRIVERID><IVEHICLEID>"
		 * + AVL_Service.pref.getString("VehicleID", "0")
		 * + "</IVEHICLEID></GetAssignedAndPendingTrips></soap:Body></soap:Envelope>";
		 * 
		 * // WS_Response wsResponse = CallingWS.submit(webServiceURL, soapAction_fetchAssignedAndPendingTrips, envelope);
		 * WS_Response wsResponse = CallingWS.submit("http://192.168.4.112/MRMSWebService/MRMSGlobalService.asmx", soapAction_fetchAssignedAndPendingTrips, envelope);
		 * 
		 * if (wsResponse != null)
		 * if (!wsResponse.error && wsResponse.responseType != null && wsResponse.responseType.equalsIgnoreCase("GetAssignedAndPendingTripsResult")) {
		 * 
		 * btMeterAvailable = wsResponse.generalSettings.get_AllowBTMeterOnSDStartUp();
		 * centrodyneMeterAvailable = wsResponse.generalSettings.get_AllowCentrodyneMeterOnSDStartUp();
		 * vivotechAvailable = wsResponse.generalSettings.get_AllowCMTVivotechOnSDStartUp();
		 * blueBambooAvailable = wsResponse.generalSettings.get_AllowBlueBamboOnSDStartUp();
		 * enableAudioCommands = wsResponse.generalSettings.get_AllowAudioCommandsOnSDStartUp();
		 * showEstMiles = wsResponse.generalSettings.get_ShowEstMilesOnDevice();
		 * googleMapAPIKey = wsResponse.generalSettings.get_GoogleMapAPIKey();
		 * callOutRequestPrompt = wsResponse.generalSettings.get_SDCallOutRequestPrompt();
		 * callOutRequestPrompt_ar = wsResponse.generalSettings.get_SDCallOutRequestPrompt_ar();
		 * sdEnableBreak = wsResponse.generalSettings.get_SDEnableBreak();
		 * sdEnableCalcEstOnDropped = wsResponse.generalSettings.get_SDEnableCalcEstOnDropped();
		 * sdCalcEstViaMRMSService = wsResponse.generalSettings.get_SDCalcEstViaMRMSService();
		 * tipVoiceEnabled = wsResponse.generalSettings.get_TipVoiceEnabled();
		 * hideCostOnSDByFundingSource = wsResponse.generalSettings.get_HideCostOnSDByFundingSource();
		 * reverseGeocodeFromMRMSService = wsResponse.generalSettings.get_ReverseGeocodeFromMRMSService();
		 * showManifestWallOnSD = wsResponse.generalSettings.get_ShowManifestWallOnSD();
		 * sendBidOffers = wsResponse.generalSettings.get_SendBidOffers();
		 * restrictSoftDropIfMeterConnected = wsResponse.generalSettings.get_RestrictSoftDropIfMeterConnected();
		 * allowedSpeedForMessaging = wsResponse.generalSettings.get_AllowedSpeedForMessaging();
		 * timerForCradleLogout = wsResponse.generalSettings.get_TimerForCradleLogout();
		 * 
		 * status = true;
		 * } else
		 * for (IMessageListener list1 : AVL_Service.msg_listeners.values())
		 * list1.exception("Fetch GeneralSettings Failed. " + wsResponse.errorString);
		 * 
		 * else
		 * for (IMessageListener list1 : AVL_Service.msg_listeners.values())
		 * list1.exception("Fetch GeneralSettings Failed");
		 * 
		 * for (IMessageListener list2 : AVL_Service.msg_listeners.values())
		 * list2.hideProgressDialog();
		 * 
		 * } catch (Exception e) {
		 * for (IMessageListener list : AVL_Service.msg_listeners.values())
		 * list.exception(e.getStackTrace()[0].getFileName() + "| " + e.getLocalizedMessage() + " in " + e.getStackTrace()[0].getMethodName() + " Line " +
		 * e.getStackTrace()[0].getLineNumber());
		 * }
		 */

		return status;
	}

	/*-------------------------------------------------------------getDeviceID----------------------------------------------------------*/
	public static String getDeviceID() {
		try {
			deviceID = tm.getDeviceId();
		} catch (Exception e) {
			try {
				if (wifiMan.isWifiEnabled())
					deviceID = wifiMan.getConnectionInfo().getMacAddress();
				else
					deviceID = "000000000000000";
			} catch (Exception ex) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.exception(e.getClass() + "| getDeviceID(): " + e.getLocalizedMessage() + "|");
				deviceID = "000000000000000";
			}
		}
		return deviceID;
	}

}// Class AVL_Service
