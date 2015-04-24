/*
 * Copyright 2013-2014 TeamViewer (www.teamviewer.com).  All rights reserved.
 *
 * Please refer to the end user license agreement (EULA), the app developer agreement and license 
 * information associated with this source code for terms and 
 * conditions that govern your use of this software. 
 *
 */

package itcurves.ncs;

import android.app.Activity;
import android.content.res.Resources;

import com.teamviewer.sdk.screensharing.api.TVConfigurationID;
import com.teamviewer.sdk.screensharing.api.TVCreationError;
import com.teamviewer.sdk.screensharing.api.TVSession;
import com.teamviewer.sdk.screensharing.api.TVSessionCallback;
import com.teamviewer.sdk.screensharing.api.TVSessionConfiguration;
import com.teamviewer.sdk.screensharing.api.TVSessionCreationCallback;
import com.teamviewer.sdk.screensharing.api.TVSessionError;
import com.teamviewer.sdk.screensharing.api.TVSessionFactory;

/**
 * Encapsulates the TeamViewer Screen Sharing SDK.
 */
final class ScreenSharingWrapper {

	/**
	 * A valid SDK Token which was created in the
	 * <a href="https://login.teamviewer.com/nav/api/create/mobile">TeamViewer Management Console</a>.
	 */
	private static final String SDK_TOKEN = "21c7ed53-f0a2-1e70-6f62-bc4912c8e075";
	/**
	 * The Configuration ID of a custom QuickSupport module created within the
	 * <a href="https://login.teamviewer.com/nav/menu/designanddeploy">TeamViewer Management Console</a>.
	 */
	private static final String CONFIGURATION_ID = "eeqjn4a";

	private static final ScreenSharingWrapper sInstance = new ScreenSharingWrapper();

	private RunningStateListener mRunningStateListener;
	public static boolean mIsSessionRunning;
	public static TVSession session1;

	public static ScreenSharingWrapper getInstance() {
		return sInstance;
	}

	private ScreenSharingWrapper() {
		// created by the factory method
	}

	/**
	 * @param listener A listener object, or {@code null} to unregister.
	 */
	public void setRunningStateListener(RunningStateListener listener) {
		mRunningStateListener = listener;
	}

	/**
	 * The TeamViewer session will be started asynchronously.
	 * 
	 * @param activity The currently visible activity.
	 */
	public void startTeamViewerSession(Activity activity) {
		Resources resources = activity.getResources();
		String serviceCaseName = "Screen Share";
		String serviceCaseDescription = "Screen Sharing Request";

		try {
			TVSessionConfiguration configuration = createConfiguration(serviceCaseName, serviceCaseDescription);
			TVSessionFactory.createTVSession(activity, SDK_TOKEN, createSessionCreationCallback(this, configuration));
		} catch (IllegalArgumentException e) {
			// either the configuration, or the parameters of the factory are invalid
			// no error handling wanted
		} catch (IllegalStateException e) {
			// the app's manifest is missing permissions or the service declaration
			// no error handling wanted
		}
	}

	public boolean isSessionRunning() {
		return mIsSessionRunning;
	}

	/**
	 * Will be called by the session callbacks on changes to the running state
	 * of the TeamViewer session.
	 * 
	 * @param isRunning {@code true} if the session is now running, {@code false} otherwise.
	 */
	private void setSessionRunning(boolean isRunning) {
		mIsSessionRunning = isRunning;
		RunningStateListener listener = mRunningStateListener;
		if (listener != null) {
			listener.onRunningStateChange(isRunning);
		}
	}

	/**
	 * Creates the configuration used for starting a TeamViewer session.
	 * 
	 * @param serviceCaseName Name of the newly generated service case.
	 * @param serviceCaseDescription Description of the newly generated service case.
	 * @return A new session configuration.
	 * @throws IllegalArgumentException If the provided configuration id is {@code null}.
	 */
	private static TVSessionConfiguration createConfiguration(String serviceCaseName, String serviceCaseDescription) throws IllegalArgumentException {
		TVConfigurationID configurationID = new TVConfigurationID(CONFIGURATION_ID);
		return new TVSessionConfiguration.Builder(configurationID).setServiceCaseName(serviceCaseName).setServiceCaseDescription(serviceCaseDescription).build();
	}

	/**
	 * Creates a {@link com.teamviewer.sdk.screensharing.api.TVSessionCreationCallback} for starting the TeamViewer session.
	 * 
	 * @param wrapper Will be notified about a successful start of the session.
	 * @param configuration Configuration options for starting the session.
	 * @return Callback for receiving the result of the session's creation.
	 */
	private static TVSessionCreationCallback createSessionCreationCallback(final ScreenSharingWrapper wrapper, final TVSessionConfiguration configuration) {
		return new TVSessionCreationCallback() {
			@Override
			public void onTVSessionCreationSuccess(TVSession session) {
				session.setTVSessionCallback(createSessionCallback(wrapper));
				session.start(configuration);
				session1 = session;
				wrapper.setSessionRunning(true);
				TaxiPlexer.tvsessioncheck = true;
			}

			@Override
			public void onTVSessionCreationFailed(TVCreationError error) {
				// no error handling wanted
			}
		};
	}

	/**
	 * Creates a {@link com.teamviewer.sdk.screensharing.api.TVSessionCallback} for receiving the session end.
	 * 
	 * @param wrapper Will be notified about the session end.
	 * @return A new {@code TVSessionCallback} object.
	 */
	private static TVSessionCallback createSessionCallback(final ScreenSharingWrapper wrapper) {
		return new TVSessionCallback() {
			@Override
			public void onTVSessionEnd() {
				wrapper.setSessionRunning(false);
				TaxiPlexer.tvsessioncheck = false;
			}

			@Override
			public void onTVSessionError(TVSessionError error) {
				// no error handling wanted
			}
		};
	}

	/**
	 * Callback to be invoked when the TeamViewer session is started or has
	 * ended.
	 */
	public interface RunningStateListener {
		/**
		 * Will be called on running state changes of the TeamViewer session.
		 * 
		 * @param isRunning New session state.
		 */
		void onRunningStateChange(boolean isRunning);
	}
}
