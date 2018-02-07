/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */
package com.tornadolab.comp594.videoarm.imageTracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.maxst.ar.BackgroundRenderer;
import com.maxst.ar.MaxstARUtil;
import com.maxst.ar.Trackable;
import com.maxst.ar.TrackerManager;
import com.maxst.ar.TrackingResult;
import com.tornadolab.comp594.videoarm.arobject.ChromaKeyVideoQuad;
import com.tornadolab.comp594.videoarm.arobject.ColoredCube;
import com.tornadolab.comp594.videoarm.arobject.CubeVideoQuad;
import com.tornadolab.comp594.videoarm.arobject.ShaperVideoQuad;
import com.tornadolab.comp594.videoarm.arobject.TexturedCube;
import com.tornadolab.comp594.videoarm.arobject.VideoQuad;
import com.tornadolab.comp594.videoarm.util.SampleRenderer;
import com.maxst.videoplayer.VideoPlayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class ImageTrackerRenderer implements Renderer {

	public static final String TAG = ImageTrackerRenderer.class.getSimpleName();

	private TexturedCube texturedCube;
	private ColoredCube coloredCube;
	private VideoQuad videoQuad;
	private CubeVideoQuad mCubeVideoQuad;
    private ShaperVideoQuad mShaperVideoQuad;
	private ChromaKeyVideoQuad chromaKeyVideoQuad;

	private int surfaceWidth;
	private int surfaceHeight;
	private SampleRenderer sampleRenderer;

	private final Activity activity;

	ImageTrackerRenderer(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		sampleRenderer = new SampleRenderer();
		sampleRenderer.onSurfaceCreated();

//		Bitmap bitmap = MaxstARUtil.getBitmapFromAsset("MaxstAR_Cube.png", activity.getAssets());
//
//		texturedCube = new TexturedCube();
//		texturedCube.setTextureBitmap(bitmap);
//
//		coloredCube = new ColoredCube();

		videoQuad = new VideoQuad();
		VideoPlayer player = new VideoPlayer(activity);
		videoQuad.setVideoPlayer(player);
		player.openVideo("movies/bag_end.mp4");


		mShaperVideoQuad = new ShaperVideoQuad();
		player = new VideoPlayer(activity);
		mShaperVideoQuad.setVideoPlayer(player);
		player.openVideo("movies/bag_end.mp4");

        mCubeVideoQuad = new CubeVideoQuad();
        player = new VideoPlayer(activity);
        mCubeVideoQuad.setVideoPlayer(player);
        player.openVideo("movies/bag_end.mp4");

		chromaKeyVideoQuad = new ChromaKeyVideoQuad();
		player = new VideoPlayer(activity);
		chromaKeyVideoQuad.setVideoPlayer(player);
		player.openVideo("movies/processed_video.mp4");
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		sampleRenderer.onSurfaceChanged(width, height);

		surfaceWidth = width;
		surfaceHeight = height;
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		GLES20.glViewport(0, 0, surfaceWidth, surfaceHeight);

		TrackingResult trackingResult = TrackerManager.getInstance().getTrackingResult();

		sampleRenderer.onDrawFrame();

		boolean legoDetected = false;
		boolean blocksDetected = false;

		float[] projectionMatrix = BackgroundRenderer.getInstance().getProjectionMatrix();

		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		for (int i = 0; i < trackingResult.getCount(); i++) {
			Trackable trackable = trackingResult.getTrackable(i);

//			Log.d(TAG, trackable.getName());

			if (trackable.getName().equals("door_far") ) {
				legoDetected = true;
				if (videoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
						videoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
					videoQuad.getVideoPlayer().start();
				}
				videoQuad.setProjectionMatrix(projectionMatrix);
				videoQuad.setTransform(trackable.getPoseMatrix());
				videoQuad.setTranslate(0.0f, 0.0f, 0.0f);
				videoQuad.setScale(
						1.0f,
						-0.560f,
						1.0f
				);
				videoQuad.draw();
			} else if (trackable.getName().startsWith("solide_circle")) {

				legoDetected = true;
				if (mShaperVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
						mShaperVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
					mShaperVideoQuad.getVideoPlayer().start();
				}
				mShaperVideoQuad.setProjectionMatrix(projectionMatrix);
				mShaperVideoQuad.setTransform(trackable.getPoseMatrix());
				mShaperVideoQuad.setTranslate(0.0f, 0.0f, 0.0f);
				mShaperVideoQuad.setScale(
						1.0f,
						-0.560f,
						1.0f
				);
				mShaperVideoQuad.draw();

			} else if (trackable.getName().equals("cb_square")) {

                legoDetected = true;
                if (mCubeVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
                        mCubeVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
                    mCubeVideoQuad.getVideoPlayer().start();
                }
                mCubeVideoQuad.setProjectionMatrix(projectionMatrix);
                mCubeVideoQuad.setTransform(trackable.getPoseMatrix());
                mCubeVideoQuad.setTranslate(0.0f, 0.0f, 0.0f);
                mCubeVideoQuad.setScale(
                        1.0f,
                        -0.560f,
                        1.0f
                );
                mCubeVideoQuad.draw();

            }

			else if (trackable.getName().equals("blue_door_far")) {
				blocksDetected = true;
				if (chromaKeyVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_READY ||
						chromaKeyVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PAUSE) {
					chromaKeyVideoQuad.getVideoPlayer().start();
				}
				chromaKeyVideoQuad.setProjectionMatrix(projectionMatrix);
				chromaKeyVideoQuad.setTransform(trackable.getPoseMatrix());
				chromaKeyVideoQuad.setTranslate(0.0f, 0.0f, 0.0f);
				chromaKeyVideoQuad.setScale(
						1.0f,
						-0.70f,
						1.0f
				);
				chromaKeyVideoQuad.draw();
			}
			else{
				Log.d(TAG, "not match any images");
			}
		}

//		if (!legoDetected) {
//			if (videoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
//				videoQuad.getVideoPlayer().pause();
//			}
//		}
//
//		if (!blocksDetected) {
//			if (chromaKeyVideoQuad.getVideoPlayer().getState() == VideoPlayer.STATE_PLAYING) {
//				chromaKeyVideoQuad.getVideoPlayer().pause();
//			}
//		}
	}

	void destroyVideoPlayer() {
		videoQuad.getVideoPlayer().destroy();
		chromaKeyVideoQuad.getVideoPlayer().destroy();
        mCubeVideoQuad.getVideoPlayer().destroy();
        mShaperVideoQuad.getVideoPlayer().destroy();
	}
}
