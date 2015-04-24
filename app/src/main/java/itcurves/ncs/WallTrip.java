package itcurves.ncs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*--------------------------------------------------------------------------------------------------------------------------
 *---------------------------------------------------- WallTrip Class ------------------------------------------------------
 *--------------------------------------------------------------------------------------------------------------------------
 */

public class WallTrip {

	private static final SimpleDateFormat MRMS_DateFormat = new SimpleDateFormat("HHmmss MMddyyyy");

	public String tripNumber;
	public String ConfirmNumber;
	public Date PUTime;
	public String PUaddress;
	public String PickupZone;
	public String DropZone;
	public String EstMiles;
	public String EstFare;
	public String PhoneNumber;
	public String CustomerName;
	public String AMBPassengers;
	public String WheelChairPassengers;
	public String LOS;
	public double DistanceFromVehicle;
	public String PickUpLat;
	public String PickUpLong;
	public boolean ShowPhoneNumberOnTrip;

	// Constructor for Unshared Trips
	public WallTrip() {

		try {
			tripNumber = "";
			ConfirmNumber = "";
			PUTime = MRMS_DateFormat.parse("235959 12319999");
			PUaddress = "";
			PickupZone = "";
			DropZone = "";
			EstMiles = "";
			EstFare = "";
			PhoneNumber = "";
			CustomerName = "";
			AMBPassengers = "";
			WheelChairPassengers = "";
			LOS = "";
			DistanceFromVehicle = -1;
			PickUpLat = "";
			PickUpLong = "";
			ShowPhoneNumberOnTrip = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return tripNumber + "|"
				+ ConfirmNumber
				+ "|"
				+ PUTime
				+ "|"
				+ PUaddress
				+ "|"
				+ PickupZone
				+ "|"
				+ DropZone
				+ "|"
				+ EstMiles
				+ "|"
				+ EstFare
				+ "|"
				+ PhoneNumber
				+ "|"
				+ CustomerName
				+ "|"
				+ AMBPassengers
				+ "|"
				+ WheelChairPassengers
				+ "|"
				+ LOS
				+ "|"
				+ PickUpLat
				+ "|"
				+ PickUpLong
				+ "|"
				+ ShowPhoneNumberOnTrip;
	}
}
