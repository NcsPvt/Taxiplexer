package itcurves.ncs;

import itcurves.ncs.webhandler.JSONfunctions;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

public class DistanceCalculator {
    final static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private final static double Radius = 6377500; // earth's radius in meters (mean radius = 6,378km)

    public String pickupLong = "0";
    public String pickupLat = "0";
    public String dropOffLong = "0";
    public String dropOffLat = "0";
    public String miles = "0";
    public String minutes = "0";
    private static double milesDistance;

    public static double CalculateDistance(Double StartLat, Double StartLong, Double EndLat, Double EndLong) {
        double dLat = Math.toRadians(EndLat - StartLat);
        double dLon = Math.toRadians(EndLong - StartLong);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(StartLat)) * Math.cos(Math.toRadians(EndLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return (Radius * c) - Math.abs(Double.valueOf(AVL_Service.pref.getString("Accuracy", "0.0")));
    }

    protected String calculateDrivingDistance(String StartLocation, String EndLocation) {

        String code = "NOT_FOUND";
        if (!(StartLocation.split(",")[0].contains("Unknown") || EndLocation.split(",")[0].contains("Unknown"))) {
            JSONObject json = JSONfunctions.getJSONfromURL("http://maps.googleapis.com/maps/api/directions/json?" + "origin="
                    + StartLocation.replaceAll(" ", "%20")
                    + "&destination="
                    + EndLocation.replaceAll(" ", "%20")
                    + "&sensor=true&mode=driving");

            try {
                code = json.getString("status");

                if (code.equalsIgnoreCase("OK")) {
                    JSONArray routeArray = json.getJSONArray("routes");
                    JSONObject routes = routeArray.getJSONObject(0);

                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);

                    JSONObject distance = steps.getJSONObject("distance");
                    JSONObject duration = steps.getJSONObject("duration");
                    JSONObject endLocation = steps.getJSONObject("end_location");
                    JSONObject startLocation = steps.getJSONObject("start_location");

                    pickupLong = startLocation.getString("lng");
                    pickupLat = startLocation.getString("lat");

                    dropOffLong = endLocation.getString("lng");
                    dropOffLat = endLocation.getString("lat");

                    double meters = (distance.getInt("value")) / 1609;
                    double seconds = (duration.getInt("value")) / 60;
                    miles = decimalFormat.format(meters);
                    minutes = decimalFormat.format(seconds);

                    AVL_Service.pref
                            .edit()
                            .putString("LastEstDistance", miles)
                            .putString("LastEstTime", minutes)
                            .putString("LastPickupLong", pickupLong)
                            .putString("LastPickupLat", pickupLat)
                            .putString("LastDropOffLong", dropOffLong)
                            .putString("LastDropOffLat", dropOffLat)
                            .commit();
                }

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
                for (IMessageListener list : AVL_Service.msg_listeners.values())
                    list.exception("[exception in calculateDrivingDistance in distance calculator][calculateDrivingDistance][" + e.getLocalizedMessage() + "] NoSuchMethodException");
            }
        }
        return code;
    }
    protected static double calculateDrivingDistance(double StartLat, double StartLong, double EndLat, double EndLong) {
        String code = "NOT_FOUND";
        try {
            JSONObject json = JSONfunctions.getJSONfromURL("http://maps.googleapis.com/maps/api/directions/json?" + "origin="
                    + String.valueOf(StartLat)
                    + ","
                    + String.valueOf(StartLong)
                    + "&destination="
                    + String.valueOf(EndLat)
                    + ","
                    + String.valueOf(EndLong)
                    + "&sensor=true&mode=driving");

            code = json.getString("status");

            if (code.equalsIgnoreCase("OK")) {

                JSONArray routeArray = json.getJSONArray("routes");
                JSONObject routes = routeArray.getJSONObject(0);

                JSONArray legs = routes.getJSONArray("legs");
                JSONObject steps = legs.getJSONObject(0);

                JSONObject distance = steps.getJSONObject("distance");

                milesDistance = (Double.parseDouble(distance.getString("value").toString())) / 1609;

            } else
                milesDistance = CalculateDistance(StartLat, StartLong, EndLat, EndLong) / 1609;

        } catch (Exception e) {
            milesDistance = CalculateDistance(StartLat, StartLong, EndLat, EndLong) / 1609;
        }

        return milesDistance;

    }

}
