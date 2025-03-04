package com.ch11;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity {
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		FragmentTransaction ft = 
				getFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, 
				new MyPreferenceFragment()).commit();
	}
	public class MyPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference);

			// 監聽[安全模式]
			Preference pref = findPreference("apply_safemode");
			pref.setOnPreferenceChangeListener(
					new OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference p,
						Object newValue) {
					Toast.makeText(context,
							p.getKey() + ":" + newValue,
							Toast.LENGTH_SHORT).show();
					return true;
				}
			});
		}
	}
}
