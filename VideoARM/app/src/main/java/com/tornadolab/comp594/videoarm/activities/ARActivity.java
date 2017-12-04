/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;

import com.maxst.ar.BackgroundRenderer;
import com.maxst.ar.CameraDevice;
import com.maxst.ar.MaxstAR;
import com.maxst.ar.TrackerManager;
import com.tornadolab.comp594.videoarm.R;

public abstract class ARActivity extends Activity {

	protected GLSurfaceView glSurfaceView;
	protected TrackerManager trackerManager;
	protected BackgroundRenderer backgroundRenderer;
	protected CameraDevice cameraDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MaxstAR.init(getApplicationContext(), getResources().getString(R.string.app_key));

		trackerManager = TrackerManager.getInstance();
		backgroundRenderer = BackgroundRenderer.getInstance();
		cameraDevice = CameraDevice.getInstance();

		backgroundRenderer.setScreenOrientation(getResources().getConfiguration().orientation);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		trackerManager.destroyTracker();
		MaxstAR.deinit();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}

		backgroundRenderer.setScreenOrientation(newConfig.orientation);
	}
}
