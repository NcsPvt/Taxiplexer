package itcurves.ncs;

import java.util.ArrayList;

import itcurves.ncs.taximeter.messages.MeterMessage;

public interface IMessageListener {

	public void receivedHandshakeResponse(String[] msg);

	public void receivedLoginResponse(String[] columns);

	public void receivedLogoffResponse(String[] msg);

	public void receivedBookinResponse(String msg);

	public void receivedAVLResp(String msg);

	public void receivedBidUpdate(String msg);

	public void receivedTripDetails(String msg);

	public void receivedTripOffer(String msg);

	public void receivedLocationChange(String address);

	public void receivedMeterMessage(MeterMessage msg);

	public void receivedVivotechMessage(String msg);

	public void receivedZFT(String[] rows);

	public void receivedFlushBid(String packetString);

	public void exception(String exception);

	public void exceptionToast(String exception);

	public void receivedPaymentResp(String packetString);

	public void receivedManifest(String packetString);

	public void invalidServerIP(String string);

	public void receivedClearTrip(String packetString);

	public void receivedTripUpdate(String TripUpdate);

	public void receivedSDTripFare(String TripFare);

	public void receivedRegisterResponse(String[] columns);

	public void receivedAppUpdate(String packetRcvd);

	// public void receivedSDTripFare(String packetRcvd);
	// hamza
	public void CheckButtonState(boolean value);

	public void receivedNoShowResponse(String packetRcvd);

	public void receivedTurnONGPS();

	public String getName();

	public void receivedVivotechError(String string);

	public void receivedForcedLogout(String string);

	public void receivedPopupMsg(String msg, String showAsToast);

	public void receivedCreditCardData(CreditCard ccInfo);

	void receivedEstimatedFareResp(String msg);

	public void receivedSystemBroadCast(String action);

	public void showProgressDialog(String string);

	public void hideProgressDialog();

	public void receivedSetNetworkStatus(boolean status);

	public void receivedSetGPSstatus(boolean status);

	public void receivedMeterStatus();

	public void receivedHeartBeatChange();

	public void receivedTextMsg(String msg, String title);

	public void receivedWallTrips(ArrayList<WallTrip> wallTripArray);

	public void receivedCannedMessages(ArrayList<CannedMessage> cMsgs);

	public void receivedSDInactiveRequest(String Body);

	public void receivedSDBreakEnded(String Body);

	public void SD_BookOut();

	public void LogException(String Message);

	public void receivedManifestWallTrips(ArrayList<ManifestWallTrip> manifestwallTripArray);

	public void receivedEmergencyConfirmation();

	public void fetchAssignedAndPendingTrips();

    public void receivedSoftMeterMessage(String msg);
}
