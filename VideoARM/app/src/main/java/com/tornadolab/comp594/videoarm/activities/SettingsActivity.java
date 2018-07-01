/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tornadolab.comp594.videoarm.R;
import com.tornadolab.comp594.videoarm.util.SampleUtil;


public class SettingsActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		RadioButton sdResolution = (RadioButton) findViewById(R.id.sd_resolution);
		RadioButton hdResolution = (RadioButton) findViewById(R.id.hd_resolution);
		RadioButton FhdResolution = (RadioButton) findViewById(R.id.Fhd_resolution);
		RadioButton UhdResolution = (RadioButton) findViewById(R.id.Uhd_resolution);

		sdResolution.setOnClickListener(this);
		hdResolution.setOnClickListener(this);
		FhdResolution.setOnClickListener(this);
		UhdResolution.setOnClickListener(this);

		int resolution = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).getInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);
		switch (resolution) {
			case 0:
				sdResolution.setChecked(true);
				break;

			case 1:
				hdResolution.setChecked(true);
				break;
			case 2:
				FhdResolution.setChecked(true);
				break;
			case 3:
				UhdResolution.setChecked(true);
				break;
		}
	}

	@Override
	public void onClick(View view) {
		Toast.makeText(this, "id:"+view.getId(), Toast.LENGTH_SHORT).show();
		switch (view.getId()) {
			case R.id.sd_resolution:
				SharedPreferences.Editor editor = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).edit();
				editor.putInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);
				editor.apply();
				break;

			case R.id.hd_resolution:
				editor = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).edit();
				editor.putInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 1);
				editor.apply();
				break;

			case R.id.Fhd_resolution:
				editor = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).edit();
				editor.putInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 2);
				editor.apply();
				break;
			case R.id.Uhd_resolution:
				editor = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).edit();
				editor.putInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 3);
				editor.apply();
				break;
		}
	}
}
