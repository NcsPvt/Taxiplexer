package itcurves.ncs;

import itcurves.regencycab.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SwiperPreferences extends PreferenceActivity implements OnPreferenceClickListener {
	public static final String STORE_NAME = "SwiperAPISettings";
	private CheckBoxPreference setDetectDeviceChangePref;
	private CheckBoxPreference setFskRequiredPref;
	private ListPreference setTimeoutlistPref;
	private ListPreference setKsnChargeUplistPref;
	private ListPreference setSwipeChargeUplistPref;
	private Preference sdkVersionPref;
	private Preference firmwareVersionPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		setDetectDeviceChangePref = (CheckBoxPreference) findPreference("setDetectDeviceChangePref");
		setFskRequiredPref = (CheckBoxPreference) findPreference("setFskRequiredPref");
		setTimeoutlistPref = (ListPreference) findPreference("setTimeoutlistPref");
		setKsnChargeUplistPref = (ListPreference) findPreference("setKsnChargeUplistPref");
		setSwipeChargeUplistPref = (ListPreference) findPreference("setSwipeChargeUplistPref");
		sdkVersionPref = findPreference("apiVersionPref");
		firmwareVersionPref = findPreference("firmwareVersionPref");

		setDetectDeviceChangePref.setOnPreferenceClickListener(this);
		setFskRequiredPref.setOnPreferenceClickListener(this);
		setTimeoutlistPref.setOnPreferenceClickListener(this);
		setKsnChargeUplistPref.setOnPreferenceClickListener(this);
		setSwipeChargeUplistPref.setOnPreferenceClickListener(this);
		firmwareVersionPref.setOnPreferenceClickListener(this);

		SharedPreferences customSharedPreference = getSharedPreferences(STORE_NAME, Activity.MODE_PRIVATE);
		sdkVersionPref.setSummary(customSharedPreference.getString("apiVersionPref", ""));
		firmwareVersionPref.setSummary(customSharedPreference.getString("firmwareVersionPref", ""));
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		SharedPreferences customSharedPreference = getSharedPreferences(STORE_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = customSharedPreference.edit();

		if (preference.getKey().equals("setDetectDeviceChangePref")) {
			editor.putBoolean("setDetectDeviceChangePref", setDetectDeviceChangePref.isChecked());
			editor.commit();
			return true;
		} else if (preference.getKey().equals("setFskRequiredPref")) {
			editor.putBoolean("setFskRequiredPref", setFskRequiredPref.isChecked());
			editor.commit();
			return true;
		} else if (preference.getKey().equals("setTimeoutlistPref")) {
			editor.putString("setTimeoutlistPref", setTimeoutlistPref.getValue());
			editor.commit();
			return true;
		} else if (preference.getKey().equals("setKsnChargeUplistPref")) {
			editor.putString("setKsnChargeUplistPref", setKsnChargeUplistPref.getValue());
			editor.commit();
			return true;
		} else if (preference.getKey().equals("setSwipeChargeUplistPref")) {
			editor.putString("setSwipeChargeUplistPref", setSwipeChargeUplistPref.getValue());
			editor.commit();
			return true;
		}

		return false;
	}
}