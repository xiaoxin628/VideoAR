/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.imageTracker;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.maxst.ar.CameraDevice;
import com.maxst.ar.MaxstAR;
import com.maxst.ar.ResultCode;
import com.maxst.ar.TrackerManager;
import com.tornadolab.comp594.videoarm.activities.ARActivity;
import com.tornadolab.comp594.videoarm.R;
import com.tornadolab.comp594.videoarm.util.SampleUtil;


public class ImageTrackerActivity extends ARActivity implements View.OnClickListener {

	private ImageTrackerRenderer imageTargetRenderer;
	private GLSurfaceView glSurfaceView;
	private int preferCameraResolution = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_tracker);

//		findViewById(R.id.normal_tracking).setOnClickListener(this);
//		findViewById(R.id.extended_tracking).setOnClickListener(this);
//		findViewById(R.id.multi_tracking).setOnClickListener(this);

		glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
		glSurfaceView.setEGLContextClientVersion(2);
		imageTargetRenderer = new ImageTrackerRenderer(this);
		glSurfaceView.setRenderer(imageTargetRenderer);

		TrackerManager.getInstance().addTrackerData("ImageTarget/door_far.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/door.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/blue_door_cut.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/cube.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/door_chinese_garden.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/door_cg_2.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/door_cg_3.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/cb_square.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_1.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_2.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_3.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_4.2dmap", true);
//		trackerManager.addTrackerData("ImageTarget/matche_box.2dmap", true);
		//black and white colour
		TrackerManager.getInstance().addTrackerData("ImageTarget/matche_box_bw.2dmap", true);

		TrackerManager.getInstance().addTrackerData("ImageTarget/blender_big.2dmap", true);
		TrackerManager.getInstance().addTrackerData("ImageTarget/matchbox_side.2dmap", true);

		TrackerManager.getInstance().addTrackerData("ImageTarget/cap_circle.2dmap", true);
		TrackerManager.getInstance().loadTrackerData();
		TrackerManager.getInstance().setTrackingOption(TrackerManager.TrackingOption.EXTENDED_TRACKING);

		preferCameraResolution = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).getInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		glSurfaceView.onResume();
		TrackerManager.getInstance().startTracker(TrackerManager.TRACKER_TYPE_IMAGE);

		ResultCode resultCode = ResultCode.Success;
		switch (preferCameraResolution) {
			case 0:
				resultCode = CameraDevice.getInstance().start(0, 640, 480);
				break;

			case 1:
				resultCode = CameraDevice.getInstance().start(0, 1280, 720);
				break;

			case 2:
				resultCode = CameraDevice.getInstance().start(0, 1920, 1080);
				break;
		}

		if (resultCode != ResultCode.Success) {
			Toast.makeText(this, R.string.camera_open_fail, Toast.LENGTH_SHORT).show();
			finish();
		}

		MaxstAR.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		glSurfaceView.queueEvent(new Runnable() {
			@Override
			public void run() {
				imageTargetRenderer.destroyVideoPlayer();
			}
		});

		glSurfaceView.onPause();

		TrackerManager.getInstance().stopTracker();
		CameraDevice.getInstance().stop();
		MaxstAR.onPause();
	}

	@Override
	public void onClick(View view) {
//		switch (view.getId()) {
//			case R.id.normal_tracking:
//				trackerManager.setTrackingOption(TrackerManager.TrackingOption.NORMAL_TRACKING);
//				break;
//			case R.id.extended_tracking:
//				trackerManager.setTrackingOption(TrackerManager.TrackingOption.EXTENDED_TRACKING);
//				break;
//			case R.id.multi_tracking:
//				trackerManager.setTrackingOption(TrackerManager.TrackingOption.MULTI_TRACKING);
//				break;
//		}
	}
}
