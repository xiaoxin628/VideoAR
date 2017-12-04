/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.instantTracker;

import android.app.Activity;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.maxst.ar.SensorDevice;
import com.maxst.ar.TrackerManager;

import com.tornadolab.comp594.videoarm.R;
import com.tornadolab.comp594.videoarm.activities.ARActivity;
import com.tornadolab.comp594.videoarm.util.SampleUtil;

public class InstantTrackerActivity extends ARActivity implements View.OnTouchListener, View.OnClickListener {

	private InstantTrackerRenderer instantImageTargetRenderer;
	private int preferCameraResolution = 0;
	private Button startTrackingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_instant_tracker);

		startTrackingButton = (Button) findViewById(R.id.start_tracking);
		startTrackingButton.setOnClickListener(this);

		glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
		glSurfaceView.setEGLContextClientVersion(2);
		instantImageTargetRenderer = new InstantTrackerRenderer(this);
		glSurfaceView.setRenderer(instantImageTargetRenderer);
		glSurfaceView.setOnTouchListener(this);

		instantImageTargetRenderer.setOrientation(getResources().getConfiguration().orientation);

		preferCameraResolution = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).getInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		glSurfaceView.onResume();
		SensorDevice.getInstance().start();
		trackerManager.startTracker(TrackerManager.TrackerMask.INSTANT_TRACKER);

		switch (preferCameraResolution) {
			case 0:
				cameraDevice.start(0, 640, 480);
				break;

			case 1:
				cameraDevice.start(0, 1280, 720);
				break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		glSurfaceView.queueEvent(new Runnable() {
			@Override
			public void run() {
				backgroundRenderer.deinitRendering();
                instantImageTargetRenderer.destroyVideoPlayer();
			}
		});
		glSurfaceView.onPause();

		trackerManager.stopTracker();
		cameraDevice.stop();
		SensorDevice.getInstance().stop();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		instantImageTargetRenderer.setOrientation(newConfig.orientation);
	}

	private static final float TOUCH_TOLERANCE = 5;
	private float touchStartX;
	private float touchStartY;
	private float translationX;
	private float translationY;

	@Override
	public boolean onTouch(View v, final MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				touchStartX = x;
				touchStartY = y;

				final float[] screen = new float[2];
				screen[0] = x;
				screen[1] = y;

				final float[] world = new float[3];

				TrackerManager.getInstance().getWorldPositionFromScreenCoordinate(screen, world);
				translationX = world[0];
				translationY = world[1];
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				float dx = Math.abs(x - touchStartX);
				float dy = Math.abs(y - touchStartY);
				if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
					touchStartX = x;
					touchStartY = y;

					final float[] screen = new float[2];
					screen[0] = x;
					screen[1] = y;

					final float[] world = new float[3];

					TrackerManager.getInstance().getWorldPositionFromScreenCoordinate(screen, world);
					float posX = world[0];
					float posY = world[1];

					instantImageTargetRenderer.setTranslate(posX - translationX, posY - translationY);
					translationX = posX;
					translationY = posY;
				}
				break;
			}

			case MotionEvent.ACTION_UP:
				break;
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.start_tracking:
				String text = startTrackingButton.getText().toString();
				if (text.equals(getResources().getString(R.string.start_tracking))) {
					TrackerManager.getInstance().findSurface();
					instantImageTargetRenderer.resetPosition();
					startTrackingButton.setText(getResources().getString(R.string.stop_tracking));
				} else {
					TrackerManager.getInstance().quitFindingSurface();
					startTrackingButton.setText(getResources().getString(R.string.start_tracking));
				}
				break;
		}
	}
}
