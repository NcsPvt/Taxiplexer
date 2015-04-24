package itcurves.ncs;

public final class Constants {

	public static final short MAXSOURCEIDSIZE = 16;
	public static final short MAXSENDATTEMPTS = 3;
	public static final short MAXDESTIDSIZE = 16;
	public static final short MAXMSGTAGSIZE = 17;
	public static final short MAXSENDBUFFERSIZE = 1024;
	public static final short MAXRECEIVEBUFFERSIZE = 1024;
	public static final short MAXHEADERLENGTH = 70;
	public static final short MESSAGETAILERLENGTH = 4;
	public static final char EOT = (char) 0x4;
	public static final char COLSEPARATOR = '^';
	public static final char ROWSEPARATOR = '~';
	public static final char BODYSEPARATOR = (char) 0x2;

	public static final String PREFS_NAME = "AVLPrefsFile";
	public static final String PREFS_RP = "RunningProcesses";
	public static final String PREF_GPS_Atleast = "GPS_Atleast";
	public static final String PREF_GPSAt = "GPS_At";
	public static final String PREF_Company = "Company";
	public static final String Allowable_Stand_Distance = "Stand_Distance";

	public static final int SIGNATURE_CAPTURE = 1;
	public static final int ZFT_Fields = 4;
	public static final int AVLResp_Fields = 3;
	public static final int BID_Fields = 3;

	static final int BID_CODE = 31;
	static final int TripAccept_CODE = 32;
	static final int TripReject_CODE = 33;
	static final int GPS_REQUEST_CODE = 34;
	static final int REQUEST_ENABLE_BT = 35;
	static final int REQUEST_CONNECT_DEVICE_INSECURE = 36;

}
