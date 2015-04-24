package itcurves.ncs;

public final class MsgType {
	protected static final int LoginBtn = 0;
	public static final int HandShake = 1;
	public static final int HandShakeResp = 2;
	public static final int HandShakeRespFields = 3;

	public static final int Login_Req = 5;
	public static final int Login_Resp = 6;
	public static final int Login_RespFields = 7;

	public static final int AVL = 7;
	public static final int BID_Offer = 8;
	public static final int BID = 9;
	public static final int TripDetails = 10;
	public static final int TripResponse = 11;
	public static final int PICKEDUP = 12;
	public static final int MeterOff = 13;
	public static final int Payment = 14;
	public static final int PaymentResponse = 15;
	public static final int NoShowResp = 16;
	public static final int CallOut = 17;
	public static final int Emergency = 18;
	public static final int Penalized = 19;
	public static final int BookinReq = 20;
	public static final int BookInResponse = 21;
	public static final int ZFT = 22;
	public static final int LogoffReq = 26;
	public static final int LogoffResp = 27;
	public static final int AVLResp = 28;
	public static final int AVLRespFields = 6;

	public static final int FLUSH_BID = 29;
	public static final int ZFTReq = 30;
	public static final int ManifestReq = 31;
	public static final int Manifest = 32;
	public static final int ManifestResponse = 33;
	public static final int TripDetailUpdate = 34;
	public static final int TextMessage = 35;
	public static final int PopUpMessage = 36;
	public static final int ClearTrip = 37;
	public static final int registerDevice = 38;
	public static final int registerResp = 39;
	public static final int newAppOnServer = 40;

	public static final int ORSDLOGGEDOUT = 42;
	public static final int EstimatedFareReq = 43;
	public static final int EstimatedFareResp = 44;
	public static final int updateMeterBTAddress = 65;
	public static final int VehicleMilage = 66;
	public static final int SDTRIPDTLREQ = 67;
	public static final int SDHEARTBEAT = 68;

	public static final int wallTripsArray = 4;
	public static final int ReverseGeoCode = 88;
	public static final int systemBroadcast = 89;
	public static final int CreditCardData = 90;
	public static final int killProgress = 91;
	public static final int showProgress = 92;
	protected static final int hideProgress = 93;
	protected static final int connectVivotech = 94;
	protected static final int TabberClick = 95;
	protected static final int kill = 96;
	public static final int turnGPSON = 97;
	public static final int exception = 98;
	public static final int invalidIP = 99;
	protected static final int Reminder = 100;
	public static final int messageHistoryArray = 101;
	public static final int exceptionToast = 102;
	public static final int SDINACTIVITYREQUEST = 103;
	public static final int SDINACTIVITYRESPONSE = 104;
	public static final int SDONBREAK = 105;
	public static final int SDMFSTDTLREQ = 106;
	public static final int manifestwallTripsArray = 107;
	public static final int SDBREAKENDED = 108;
	public static final int SDADDRESSUPDATE = 109;
	public static final int SDTRIPFARE = 110;
	public static final int SDTRIPOFFER = 111;
	public static final int SDTRIPRSP = 112;
	public static final int EmergencyConfirmation = 113;
    public static final int MSG_RCF = 11111;
    public static final int MSG_CF_RCV = 12222;
    public static final int MSG_MON_RCV= 13333;  // MON - Meter On/ Time On
    public static final int MSG_QTD = 14444;  // QTD - Receive Query Trip Data
    public static final int MSG_RTD_RCV = 15555;  // CF - Receive Trip Data
    public static final int MSG_TOFF_RCV = 16666;  // TOFF - Time Off
    public static final int MSG_MOFF_RCV = 17777;  // MOFF - Meter Off
    public static final int MSG_TON_RCV = 18888;  // TON - Time On
    public static final int MSG_CONNECTION_ALIVE_RCV = 10000;  // CONNECTION_ALIVE
    public static final int MSG_CONNECTION_ALIVE_REP = 10001;  // CONNECTION_ALIVE_REP

	public static String getDesc(int msgNumber) {
		switch (msgNumber) {
			case HandShake :
				return "HandShake";
			case HandShakeResp :
				return "HandShakeResp";
			case registerDevice :
				return "RegisterDevice";
			case registerResp :
				return "RegisterResp";
			case Login_Req :
				return "LoginReq";
			case Login_Resp :
				return "LoginResp";
			case AVL :
				return "AVL";
			case AVLResp :
				return "AVL_Resp";
			case BID :
				return "BID";
			case BID_Offer :
				return "BID-Offer";
			case FLUSH_BID :
				return "FLUSH BID";
			case TripDetails :
				return "TripDetails";
			case TripDetailUpdate :
				return "Trip-Update";
			case TripResponse :
				return "TripResponse";
			case ClearTrip :
				return "ClearTrip";
			case PICKEDUP :
				return "PICKEDUP";
			case MeterOff :
				return "DROPPED";
			case PaymentResponse :
				return "PaymentResponse";
			case Payment :
				return "Payment";
			case NoShowResp :
				return "NoShowResp";
			case CallOut :
				return "CallOut";
			case Emergency :
				return "Emergency";
			case BookinReq :
				return "BookinReq";
			case BookInResponse :
				return "BookinResp";
			case LogoffReq :
				return "LogoffReq";
			case LogoffResp :
				return "LogoffResp";
			case ZFTReq :
				return "ZFTReq";
			case ZFT :
				return "ZFT";
			case ManifestReq :
				return "ManifestReq";
			case Manifest :
				return "Manifest";
			case TextMessage :
				return "Text";
			case ORSDLOGGEDOUT :
				return "Forced Logout";
			case SDTRIPDTLREQ :
				return "Wall Trip Detail Req";
			case SDHEARTBEAT :
				return "HeartBeat";
			case exception :
				return "EXCEPTION";
			case exceptionToast :
				return "EXCEPTION TOAST";
			case SDINACTIVITYREQUEST :
				return "Inactivity Request";
			case SDINACTIVITYRESPONSE :
				return "Inavtivity Response";
			case SDONBREAK :
				return "ON Break/Resume";
			case SDMFSTDTLREQ :
				return "Manifest Wall Trip Detail Req";
			case SDADDRESSUPDATE :
				return "Address update";
			default :
				return "Unknown Msg Type";

		}

	}

}
