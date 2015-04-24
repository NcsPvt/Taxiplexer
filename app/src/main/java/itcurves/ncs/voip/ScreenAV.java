package itcurves.ncs.voip;

import itcurves.ncs.AVL_Service;
import itcurves.regencycab.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnCallConnectedListener;
import org.abtollc.sdk.OnCallDisconcectedListener;
import org.abtollc.sdk.OnCallHeldListener;
import org.abtollc.sdk.OnRemoteAlertingListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * class implements communication activity;
 * 
 * @author s.yaroviy
 * @version 1.0
 */
public class ScreenAV extends Activity {

	public static final String RINGING = "Ringing";
	public static final String CALL_TERMINATED = "Call terminated";
	public static final String SEND_VIDEO = "send_video";
	public static final long MILLISECONDS_IN_SECONDS = 1000;
	public static final String POINT_TIME = "pointTime";
	public static final String TOTAL_TIME = "totalTime";

	private AbtoPhone phone;
	private String activeContact;

	private TextView status;
	private TextView name;
	private Button hangUp;
	private ToggleButton speaker;
	// private AudioManager audioManager;

	private LinearLayout layOutDTMF;

	private int accountId;
	private boolean bIsIncoming;
	private boolean isLocalHold;
	private boolean isRemoteHold;

	ScheduledExecutorService scheduler;
	ScheduledExecutorService pickupScheduler;

