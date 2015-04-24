package itcurves.ncs;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;

public class MapOverlay extends ItemizedOverlay<OverlayItem> {
	private final List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public MapOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public void addOverlayItem(int lat, int lon, String title, Drawable altMarker) {
		GeoPoint point = new GeoPoint(lat, lon);
		OverlayItem overlayItem = new OverlayItem(point, title, null);
		addOverlayItem(overlayItem, altMarker);
	}

	public void addOverlayItem(OverlayItem overlayItem) {
		mOverlays.add(overlayItem);
		populate();
	}

	public void addOverlayItem(OverlayItem overlayItem, Drawable altMarker) {
		overlayItem.setMarker(boundCenterBottom(altMarker));
		addOverlayItem(overlayItem);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	public boolean onTap(int index) {
		// Toast.makeText(myContext, getItem(index).getTitle(), Toast.LENGTH_LONG).show();
		return true;
	}

	public void clear() {
		mOverlays.clear();
		populate();
	}

}
