/*
 * Copyright 2017 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.util;

import com.maxst.ar.BackgroundRenderer;
import com.maxst.ar.BackgroundTexture;
import com.maxst.ar.TrackerManager;

public class SampleRenderer {

	private BackgroundQuad backgroundQuad;
	private BackgroundRenderer backgroundRenderer;
	private TrackerManager.TrackerMask trackerMask;

	public SampleRenderer() {}

	public SampleRenderer(TrackerManager.TrackerMask trackerMask) {
		this.trackerMask = trackerMask;
	}

	public void onSurfaceCreated() {
		backgroundQuad = new BackgroundQuad();
		backgroundRenderer = BackgroundRenderer.getInstance();
		backgroundRenderer.initRendering();

		if (trackerMask == TrackerManager.TrackerMask.SLAM_TRACKER) {
			backgroundRenderer.setRenderingOption(BackgroundRenderer.RenderingOption.FEATURE_RENDERER,
					BackgroundRenderer.RenderingOption.PROGRESS_RENDERER,
					BackgroundRenderer.RenderingOption.SURFACE_MESH_RENDERER,
					BackgroundRenderer.RenderingOption.AXIS_RENDERER);
		} else if (trackerMask == TrackerManager.TrackerMask.CODE_TRACKER) {
			backgroundRenderer.setRenderingOption(BackgroundRenderer.RenderingOption.VIEW_FINDER_RENDERER);
		}
	}

	public void onSurfaceChanged(int width, int height) {
		backgroundRenderer.updateRendering(width, height);
	}

	public void onDrawFrame() {
		BackgroundTexture backgroundTexture = backgroundRenderer.getBackgroundTexture();
		if (backgroundTexture == null) {
			return;
		}

		backgroundRenderer.begin(backgroundTexture);
		backgroundRenderer.renderBackgroundToTexture();
		backgroundRenderer.end();

		backgroundQuad.draw(backgroundTexture, backgroundRenderer.getBackgroundPlaneProjectionMatrix());
	}
}