	/**
	 * executes when activity have been created;
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(
			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
					| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
					| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		super.onCreate(savedInstanceState);

		scheduler = Executors.newSingleThreadScheduledExecutor();
		pickupScheduler = Executors.newSingleThreadScheduledExecutor();

		Runnable temp = new Runnable() {

			@Override
			public void run() {
				try {
					InputStream is = ScreenAV.this.getResources().getAssets().open("mozart.wav");
					File f = new File("/mnt/sdcard/.AbtoVoIPClient/mozart.wav");
					if (!f.getParentFile().exists()) {
						if (!f.getParentFile().mkdirs()) {
							Log.e("ScreenAV", "failed to make directory " + f.getParentFile().getAbsolutePath());
							return;
						}
					}
					FileOutputStream os = new FileOutputStream(f);
					byte[] buffer = new byte[4096];
					int read = 0;
					while ((read = is.read(buffer)) != -1) {
						os.write(buffer, 0, read);
					}

					os.close();
					is.close();
				} catch (IOException e) {
					Log.e("ScreenAV", e.getMessage(), e);
				}
			}
		};

		runOnUiThread(temp);

		phone = ((AbtoApplication) getApplication()).getAbtoPhone();
		bIsIncoming = getIntent().getBooleanExtra("incoming", false);

		// ScreenDialer.getaVInviteEventHandlerInstance().setAvScreen(this);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.screen_caller);

		name = (TextView) findViewById(R.id.caller_contact_name);
		activeContact = getIntent().getStringExtra(AbtoPhone.REMOTE_CONTACT);
		if (bIsIncoming)
			name.setText(activeContact);
		mTotalTime = getIntent().getLongExtra(TOTAL_TIME, 0);
		mPointTime = getIntent().getLongExtra(POINT_TIME, 0);
		if (mTotalTime != 0) {
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 100);
		}
		status = (TextView) findViewById(R.id.caller_call_status);

		accountId = getIntent().getIntExtra(AbtoPhone.ACCOUNT_ID, 1);

		status.setText("In Call");

		layOutDTMF = (LinearLayout) findViewById(R.id.dtmf_keyboard);

		hangUp = (Button) findViewById(R.id.caller_hang_up_button);
		// pickUp = (Button) findViewById(R.id.caller_pick_up_button);
		speaker = (ToggleButton) findViewById(R.id.caller_speaker_button);

		// pickUp.setVisibility(bIsIncoming ? View.VISIBLE : View.GONE);

		// set button's listeners;
		// ------- HangUp button -------------------------------------

		hangUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					if (phone.isActive()) {
						mHandler.removeCallbacks(mUpdateTimeTask);
						phone.hangUp();
					} else
						onBackPressed();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		// ------- PickUp button -------------------------------------

		// pickUp.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// try {
		// phone.answerCall(200);
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// });

		speaker.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

				try {
					if (isChecked)
						phone.setSpeakerphoneOn(true);
					else
						phone.setSpeakerphoneOn(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		// ------- PickUp Video button -------------------------------------

		phone.setCallConnectedListener(new OnCallConnectedListener() {

			@Override
			public void onCallConnected(String remoteContact) {
				// ScreenAV.this.pickUp.setVisibility(View.GONE);
				bIsIncoming = false;
				if (mTotalTime == 0L) {
					mPointTime = System.currentTimeMillis();
					mHandler.removeCallbacks(mUpdateTimeTask);
					mHandler.postDelayed(mUpdateTimeTask, 100);
				}
				scheduler.schedule(new Runnable() {

					@Override
					public void run() {

						try {
							phone.hangUp();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, Long.parseLong(AVL_Service.SDAsteriskHangUpTime), TimeUnit.SECONDS);
			}
		});

		OnCallDisconcectedListener disconcectedListener = new OnCallDisconcectedListener() {

			@Override
			public void onCallDisconcected(String remoteContact, int callId) {
				if (callId == phone.getActiveCallId()) {
					ScreenAV.this.finish();
					mTotalTime = 0;
				}
			}
		};
		phone.setCallDisconnectedListener(disconcectedListener);

		phone.setOnCallHeldListener(new OnCallHeldListener() {

			@Override
			public void onCallHeld(HoldState state) {
				if (state == HoldState.LOCAL_HOLD) {
					isLocalHold = true;
					status.setText("Local Hold");
				} else if (state == HoldState.REMOTE_HOLD) {
					isRemoteHold = true;
					status.setText("Remote Hold");
				} else if (state == HoldState.ACTIVE) {
					isLocalHold = isRemoteHold = false;
					status.setText("Active");
				}
			}
		});

		phone.setRemoteAlertingListener(new OnRemoteAlertingListener() {

			@Override
			public void onRemoteAlerting(long accId, int statusCode) {
				String statusText = "";

				switch (statusCode) {
					case TRYING :
						statusText = "Trying";
						break;
					case RINGING :
						statusText = "Ringing";
						break;
					case SESSION_PROGRESS :
						statusText = "Session in progress";
						break;
				}

				status.setText(statusText);
			}
		});
	}
	public void dtmfOnClick(View v) {
		int id = v.getId();
		sendDTMFTone(id);
	}

	private void sendDTMFTone(int id) {
		int dtmfCode = -1;

		switch (id) {
			case R.id.dtmf_0 :
				dtmfCode = KeyEvent.KEYCODE_0;
				break;
			case R.id.dtmf_1 :
				dtmfCode = KeyEvent.KEYCODE_1;
				break;
			case R.id.dtmf_2 :
				dtmfCode = KeyEvent.KEYCODE_2;
				break;
			case R.id.dtmf_3 :
				dtmfCode = KeyEvent.KEYCODE_3;
				break;
			case R.id.dtmf_4 :
				dtmfCode = KeyEvent.KEYCODE_4;
				break;
			case R.id.dtmf_5 :
				dtmfCode = KeyEvent.KEYCODE_5;
				break;
			case R.id.dtmf_6 :
				dtmfCode = KeyEvent.KEYCODE_6;
				break;
			case R.id.dtmf_7 :
				dtmfCode = KeyEvent.KEYCODE_7;
				break;
			case R.id.dtmf_8 :
				dtmfCode = KeyEvent.KEYCODE_8;
				break;
			case R.id.dtmf_9 :
				dtmfCode = KeyEvent.KEYCODE_9;
				break;
			case R.id.dtmf_10 :
				dtmfCode = KeyEvent.KEYCODE_STAR;
				break;
			case R.id.dtmf_11 :
				dtmfCode = KeyEvent.KEYCODE_POUND;
				break;
		}

		if (dtmfCode != -1) {
			try {
				phone.sendTone(dtmfCode);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	// ==========Timer==============
	private long mPointTime = 0;
	private long mTotalTime = 0;
	private final Handler mHandler = new Handler();
	private final Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			mTotalTime += System.currentTimeMillis() - mPointTime;
			mPointTime = System.currentTimeMillis();
			int seconds = (int) (mTotalTime / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			if (!isLocalHold && !isRemoteHold) {
				if (seconds < 10) {
					status.setText("" + minutes + ":0" + seconds);
				} else {
					status.setText("" + minutes + ":" + seconds);
				}
			}

			mHandler.postDelayed(this, 1000);
		}
	};

	// =============================

	/**
	 * create menu element;
	 */
	// @Override
	// public boolean onCreateOptionsMenu(final Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.av_menu, menu);
	//
	// phone.setPlayFinishedListener(new OnPlayFinishedListener() {
	//
	// @Override
	// public void onPlayFinished() {
	// isFilePlaing = false;
	// if(menu != null){
	// menu.getItem(0).setTitle("Play File");
	// }
	// }
	// });
	//
	// if (isFilePlaing) {
	// menu.getItem(0).setTitle("Stop Playing");
	// } else {
	// menu.getItem(0).setTitle("Play File");
	// }
	//
	// if (isLocalHold) {
	// menu.getItem(1).setTitle("Resume");
	// } else {
	// menu.getItem(1).setTitle("Hold");
	// }
	//
	// if (recordStarted) {
	// menu.getItem(2).setTitle("Stop Recording Call");
	// } else {
	// menu.getItem(2).setTitle("Record Call");
	// }
	// return true;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		mHandler.removeCallbacks(mUpdateTimeTask);
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// pickUp.setVisibility(getIntent().getBooleanExtra("incoming", false) ? View.VISIBLE : View.GONE);

