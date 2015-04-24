package itcurves.ncs;

public class LocalizedResources {
	public static String getDesc(String pMessage) {
		String resourceId = "";
		if (pMessage.trim().contains("Activation Required By Admin Thanks"))
			resourceId = "ActivationRequiredByAdmin";
		else if (pMessage.trim().contains("Device is not Registered, Registration Required"))
			resourceId = "DeviceisnotRegistered";
		else if (pMessage.trim().contains("Driver Activation Required"))
			resourceId = "DriverActivationRequired";
		else if (pMessage.trim().contains("Device Registered Successfully, Activation Required"))
			resourceId = "DeviceRegisteredSuccessfully";
		else if (pMessage.trim().contains("Are you there! System is assuming that your device is inactive"))
			resourceId = "InActivitySystemMessage";
		else if (pMessage.trim().contains("Application version is not compatible with Server, Press"))
			resourceId = "Applicationversionisnotcompatible";
		else if (pMessage.trim().contains("Booked Out from Zone and Stand Due To Inactivity"))
			resourceId = "BookedOutDueToInActivity";
		else if (pMessage.trim().contains("AVL Updated"))
			resourceId = "AVLUpdated";
		else if (pMessage.trim().contains("Vehicle:"))
			resourceId = "vehicle";
		else if (pMessage.trim().contains("Your Device has been Locked"))
			resourceId = "YourDevicehasbeenLocked";
		else if (pMessage.trim().contains("You are not Authorized to Use This Device"))
			resourceId = "notAuthorizedtoUseDevice";
		else if (pMessage.trim().contains("Wrong Driver Number or Pin Provided"))
			resourceId = "WrongDriverNumberorPin";
		else if (pMessage.trim().contains("Account has been Locked"))
			resourceId = "AccounthasbeenLocked";
		else if (pMessage.trim().contains("Wrong Vehicle Provided"))
			resourceId = "WrongVehicleProvided";
		else if (pMessage.trim().contains("Vehicle Activation Required"))
			resourceId = "VehicleActivationRequired";
		else if (pMessage.trim().contains("Wrong Vehicle Association Provided"))
			resourceId = "WrongVehicleAssociation";
		else if (pMessage.trim().contains("Driver is already in login state with some other device"))
			resourceId = "Driverisalreadyinloginstate";
		else if (pMessage.trim().contains("Wrong Vehicle# provided. Please provide correct"))
			resourceId = "CheckinFailure";
		else if (pMessage.trim().contains("Check-in UnAuthorized: Check-in is not"))
			resourceId = "CheckinUnAuthorized";
		else if (pMessage.trim().contains("Logged in Successfully"))
			resourceId = "LoggedinSuccessfully";
		else if (pMessage.trim().contains("Successfully Logged Off"))
			resourceId = "LoggedOffSuccessfully";
		else if (pMessage.trim().contains("UnKnown Zone"))
			resourceId = "UnKnownZone";
		else if (pMessage.trim().contains("Manifest couldn't be assigned and dispatched"))
			resourceId = "Manifestcouldntbeassigned";
		else if (pMessage.trim().contains("Trip has been successfully assigned and dispatched"))
			resourceId = "Triphasbeensuccessfullyassigned";
		else if (pMessage.trim().contains("Trip couldn't be assigned and dispatched because request is not available"))
			resourceId = "Tripcouldntbeassignedrequestnotavailable";
		else if (pMessage.trim().contains("You received a trip offer a few moments ago. Please try again after 40 seconds"))
			resourceId = "Youreceivedtripoffer";
		else if (pMessage.trim().contains("Trip couldn't be assigned and dispatched because required vehicle type not matched"))
			resourceId = "Tripnotassignedvehicletypenotmatched";
		else if (pMessage.trim().contains("Trip couldn't be assigned and dispatched because required gender not matched"))
			resourceId = "Tripntbeassignedrequiredgendernotmatched";
		else if (pMessage.trim().contains("Trip couldn't be assigned and dispatched because trip belongs to restricted account"))
			resourceId = "Tripntassigntripbelongsrestrictedaccount";
		else if (pMessage.trim().equalsIgnoreCase("Trip couldn't be assigned and dispatched because required wheelchair count not matched"))
			resourceId = "Notdispatchedrequiredwheelchaircountnotmatched";
		else if (pMessage.trim().equalsIgnoreCase("Trip couldn't be assigned and dispatched because required capacity not matched"))
			resourceId = "Notassignedanddispatchedrequiredcapacitynotmatched";
		else if (pMessage.trim().equalsIgnoreCase("Trip couldn't be assigned and dispatched"))
			resourceId = "Tripcouldntbeassigned";
		else if (pMessage.trim().contains("Trip Cleared By SDHS On Late Response"))
			resourceId = "TripClearedBySDHS";
		else if (pMessage.trim().contains("Trip Cleared By Dispatcher"))
			resourceId = "TripClearedByDispatcher";
		else if (pMessage.trim().contains("Booked Out from Zone and Stand"))
			resourceId = "BookedOutfromZoneandStand";
		else if (pMessage.trim().contains("Could not Create Flagger in System, Service ID is not 9999"))
			resourceId = "CouldnotCreateFlaggerSystem";
		else if (pMessage.trim().contains("Flagger Successfully Created in System"))
			resourceId = "FlaggerSuccessfullyCreated";
		else if (pMessage.trim().contains("Device has been Locked By Admin"))
			resourceId = "DevicehasbeenLockedAdmin";
		else if (pMessage.trim().contains("Update of this Application Available on Server"))
			resourceId = "UpdateofthisApplicationAvailable";
		else if (pMessage.trim().contains("Device has been Deactivated By Admin"))
			resourceId = "DevicehasbeenDeactivatedByAdmin";
		else if (pMessage.trim().contains("Device has been Removed By Admin"))
			resourceId = "DevicehasbeenRemovedByAdmin";
		else if (pMessage.trim().contains("Rank Updated"))
			resourceId = "RankUpdated";
		else if (pMessage.trim().contains("regression due to no show or cancellation"))
			resourceId = "Regressionduetonoshoworcancellation";
		else if (pMessage.trim().toLowerCase().contains("bye"))
			resourceId = "bye";
		else if (pMessage.trim().toLowerCase().contains("trip has been cancelled"))
			resourceId = "Triphasbeencancelled";
		else if (pMessage.trim().toLowerCase().contains("trip has been updated"))
			resourceId = "Triphasbeenupdated";
		else if (pMessage.trim().toLowerCase().contains("no show approved"))
			resourceId = "NoShowApproved";
		else if (pMessage.trim().toLowerCase().contains("pickedup"))
			resourceId = "PickedUp";
		else if (pMessage.trim().toLowerCase().contains("callout")) {
			String[] s = pMessage.split(":");
			if (s[2].equalsIgnoreCase("Successful") || s[2].equalsIgnoreCase("answer"))
				resourceId = "Calloutsuccessfull";
			else
				resourceId = "Calloutunsuccessfull";
		} else if (pMessage.trim().toLowerCase().contains("noshow"))
			resourceId = "NoShowReq";
		else if (pMessage.trim().toLowerCase().contains("noshowreq"))
			resourceId = "NoShowReq";
		else if (pMessage.trim().toLowerCase().contains("atlocation"))
			resourceId = "AtLocation";
		else if (pMessage.trim().toLowerCase().contains("cash payment successful"))
			resourceId = "CashPaymentSuccessful";
		else if (pMessage.trim().toLowerCase().contains("voucher payment successful"))
			resourceId = "VoucherPaymentSuccessful";
		else if (pMessage.trim().toLowerCase().contains("trip has been successfully assigned"))
			resourceId = "TripSuccessfullyAssignedDispatched";
		else if (pMessage.trim().toLowerCase().contains("trip is cleared by dispatcher"))
			resourceId = "TripisClearedbyDispatcher";
		else if (pMessage.trim().toLowerCase().contains("trip couldn't be assigned and dispatched because request is not available"))
			resourceId = "TripNotAssignedDispatched";
		else if (pMessage.trim().toLowerCase().contains("bid has been recieved and accepted by system"))
			resourceId = "BidRecievedAccepted";
		else if (pMessage.trim().toLowerCase().contains("the trip has been approved as noshow"))
			resourceId = "TripApprovedNOSHOW";
		else if (pMessage.trim().toLowerCase().contains("trip un-assigned by dispatcher"))
			resourceId = "TripUnAssignedDispatcher";
		else if (pMessage.trim().toLowerCase().contains("trip cancelled by dispatcher on reason"))
			resourceId = "TripCancelledDispatcherReason";
		else if (pMessage.trim().toLowerCase().contains("trip cancelled by dispatcher"))
			resourceId = "TripCancelledbyDispatcher";
		else if (pMessage.trim().toLowerCase().contains("the trip has state= cancelled"))
			resourceId = "TripStateCancelled";
		else if (pMessage.trim().toLowerCase().contains("booked in zone"))
			resourceId = "BookedInZone";
		else if (pMessage.trim().toLowerCase().contains("no show approved"))
			resourceId = "NoShowApproved";
		else if (pMessage.trim().toLowerCase().contains("invalid cleartrip message recieved"))
			resourceId = "InvalidClearTripMessageRecieved";
		else if (pMessage.trim().toLowerCase().contains("you are booked in "))
			resourceId = "YouAreBookedinbyDispatcher";
		else if (pMessage.trim().toLowerCase().contains("are you there"))
			resourceId = "AreYouThereInActivity";
		else if (pMessage.trim().toLowerCase().contains("fetch generalsettings failed"))
			resourceId = "FetchGeneralSettingsFailed";
		else if (pMessage.trim().toLowerCase().contains("fetch adjacent zones failed"))
			resourceId = "FetchAdjacentZonesFailed";
		else if (pMessage.trim().toLowerCase().contains("fetchcannedMsgslist() failed"))
			resourceId = "fetchCannedMsgsListFailed";
		else if (pMessage.trim().toLowerCase().contains("fetchAppslist() failed"))
			resourceId = "fetchAppsListFailed";
		else if (pMessage.trim().toLowerCase().contains("fetchccmapping failed"))
			resourceId = "FetchCCMappingFailed";
		else if (pMessage.trim().toLowerCase().contains("fetch ccprocessorslist failed"))
			resourceId = "FetchCCProcessorsListFailed";
		else if (pMessage.trim().contains("CANCELLED"))
			resourceId = "Cancelled";
		else if (pMessage.trim().contains("because request is not available"))
			resourceId = "RequestIsNotAvailable";
		else if (pMessage.trim().contains("offer a few moments ago. Please try again after 40 seconds"))
			resourceId = "PleaseTryAgainAfterFortySeconds";
		else if (pMessage.trim().contains("dispatched because required vehicle type not matched"))
			resourceId = "RequiredVehicleTypeNotMatched";
		else if (pMessage.trim().contains("dispatched because required gender not matched"))
			resourceId = "GenderNotMatched";
		else if (pMessage.trim().contains("because trip belongs to restricted account"))
			resourceId = "BelongsToRestrictedAccount";
		else if (pMessage.trim().contains("trip couldn't be assigned and dispatched"))
			resourceId = "couldnotBeAssignedAndDispatched";
		else if (pMessage.trim().contains("Device Already Assign To Driver"))
			resourceId = "OtherDeviceAlreadyDriverCancelled";
		else if (pMessage.trim().contains("Please Use Valid Driver Number"))
			resourceId = "PleaseUseValidDriverNumber";
		else if (pMessage.trim().contains("Currently InUse To Driver"))
			resourceId = "CurrentlyInUseToDriver";
		else if (pMessage.trim().contains("booked out from zone"))
			resourceId = "bookedoutfromzone";
		else if (pMessage.trim().contains("You are booked out from stand"))
			resourceId = "bookedoutfromstand";
		else if (pMessage.trim().contains("You are booked in zone"))
			resourceId = "bookedinzone";
		else if (pMessage.trim().contains("You are booked in stand"))
			resourceId = "bookedinstand";
		else if (pMessage.trim().contains("Booked In Colo Stand"))
			resourceId = "BookedInColoStand";
		else if (pMessage.trim().contains("Booked In Stand for Zone"))
			resourceId = "BookedInStandforZone";
		else if (pMessage.trim().contains("for Zone"))
			resourceId = "forZone";
		else if (pMessage.trim().contains("By Dispatcher"))
			resourceId = "ByDispatcher";
		else if (pMessage.trim().contains("Check-in Failure - NoShift"))
			resourceId = "CheckinFailureNoShift";
		else if (pMessage.trim().contains("Trip Cancelled by Customer"))
			resourceId = "TripCancelledbyCustomer";
		else if (pMessage.trim().contains("User does not exist. Please enter"))
			resourceId = "Usernotexist";
		else if (pMessage.trim().contains("Trip is not available"))
			resourceId = "Apology";
		else
			resourceId = "EmptyString";

		return resourceId;
	}
}
