package itcurves.ncs;

import java.util.ArrayList;

import org.abtollc.sdk.AbtoApplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class CabDispatch extends AbtoApplication {

	public static Intent serviceIntent;
	public static String PACKAGE_NAME;

	@Override
	public void onCreate() {
		Log.w(getClass().getSimpleName(), "onCreate() called.");
		PACKAGE_NAME = getApplicationContext().getPackageName();
		Log.w(getClass().getSimpleName(), "PACKAGE_NAME = " + PACKAGE_NAME);
		listeners = new ArrayList<IAVLServiceStatusListener>();

		// Let's start the NetworkService
		serviceIntent = new Intent(this, AVL_Service.class);
		startService(serviceIntent);

		ServiceConnection conn = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				serviceHandle = (IAVL_Service) service;
				statusCode = serviceHandle.loggedIn();
				Log.w(getClass().getSimpleName(), "Service bound ");

				for (int i = 0; i < listeners.size(); i++) {
					listeners.get(i).networkServiceStarted(serviceHandle);
				}
			}

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				Log.w(getClass().getSimpleName(), "onServiceDisconnected called.");

			}
		};

		bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);

		Log.w(getClass().getSimpleName(), "After binding in Application.onCreate");

		super.onCreate();

	}

	public IAVL_Service getServiceHandle() {
		return serviceHandle;
	}

	public void stopService() {
		stopService(serviceIntent);
	}

	public void startService() {
		startService(serviceIntent);
	}

	public void addNetworkServiceStatusListener(IAVLServiceStatusListener listener) {
		if (listeners.contains(listener) == false) {
			listeners.add(listener);
			Log.w(getClass().getSimpleName(), "Added new INetworkServiceStatusListener.  Count: " + listeners.size());
		}
	}

	public void removeNetworkServiceStatusListener(IAVLServiceStatusListener listener) {
		if (listeners.contains(listener) == true) {
			listeners.remove(listener);
			Log.w(getClass().getSimpleName(), "Removed INetworkServiceStatusListener.  Count: " + listeners.size());
		}
	}

	boolean statusCode;
	public IAVL_Service serviceHandle = null;
	private ArrayList<IAVLServiceStatusListener> listeners;
}