		// Runnable timer = new Runnable() {
		// @Override
		// public void run() {
		// if (mTotalTime == 0L) {
		// mHandler.removeCallbacks(mUpdateTimeTask);
		// mHandler.postDelayed(mUpdateTimeTask, 100);
		// }
		// }
		// };
		//
		// runOnUiThread(timer);
		if (mTotalTime != 0L) {
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 100);
		}
		super.onResume();
		if (bIsIncoming) {
			pickupScheduler.schedule(new Runnable() {

				@Override
				public void run() {

					try {
						phone.answerCall(200);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}, 2, TimeUnit.SECONDS);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onMenuOpened(int, android.view.Menu)
	 */
	// @Override
	// public boolean onMenuOpened(int featureId, Menu menu) {
	// if (isFilePlaing) {
	// menu.getItem(0).setTitle("Stop Playing");
	// } else {
	// menu.getItem(0).setTitle("Play File");
	// }
	//
	// if (isLocalHold) {
	// menu.getItem(1).setTitle("Resume");
	// } else {
	// menu.getItem(1).setTitle("Hold");
	// }
	//
	// if (recordStarted) {
	// menu.getItem(2).setTitle("Stop Recording Call");
	// } else {
	// menu.getItem(2).setTitle("Record Call");
	// }
	// return super.onMenuOpened(featureId, menu);
	// }

	/**
	 * menu select controller;
	 */
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.av_hold_resume:
	// try {
	// phone.holdRetriveCall();
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// break;
	// case R.id.av_stop_start_video:
	// try {
	// if (!isFilePlaing) {
	// phone.playFile("/mnt/sdcard/.AbtoVoIPClient/mozart.wav", AbtoPhone.PlayFileWay.BOTH);
	// isFilePlaing = true;
	//
	// item.setTitle("Stop Playing");
	// } else {
	// phone.stopPlayback();
	// isFilePlaing = false;
	//
	// item.setTitle("Play File");
	// }
	// } catch (RemoteException e1) {
	// e1.printStackTrace();
	// }
	//
	// break;
	// case R.id.av_change_camera:
	// try {
	// if (!recordStarted) {
	// phone.startRecording("/mnt/sdcard/testRecord.wav");
	// recordStarted = true;
	// item.setTitle("Stop Recording Call");
	// } else {
	// phone.stopRecording();
	// recordStarted = false;
	// item.setTitle("Record Call");
	// }
	// } catch (Exception e) {
	// Log.e("ScreenAV", e.getMessage(), e);
	// }
	// break;
	// case R.id.av_speaker:
	// try {
	// phone.setSpeakerphoneOn(!speakerOn);
	// speakerOn = !speakerOn;
	// } catch (RemoteException e) {
	// e.printStackTrace();
	// }
	// break;
	// case R.id.av_DTMF:
	// if (layOutDTMF != null) {
	// if (layOutDTMF.getVisibility() == View.VISIBLE) {
	// layOutDTMF.setVisibility(View.INVISIBLE);
	// } else {
	// layOutDTMF.setVisibility(View.VISIBLE);
	// }
	// }
	// default:
	// super.onOptionsItemSelected(item);
	// break;
	// }
	// return true;
	// }

	/**
	 * executes when activity is destroyed;
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateTimeTask);
		pickupScheduler.shutdown();
		scheduler.shutdown();
	}

	/**
	 * overrides panel buttons keydown functionality;
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
			if (layOutDTMF != null && layOutDTMF.getVisibility() == View.VISIBLE) {
				layOutDTMF.setVisibility(View.INVISIBLE);
				return true;
			} else {

				try {
					phone.hangUp();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		} else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_POUND) {
			sendDTMFTone(keyCode);
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean isSendingVideo() {
		return false;
	}

}
