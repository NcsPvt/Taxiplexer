/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package itcurves.ncs;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import itcurves.regencycab.R;
/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class DeviceListActivity extends Activity {

	String title = "select a device to connect";

	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_DEVICE_NAME = "device_name";

	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private static int counter = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title = this.getIntent().getStringExtra("Title");

		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		setTitle(title);

		// Set result CANCELED in case the user backs out
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});

		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

		// Find and set up the ListView for paired devices
		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		if (AVL_Service.pref.getBoolean("VeriFoneDevice", false) && TaxiPlexer.VMeterAddress != null) {
			findViewById(R.id.title_paired_devices).setVisibility(View.GONE);
			findViewById(R.id.paired_devices).setVisibility(View.GONE);
			findViewById(R.id.button_scan).setVisibility(View.GONE);
			doDiscovery();
		} else {

			// If there are paired devices, add each one to the ArrayAdapter
			if (pairedDevices.size() > 0) {
				findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
				for (BluetoothDevice device : pairedDevices) {
					// if (!AVL_Service.pref.getBoolean("VeriFoneDevice", true)) {
					mPairedDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
					// } else {
					// if (getDeviceName(device.getName()).contains("firefly"))
					// mPairedDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
					// }
				}
			} else {
				String noDevices = "No devices have been paired";
				mPairedDevicesArrayAdapter.add(noDevices);
			}
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		TaxiPlexer.SHOW_PAIRED_DEVICES_DIALOG = true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {
		if (D)
			Log.d(TAG, "doDiscovery()");

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle("scanning for devices...");

		// Turn on sub-title for new devices
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		if (AVL_Service.pref.getBoolean("VeriFoneDevice", false) && TaxiPlexer.VMeterAddress != null) {
			findViewById(R.id.title_new_devices).setVisibility(View.GONE);
			findViewById(R.id.new_devices).setVisibility(View.GONE);
			setTitle("Searching...");
		}

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	// The on-click listener for all devices in the ListViews
	private final OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			// Cancel discovery because it's costly and we're about to connect
			mBtAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the View
			String info = ((TextView) v).getText().toString();
			String name = info.substring(0, info.length() - 17);
			String address = info.substring(info.length() - 17);

			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
			intent.putExtra(EXTRA_DEVICE_NAME, name);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			// To Show Dialog Next Time
			TaxiPlexer.SHOW_PAIRED_DEVICES_DIALOG = true;
			finish();
		}
	};

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed already
				if (TaxiPlexer.VMeterAddress == null || TaxiPlexer.VMeterAddress.equalsIgnoreCase("")) {
					if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
						// if (!AVL_Service.pref.getBoolean("VeriFoneDevice", true)) {
						mNewDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
						// } else {
						// if (getDeviceName(device.getName()).contains("firefly"))
						// mNewDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
						// }
					}
				} else {
					if (device.getAddress().equalsIgnoreCase(TaxiPlexer.VMeterAddress)) {
						mNewDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
						findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
						findViewById(R.id.new_devices).setVisibility(View.VISIBLE);
					}
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(title);
				if (TaxiPlexer.VMeterAddress != null && mNewDevicesArrayAdapter.getPosition(TaxiPlexer.VMeterAddress) <= 0) {
					if (mNewDevicesArrayAdapter.getCount() == 0) {
						TaxiPlexer.SHOW_PAIRED_DEVICES_DIALOG = true;
						DeviceListActivity.this.finish();
					}
				} else if (AVL_Service.pref.getBoolean("VeriFoneDevice", false) && TaxiPlexer.VMeterAddress == null) {
					findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
					findViewById(R.id.paired_devices).setVisibility(View.VISIBLE);
					findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
					findViewById(R.id.new_devices).setVisibility(View.VISIBLE);
				} else {
					if (mNewDevicesArrayAdapter.getCount() == 0) {
						String noDevices = "No devices found";
						mNewDevicesArrayAdapter.add(noDevices);
						counter = 0;

					}
				}

			}
		}
	};

	/*-----------------------------------------------------------------getDeviceName-----------------------------------------------------------------*/
	private String getDeviceName(String name) {
		if (name != null)
			if (name.contains("P25"))
				return "BlueBambooPrinter";
			else if (name.contains("Amp'ed"))
				return "VivoTech 8800";
			else if (name.contains("eb101"))
				return "Taxi Meter";
			else if (name.contains("Regency"))
				return "Taxi Meter";

		// else
		// if (name.trim().length() == 4)
		// try {
		// if (Integer.parseInt(name) > 0)
		// return "Taxi Meter";
		// } catch (NumberFormatException nfe) {
		// return name;
		// }
		return name;
	}

}
