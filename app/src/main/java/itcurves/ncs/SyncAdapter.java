package itcurves.ncs;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import itcurves.ncs.webhandler.CallingWS;
import itcurves.ncs.webhandler.WS_Response;

/**
 * Created by sIrshad on 4/29/2015.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        if(AVL_Service.loggedIn) {
            if (AVL_Service.pref.getBoolean("ShowWallTrips", true)) {
                StringBuffer envelope = new StringBuffer(
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetSpecializedWallTrips xmlns=\"http://Itcurves.net/\"><vLatitude>" + AVL_Service.pref
                                .getString("LastLatitude", "0.000000")
                                + "</vLatitude><vLongitude>"
                                + AVL_Service.pref.getString("LastLongitude", "0.000000")
                                + "</vLongitude><vVehicleNumber>"
                                + AVL_Service.pref.getString("VehicleID", "0")
                                + "</vVehicleNumber></GetSpecializedWallTrips></soap:Body></soap:Envelope>");
                // StringBuffer envelope = new StringBuffer(
                // "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetSpecializedWallTrips xmlns=\"http://Itcurves.net/\"><vLatitude>39.1432028263807</vLatitude><vLongitude>-77.2227812837809</vLongitude></GetSpecializedWallTrips></soap:Body></soap:Envelope>");

                // Calling Web Service and Parsing Response
                WS_Response tempResponse = CallingWS.submit(AVL_Service.webServiceURL, AVL_Service.soapAction_SpecializedWallTrips, envelope.toString());
                if (tempResponse != null && tempResponse.responseType != null && tempResponse.responseType.equalsIgnoreCase("GetSpecializedWallTripsResult")) {

                    synchronized (TaxiPlexer.WALLTrips) {
                        TaxiPlexer.WALLTrips.clear();
                        TaxiPlexer.WALLTrips.addAll(tempResponse.wallTrips);
                        TaxiPlexer.WALLTrips.notifyAll();
                    }
                    AVL_Service.callTaxiPlexerWall();
                } else {

                    StringBuffer envelope1 = new StringBuffer(
                            "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetWallTrips xmlns=\"http://Itcurves.net/\"><vVehicleNumber>"
                                    + AVL_Service.pref.getString("VehicleID", "0")
                                    + "</vVehicleNumber></GetWallTrips></soap:Body></soap:Envelope>");

                    // Calling Web Service and Parsing Response
                    WS_Response tempResponse1 = CallingWS.submit(AVL_Service.webServiceURL, AVL_Service.soapAction_WallTrips, envelope1.toString());
                    if (tempResponse1 != null && tempResponse1.responseType != null && tempResponse1.responseType.equalsIgnoreCase("GetWallTripsResult")) {

                        synchronized (TaxiPlexer.WALLTrips) {
                            TaxiPlexer.WALLTrips.clear();
                            TaxiPlexer.WALLTrips.addAll(tempResponse1.wallTrips);
                            TaxiPlexer.WALLTrips.notifyAll();
                        }


                    }
                    AVL_Service.callTaxiPlexerWall();
                }
            }

            if (AVL_Service.showManifestWallOnSD) {
                StringBuffer envelope2 = new StringBuffer(
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetManifestSummaryInfo xmlns=\"http://Itcurves.net/\" /></soap:Body></soap:Envelope>");
                // Calling Web Service and Parsing Response
                WS_Response tempResponse2 = CallingWS.submit(AVL_Service.webServiceURL, AVL_Service.soapAction_ManifestWallTrips, envelope2.toString());
                if (tempResponse2 != null && tempResponse2.responseType != null && tempResponse2.responseType.equalsIgnoreCase("GetManifestSummaryInfoResult")) {

                    AVL_Service.callTaxiPlexerManifestWall(tempResponse2.manifestWallTrips);
                } else {

                }
            }
        }

    }

}
