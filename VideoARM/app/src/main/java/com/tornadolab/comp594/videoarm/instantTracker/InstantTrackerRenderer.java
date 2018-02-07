/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.instantTracker;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import com.maxst.ar.BackgroundRenderer;
import com.maxst.ar.MaxstARUtil;
import com.maxst.ar.Trackable;
import com.maxst.ar.TrackerManager;
import com.maxst.ar.TrackingResult;
import com.maxst.videoplayer.VideoPlayer;
import com.tornadolab.comp594.videoarm.arobject.ChromaKeyVideoQuad;
import com.tornadolab.comp594.videoarm.arobject.PlaneShaperVideoQuad;
import com.tornadolab.comp594.videoarm.arobject.TexturedCube;
import com.tornadolab.comp594.videoarm.arobject.VideoQuad;
import com.tornadolab.comp594.videoarm.util.SampleRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class InstantTrackerRenderer implements Renderer {

	public static final String TAG = InstantTrackerRenderer.class.getSimpleName();

	private int surfaceWidth;
	private int surfaceHeight;
	private SampleRenderer sampleRenderer;

	private PlaneShaperVideoQuad mVideoQuad;
	private int orientation;
	private float posX;
	private float posY;
	private Activity activity;

	InstantTrackerRenderer(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);

		TrackingResult trackingResult = TrackerManager.getInstance().getTrackingResult();

		sampleRenderer.onDrawFrame();

		if (trackingResult.getCount() == 0) {
			return;
		}

		float [] projectionMatrix = BackgroundRenderer.getInstance().getProjectionMatrix();

		Trackable trackable = trackingResult.getTrackable(0);

		GLES20.glEnable(GLES20.GL_DEPTH_TEST);



		if (mVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
				mVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
			mVideoQuad.getVideoPlayer().start();
		}
		mVideoQuad.setProjectionMatrix(projectionMatrix);
		mVideoQuad.setTransform(trackable.getPoseMatrix());
		mVideoQuad.setTranslate(posX, posY-0.6f, 0.0f);
		mVideoQuad.setScale(
				5.0f,
				-0.56f,
				5.0f
		);
		mVideoQuad.draw();
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		sampleRenderer.onSurfaceChanged(width, height);

		surfaceWidth = width;
		surfaceHeight = height;

//


			mVideoQuad.setRotation(90, 0, 0, 1);
//        mVideoQuad.setRotation(90, 1, 1, -0.5f);
//		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//			mVideoQuad.setRotation(-90, 0, 0, 1);
//		} else {
//			mVideoQuad.setRotation(0, 1, 1, 1);
//		}
		mVideoQuad.setScale(
                5.0f,
                -0.56f,
                5.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		sampleRenderer = new SampleRenderer();
		sampleRenderer.onSurfaceCreated();


		mVideoQuad = new PlaneShaperVideoQuad();
		VideoPlayer player = new VideoPlayer(activity);
		mVideoQuad.setVideoPlayer(player);
//		player.openVideo("movies/stitch_walk.mp4");
//		player.openVideo("movies/IMG_0106.mp4");
		player.openVideo("movies/IMG_0105.mp4");
	}

	void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	void setTranslate(float x, float y) {
		posX += x;
		posY += y;
	}

	void resetPosition() {
		posX = 0;
		posY = 0;
	}

     void destroyVideoPlayer() {
        mVideoQuad.getVideoPlayer().destroy();
    }
}
