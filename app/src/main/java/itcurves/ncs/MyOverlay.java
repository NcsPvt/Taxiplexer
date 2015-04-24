package itcurves.ncs;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyOverlay extends Overlay {
	private MapView mMapView = null;
	private GestureDetector mGestureDetector = null;
	private final SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (mTapListener != null) {
				GeoPoint p = mMapView.getProjection().fromPixels((int) e.getRawX(), (int) e.getRawY());
				mTapListener.onTap(p, mMapView);
			}
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mTapListener != null) {
				GeoPoint p = mMapView.getProjection().fromPixels((int) e.getRawX(), (int) e.getRawY());
				mTapListener.onDoubleTap(p, mMapView);
			}
			return true;
		}
	};

	// ------------------------------------------------------------------------
	// CONSTRUCTOR
	// ------------------------------------------------------------------------

	MyOverlay(Context context, MapView mapView) {
		mMapView = mapView;
		mGestureDetector = new GestureDetector(context, mGestureListener);
	}

	// ------------------------------------------------------------------------
	// LISTENER DEFINITIONS
	// ------------------------------------------------------------------------

	// Tap listener
	public interface OnTapListener {
		public void onTap(GeoPoint p, MapView mapView);
		public void onDoubleTap(GeoPoint p, MapView mapView);
	}

	// ------------------------------------------------------------------------
	// GETTERS / SETTERS
	// ------------------------------------------------------------------------

	// Setters
	public void setOnTapListener(OnTapListener listener) {
		mTapListener = listener;
	}

	// ------------------------------------------------------------------------
	// MEMBERS
	// ------------------------------------------------------------------------

	private OnTapListener mTapListener;

	// ------------------------------------------------------------------------
	// EVENT HANDLERS
	// ------------------------------------------------------------------------

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
		return mGestureDetector.onTouchEvent(motionEvent);
	}
}