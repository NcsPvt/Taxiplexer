package itcurves.ncs;

import java.util.Calendar;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import itcurves.regencycab.R;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Maps extends MapActivity {
	MyMapView mMapView;
	MyOverlay mMapOverlay;
	Handler mHandler = new Handler();
	TextView StreetTv;
	TextView dialogTitleText;
	TextView dialogText;
	EditText dialogEditText;
	LinearLayout zoomView;
	// Button menuBtn;
	// Button locateMeBtn;
	Button dialogYesBtn;
	Button dialogNoBtn;

	// ImageView favouriteImage;
	ImageView requestImage;
	Drawable customerMarker;
	MapOverlay itemizedOverlay;
	Calendar c;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			// getWindow().setWindowAnimations(R.anim.layout_slide_back);
			setContentView(R.layout.reservation);
			c = Calendar.getInstance();
			// Populate the map member
			mMapView = (MyMapView) findViewById(R.id.reservationMaps);
			StreetTv = (TextView) findViewById(R.id.street_tv);
			StreetTv.setSingleLine();
			StreetTv.setText(Farsi.Convert(getResources().getString(R.string.unknownlocation)));
			// Add overlay
			mMapOverlay = new MyOverlay(getBaseContext(), mMapView);
			mMapView.getOverlays().add(mMapOverlay);
			GeoPoint arg0 = new GeoPoint(24721502, 46688309);
			mMapView.getController().setCenter(arg0);

			// Add zoom controls
			// mMapView.setBuiltInZoomControls(true);
			zoomView = (LinearLayout) mMapView.getZoomControls();

			zoomView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			zoomView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
			mMapView.addView(zoomView);
			mMapView.getController().setZoom(17);

			// Add Buttons
			/*
			 * favouriteImage = (ImageView) findViewById(R.id.imageView1);
			 * 
			 * menuBtn = (Button) findViewById(R.id.btnMenu);
			 * menuBtn.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override
			 * public void onClick(View arg0) {
			 * onBackPressed();
			 * 
			 * }
			 * });
			 * locateMeBtn = (Button) findViewById(R.id.btnLocateMe);
			 */
			requestImage = (ImageView) findViewById(R.id.imageRequest);
			requestImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + AVL_Service.pref.getString("MapLat", "0.0")
							+ ","
							+ AVL_Service.pref.getString("MapLong", "0.0"))), 8000);
					finish();

				}
			});

			// Add listeners
			mMapView.setOnChangeListener(new MapViewChangeListener());
			mMapOverlay.setOnTapListener(new MapViewTapListener());
		} catch (Exception ex) {
			String s = ex.toString();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	protected void onResume() {
		// animateIn this activity
		// ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
		super.onResume();
	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------- MapViewChangeListener Class -----------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	private class MapViewChangeListener implements MyMapView.OnChangeListener {

		@Override
		public void onChange(MapView view, GeoPoint newCenter, GeoPoint oldCenter, int newZoom, int oldZoom) {
			// Check values
			if ((!newCenter.equals(oldCenter)) && (newZoom != oldZoom)) {
				// Toast.makeText(MapsLocation.this, "Map Zoom + Pan", Toast.LENGTH_SHORT).show();
				// Toast.makeText(TripReservation.this, (newCenter.getLatitudeE6() / 1E6) + " " + (newCenter.getLongitudeE6() / 1E6), Toast.LENGTH_SHORT).show();
				AVL_Service.pref.edit().putString("MapLat", String.valueOf(newCenter.getLatitudeE6() / 1E6)).putString("MapLong", String.valueOf(newCenter.getLongitudeE6() / 1E6)).commit();
				try {
					reverseGeoCode(newCenter.getLatitudeE6() / 1E6, newCenter.getLongitudeE6() / 1E6);
					StreetTv.setText(AVL_Service.pref.getString("PickUpStreet", ""));
				} catch (Exception e) {
					StreetTv.setText(Farsi.Convert(getResources().getString(R.string.unknownlocation)));

				}

			} else if (!newCenter.equals(oldCenter)) {
				Toast.makeText(Maps.this, (newCenter.getLatitudeE6() / 1E6) + " " + (newCenter.getLongitudeE6() / 1E6), Toast.LENGTH_SHORT).show();
				AVL_Service.pref.edit().putString("MapLat", String.valueOf(newCenter.getLatitudeE6() / 1E6)).putString("MapLong", String.valueOf(newCenter.getLongitudeE6() / 1E6)).commit();
				try {
					reverseGeoCode(newCenter.getLatitudeE6() / 1E6, newCenter.getLongitudeE6() / 1E6);
					StreetTv.setText(AVL_Service.pref.getString("PickUpStreet", ""));
				} catch (Exception e) {
					StreetTv.setText(Farsi.Convert(getResources().getString(R.string.unknownlocation)));

				}
			} else if (newZoom != oldZoom) {
				// Toast.makeText(MapsLocation.this, "Map Zoom", Toast.LENGTH_SHORT).show();
				Toast.makeText(Maps.this, (newCenter.getLatitudeE6() / 1E6) + " " + (newCenter.getLongitudeE6() / 1E6), Toast.LENGTH_SHORT).show();
				AVL_Service.pref.edit().putString("MapLat", String.valueOf(newCenter.getLatitudeE6() / 1E6)).putString("MapLong", String.valueOf(newCenter.getLongitudeE6() / 1E6)).commit();
				try {
					reverseGeoCode(newCenter.getLatitudeE6() / 1E6, newCenter.getLongitudeE6() / 1E6);
					StreetTv.setText(AVL_Service.pref.getString("PickUpStreet", ""));
				} catch (Exception e) {
					StreetTv.setText(Farsi.Convert(getResources().getString(R.string.unknownlocation)));

				}

			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------
	 *------------------------------------------------------- MapViewTapListener Class --------------------------------------------------------------
	 *-----------------------------------------------------------------------------------------------------------------------------------------------
	 */
	private class MapViewTapListener implements MyOverlay.OnTapListener {
		@Override
		public void onTap(GeoPoint p, MapView mapView) {
			// Display message
			// Toast.makeText(MapsLocation.this, "Map Tap", Toast.LENGTH_SHORT).show();
			Toast.makeText(Maps.this, (p.getLatitudeE6() / 1E6) + " " + (p.getLongitudeE6() / 1E6), Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onDoubleTap(GeoPoint p, MapView mapView) {
			// Reposition map
			Point screenPoint = new Point();
			mMapView.getProjection().toPixels(p, screenPoint);
			mMapView.getController().zoomInFixing(screenPoint.x, screenPoint.y);

			// Display message
			// Toast.makeText(MapsLocation.this, "Map Double Tap", Toast.LENGTH_SHORT).show();
			Toast.makeText(Maps.this, (p.getLatitudeE6() / 1E6) + " " + (p.getLongitudeE6() / 1E6), Toast.LENGTH_SHORT).show();

		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	protected void reverseGeoCode(double lat, double lon) throws Exception {

		Geocoder gc = new Geocoder(Maps.this);

		AVL_Service.pref.edit().putString("PickUpStreet", gc.getFromLocation(lat, lon, 1).get(0).getAddressLine(0)).putString(
			"PickUpCity",
			gc.getFromLocation(lat, lon, 1).get(0).getAddressLine(1).split(" ")[0]).putString(
			"PickUpZip",
			(gc.getFromLocation(lat, lon, 1).get(0).getAddressLine(1).split(" ").length > 1) ? gc.getFromLocation(lat, lon, 1).get(0).getAddressLine(1).split(" ")[1] : "").putString(
			"PickUpCountry",
			gc.getFromLocation(lat, lon, 1).get(0).getAddressLine(2)).commit();
	}

}
