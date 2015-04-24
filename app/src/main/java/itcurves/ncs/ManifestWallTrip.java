package itcurves.ncs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManifestWallTrip {

	private static final SimpleDateFormat MRMS_DateFormat = new SimpleDateFormat("HHmmss MMddyyyy");

	public String ManifestNumber;
	public String PickupZone;
	public String DropZone;
	public String RouteStartTime;
	public String RouteEndTime;
	public Date stRouteStartTime;
	public Date stRouteEndTime;
	public String MaxWC;
	public String MaxAmbulatory;
	public String TotalNoOfTrip;
	public String TotalDistMile;
	public String TotalRouteMin;
	public String TotalCost;

	// Constructor for Unshared Trips
	public ManifestWallTrip() {

		try {
			ManifestNumber = "";
			PickupZone = "";
			DropZone = "";
			RouteStartTime = "";
			RouteEndTime = "";
			stRouteStartTime = MRMS_DateFormat.parse("235959 12319999");
			stRouteEndTime = MRMS_DateFormat.parse("235959 12319999");
			MaxWC = "";
			MaxAmbulatory = "";
			TotalNoOfTrip = "";
			TotalDistMile = "";
			TotalRouteMin = "";
			TotalCost = "";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return ManifestNumber + "|"
				+ PickupZone
				+ "|"
				+ DropZone
				+ "|"
				+ RouteStartTime
				+ "|"
				+ RouteEndTime
				+ "|"
				+ DropZone
				+ "|"
				+ MaxWC
				+ "|"
				+ MaxAmbulatory
				+ "|"
				+ TotalNoOfTrip
				+ "|"
				+ TotalDistMile
				+ "|"
				+ TotalRouteMin
				+ "|"
				+ TotalCost;
	}

}
