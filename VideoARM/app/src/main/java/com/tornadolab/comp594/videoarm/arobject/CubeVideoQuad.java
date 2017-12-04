/*
 * Copyright 2016 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.arobject;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.maxst.videoplayer.VideoPlayer;
import com.tornadolab.comp594.videoarm.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CubeVideoQuad extends BaseModel {

    private static final String VERTEX_SHADER_SRC =
            "attribute vec4 a_position;\n" +
                    "attribute vec2 a_texCoord;\n" +
                    "varying vec2 v_texCoord;\n" +
                    "uniform mat4 u_mvpMatrix;\n" +
                    "void main()							\n" +
                    "{										\n" +
                    "	gl_Position = u_mvpMatrix * a_position;\n" +
                    "	v_texCoord = a_texCoord; 			\n" +
                    "}										\n";

    private static final String FRAGMENT_SHADER_SRC =
            "precision mediump float;\n" +
                    "varying vec2 v_texCoord;\n" +
                    "uniform sampler2D u_texture;\n" +

                    "void main(void)\n" +
                    "{\n" +
                    "	gl_FragColor = texture2D(u_texture, v_texCoord);\n" +
                    "}\n";


    private static final float[] VERTEX_BUF = {
            // f 1/1/1 2/2/1 3/3/1 4/4/1
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            // f 1/1/1 2/2/1 3/3/1 4/4/1
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            // f 5/5/2 8/6/2 7/7/2 6/8/2
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            // f 5/5/2 8/6/2 7/7/2 6/8/2
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            // f 1/1/3 5/9/3 6/10/3 2/11/3
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            // f 1/1/3 5/9/3 6/10/3 2/11/3
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            // f 2/12/4 6/13/4 7/7/4 3/14/4
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            // f 2/12/4 6/13/4 7/7/4 3/14/4
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            // f 3/15/5 7/16/5 8/17/5 4/4/5
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            // f 3/15/5 7/16/5 8/17/5 4/4/5
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            // f 5/5/6 1/18/6 4/19/6 8/20/6
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            // f 5/5/6 1/18/6 4/19/6 8/20/6
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
    };

    private static final short[] INDEX_BUF = {
            0, 1, 2, 2, 3, 0
    };

    private static final float[] TEXTURE_COORD_BUF = {
            // f 1/1/1 2/2/1 3/3/1 4/4/1
            0.000000f, 1f,
            1.000000f, 1f,
            1.000000f, 0f,
            // f 1/1/1 2/2/1 3/3/1 4/4/1
            0.000000f, 1f,
            0.000000f, 0f,
            1.000000f, 0f,
            // f 5/5/2 8/6/2 7/7/2 6/8/2
            0.000000f, 1f,
            1.000000f, 1f,
            1.000000f, 0f,
            // f 5/5/2 8/6/2 7/7/2 6/8/2
            0.000000f, 1f,
            0.000000f, 0f,
            1.000000f, 0f,
            // f 1/1/3 5/9/3 6/10/3 2/11/3
            0.000000f, 1f,
            1.000000f, 1f,
            1.000000f, 0f,
            // f 1/1/3 5/9/3 6/10/3 2/11/3
            0.000000f, 1f,
            0.000000f, 0f,
            1.000000f, 0f,
            // f 2/12/4 6/13/4 7/7/4 3/14/4
            0.000000f, 1f,
            1.000000f, 1f,
            1.000000f, 0f,
            // f 2/12/4 6/13/4 7/7/4 3/14/4
            0.000000f, 1f,
            0.000000f, 0f,
            1.000000f, 0f,
            // f 3/15/5 7/16/5 8/17/5 4/4/5
            0.000000f, 1f,
            1.000000f, 1f,
            1.000000f, 0f,
            // f 3/15/5 7/16/5 8/17/5 4/4/5
            0.000000f, 1f,
            0.000000f, 0f,
            1.000000f, 0f,
            // f 5/5/6 1/18/6 4/19/6 8/20/6
            0.000000f, 1f,
            1.000000f, 1f,
            1.000000f, 0f,
            // f 5/5/6 1/18/6 4/19/6 8/20/6
            0.000000f, 1f,
            0.000000f, 0f,
            1.000000f, 0f,
    };

    private int[] textureNames;
    private VideoPlayer videoPlayer;
    private boolean videoSizeAcquired = false;

    public CubeVideoQuad() {
        super();
        ByteBuffer bb = ByteBuffer.allocateDirect(VERTEX_BUF.length * Float.SIZE / 8);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(VERTEX_BUF);
        vertexBuffer.position(0);

        bb = ByteBuffer.allocateDirect(INDEX_BUF.length * Integer.SIZE / 8);
        bb.order(ByteOrder.nativeOrder());
        indexBuffer = bb.asShortBuffer();
        indexBuffer.put(INDEX_BUF);
        indexBuffer.position(0);

        bb = ByteBuffer.allocateDirect(TEXTURE_COORD_BUF.length * Float.SIZE / 8);
        bb.order(ByteOrder.nativeOrder());
        textureCoordBuff = bb.asFloatBuffer();
        textureCoordBuff.put(TEXTURE_COORD_BUF);
        textureCoordBuff.position(0);

        shaderProgramId = ShaderUtil.createProgram(VERTEX_SHADER_SRC, FRAGMENT_SHADER_SRC);

        positionHandle = GLES20.glGetAttribLocation(shaderProgramId, "a_position");
        textureCoordHandle = GLES20.glGetAttribLocation(shaderProgramId, "a_texCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgramId, "u_mvpMatrix");
        textureHandle = GLES20.glGetUniformLocation(shaderProgramId, "u_texture");

        textureNames = new int[1];
    }

    @Override
    public void draw() {
        if (videoPlayer == null) {
            return;
        }

        if (!videoSizeAcquired) {
            int videoWidth = videoPlayer.getVideoWidth();
            int videoHeight = videoPlayer.getVideoHeight();

            if (videoWidth == 0 || videoHeight == 0) {
                return;
            }

            videoSizeAcquired = true;

            GLES20.glGenTextures(1, textureNames, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureNames[0]);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, videoWidth, videoHeight, 0, GLES20.GL_RGB,
                    GLES20.GL_UNSIGNED_SHORT_5_6_5, null);

            videoPlayer.setTexture(textureNames[0]);
            return;
        }

        if (videoPlayer.getState() != VideoPlayer.STATE_PLAYING) {
            return;
        }

        videoPlayer.update();

        if (!videoPlayer.isTextureDrawable()) {
            return;
        }

        GLES20.glUseProgram(shaderProgramId);

        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(textureCoordHandle, 2, GLES20.GL_FLOAT, false,
                0, textureCoordBuff);
        GLES20.glEnableVertexAttribArray(textureCoordHandle);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.multiplyMM(modelMatrix, 0, translation, 0, rotation, 0);
        Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, scale, 0);
        Matrix.multiplyMM(modelMatrix, 0, transform, 0, modelMatrix, 0);


        Matrix.multiplyMM(localMvpMatrix, 0, projectionMatrix, 0, modelMatrix, 0);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, localMvpMatrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(textureHandle, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureNames[0]);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_BUF.length / 3);

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(textureCoordHandle);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void setVideoPlayer(VideoPlayer videoPlayer) {
        this.videoPlayer = videoPlayer;
    }

    public VideoPlayer getVideoPlayer() {
        return this.videoPlayer;
    }
}
