/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.imageTracker;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.maxst.ar.TrackerManager;
import com.tornadolab.comp594.videoarm.activities.ARActivity;
import com.tornadolab.comp594.videoarm.R;
import com.tornadolab.comp594.videoarm.util.SampleUtil;


public class ImageTrackerActivity extends ARActivity implements View.OnClickListener {

	private ImageTrackerRenderer imageTargetRenderer;
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

		trackerManager.addTrackerData("ImageTarget/door_far.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/door.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/blue_door_cut.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/cube.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/door_chinese_garden.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/door_cg_2.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/door_cg_3.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/cb_square.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_1.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_2.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_3.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/Hobbiton/MaxstAR\\Hobbiton\\solide_circle_4.2dmap", true);
//		trackerManager.addTrackerData("ImageTarget/matche_box.2dmap", true);
		//black and white colour
		trackerManager.addTrackerData("ImageTarget/matche_box_bw.2dmap", true);

		trackerManager.addTrackerData("ImageTarget/blender_big.2dmap", true);
		trackerManager.addTrackerData("ImageTarget/matchbox_side.2dmap", true);

		trackerManager.addTrackerData("ImageTarget/cap_circle.2dmap", true);
		trackerManager.loadTrackerData();
		trackerManager.setTrackingOption(TrackerManager.TrackingOption.EXTENDED_TRACKING);

		preferCameraResolution = getSharedPreferences(SampleUtil.PREF_NAME, Activity.MODE_PRIVATE).getInt(SampleUtil.PREF_KEY_CAM_RESOLUTION, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		glSurfaceView.onResume();
		trackerManager.startTracker(TrackerManager.TrackerMask.IMAGE_TRACKER);
		cameraDevice.start(0, 1280, 720);
//		switch (preferCameraResolution) {
//			case 0:
//				cameraDevice.start(0, 640, 480);
//				break;
//
//			case 1:
//				cameraDevice.start(0, 1280, 720);
//				break;
//		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.queueEvent(new Runnable() {
			@Override
			public void run() {
				backgroundRenderer.deinitRendering();
				imageTargetRenderer.destroyVideoPlayer();
			}
		});

		glSurfaceView.onPause();

		trackerManager.stopTracker();
		cameraDevice.stop();
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
