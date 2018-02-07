/*
 * Copyright 2016 Maxst, Inc. All Rights Reserved.
 */

package com.tornadolab.comp594.videoarm.arobject;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.tornadolab.comp594.videoarm.util.ShaderUtil;
import com.maxst.videoplayer.VideoPlayer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ShaperVideoQuad extends BaseModel {

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
            // f 17 25 9
            1.24999999979742e-07f, -0.5000001875f, 0f,
            0.500000125f, -1.87499999996718e-07f, 0f,
            -0.499999875f, -1.87499999996718e-07f, 0f,
            // f 1 2 5
            1.24999999979742e-07f, 0.4999998125f, 0f,
            -0.097545875f, 0.4903918125f, 0f,
            -0.353553875f, 0.3535538125f, 0f,
            // f 3 4 5
            -0.191341875f, 0.4619398125f, 0f,
            -0.277785875f, 0.4157338125f, 0f,
            -0.353553875f, 0.3535538125f, 0f,
            // f 5 6 7
            -0.353553875f, 0.3535538125f, 0f,
            -0.415733875f, 0.2777858125f, 0f,
            -0.461939875f, 0.1913418125f, 0f,
            // f 7 8 5
            -0.461939875f, 0.1913418125f, 0f,
            -0.490391875f, 0.0975458125f, 0f,
            -0.353553875f, 0.3535538125f, 0f,
            // f 9 10 11
            -0.499999875f, -1.87499999996718e-07f, 0f,
            -0.490391875f, -0.0975461875f, 0f,
            -0.461939875f, -0.1913421875f, 0f,
            // f 11 12 13
            -0.461939875f, -0.1913421875f, 0f,
            -0.415733875f, -0.2777861875f, 0f,
            -0.353553875f, -0.3535541875f, 0f,
            // f 13 14 15
            -0.353553875f, -0.3535541875f, 0f,
            -0.277785875f, -0.4157341875f, 0f,
            -0.191341875f, -0.4619401875f, 0f,
            // f 15 16 13
            -0.191341875f, -0.4619401875f, 0f,
            -0.097545875f, -0.4903921875f, 0f,
            -0.353553875f, -0.3535541875f, 0f,
            // f 17 18 19
            1.24999999979742e-07f, -0.5000001875f, 0f,
            0.097546125f, -0.4903921875f, 0f,
            0.191342125f, -0.4619401875f, 0f,
            // f 19 20 21
            0.191342125f, -0.4619401875f, 0f,
            0.277786125f, -0.4157341875f, 0f,
            0.353554125f, -0.3535541875f, 0f,
            // f 21 22 23
            0.353554125f, -0.3535541875f, 0f,
            0.415736125f, -0.2777841875f, 0f,
            0.461940125f, -0.1913421875f, 0f,
            // f 23 24 21
            0.461940125f, -0.1913421875f, 0f,
            0.490392125f, -0.0975441875f, 0f,
            0.353554125f, -0.3535541875f, 0f,
            // f 25 26 27
            0.500000125f, -1.87499999996718e-07f, 0f,
            0.490392125f, 0.0975458125f, 0f,
            0.461940125f, 0.1913418125f, 0f,
            // f 27 28 29
            0.461940125f, 0.1913418125f, 0f,
            0.415734125f, 0.2777858125f, 0f,
            0.353552125f, 0.3535538125f, 0f,
            // f 29 30 31
            0.353552125f, 0.3535538125f, 0f,
            0.277784125f, 0.4157358125f, 0f,
            0.191342125f, 0.4619398125f, 0f,
            // f 31 32 29
            0.191342125f, 0.4619398125f, 0f,
            0.097544125f, 0.4903918125f, 0f,
            0.353552125f, 0.3535538125f, 0f,
            // f 2 3 5
            -0.097545875f, 0.4903918125f, 0f,
            -0.191341875f, 0.4619398125f, 0f,
            -0.353553875f, 0.3535538125f, 0f,
            // f 5 8 9
            -0.353553875f, 0.3535538125f, 0f,
            -0.490391875f, 0.0975458125f, 0f,
            -0.499999875f, -1.87499999996718e-07f, 0f,
            // f 9 11 17
            -0.499999875f, -1.87499999996718e-07f, 0f,
            -0.461939875f, -0.1913421875f, 0f,
            1.24999999979742e-07f, -0.5000001875f, 0f,
            // f 13 16 17
            -0.353553875f, -0.3535541875f, 0f,
            -0.097545875f, -0.4903921875f, 0f,
            1.24999999979742e-07f, -0.5000001875f, 0f,
            // f 17 19 25
            1.24999999979742e-07f, -0.5000001875f, 0f,
            0.191342125f, -0.4619401875f, 0f,
            0.500000125f, -1.87499999996718e-07f, 0f,
            // f 21 24 25
            0.353554125f, -0.3535541875f, 0f,
            0.490392125f, -0.0975441875f, 0f,
            0.500000125f, -1.87499999996718e-07f, 0f,
            // f 25 27 29
            0.500000125f, -1.87499999996718e-07f, 0f,
            0.461940125f, 0.1913418125f, 0f,
            0.353552125f, 0.3535538125f, 0f,
            // f 29 32 1
            0.353552125f, 0.3535538125f, 0f,
            0.097544125f, 0.4903918125f, 0f,
            1.24999999979742e-07f, 0.4999998125f, 0f,
            // f 1 5 9
            1.24999999979742e-07f, 0.4999998125f, 0f,
            -0.353553875f, 0.3535538125f, 0f,
            -0.499999875f, -1.87499999996718e-07f, 0f,
            // f 11 13 17
            -0.461939875f, -0.1913421875f, 0f,
            -0.353553875f, -0.3535541875f, 0f,
            1.24999999979742e-07f, -0.5000001875f, 0f,
            // f 19 21 25
            0.191342125f, -0.4619401875f, 0f,
            0.353554125f, -0.3535541875f, 0f,
            0.500000125f, -1.87499999996718e-07f, 0f,
            // f 25 29 1
            0.500000125f, -1.87499999996718e-07f, 0f,
            0.353552125f, 0.3535538125f, 0f,
            1.24999999979742e-07f, 0.4999998125f, 0f,
            // f 1 9 25
            1.24999999979742e-07f, 0.4999998125f, 0f,
            -0.499999875f, -1.87499999996718e-07f, 0f,
            0.500000125f, -1.87499999996718e-07f, 0f,
    };

    private static final short[] INDEX_BUF = {
            17,1,25,2,9,3,
            1,4,2,5,5,6,
            3,7,4,8,5,6,
            5,6,6,9,7,10,
            7,10,8,11,5,6,
            9,3,10,12,11,13,
            11,13,12,14,13,15,
            13,15,14,16,15,17,
            15,17,16,18,13,15,
            17,1,18,19,19,20,
            19,20,20,21,21,22,
            21,22,22,23,23,24,
            23,24,24,25,21,22,
            25,2,26,26,27,27,
            27,27,28,28,29,29,
            29,29,30,30,31,31,
            31,31,32,32,29,29,
            2,5,3,7,5,6,
            5,6,8,11,9,3,
            9,3,11,13,17,1,
            13,15,16,18,17,1,
            17,1,19,20,25,2,
            21,22,24,25,25,2,
            25,2,27,27,29,29,
            29,29,32,32,1,4,
            1,4,5,6,9,3,
            11,13,13,15,17,1,
            19,20,21,22,25,2,
            25,2,29,29,1,4,
            1,4,9,3,25,2,
    };

    private static final float[] TEXTURE_COORD_BUF = {
            // f 17/1/1 25/2/1 9/3/1
            0.478993f, 1.005606f,
            0.890856f, 0.554928f,
            0.140984f, 0.456455f,
            // f 1/4/1 2/5/1 5/6/1
            0.552847f, 0.00577899999999998f,
            0.478992f, 0.00578000000000001f,
            0.276912f, 0.117385f,
            // f 3/7/1 4/8/1 5/6/1
            0.406555f, 0.024991f,
            0.338321f, 0.062676f,
            0.276912f, 0.117385f,
            // f 5/6/1 6/9/1 7/10/1
            0.276912f, 0.117385f,
            0.224688f, 0.187017f,
            0.183656f, 0.268895f,
            // f 7/10/1 8/11/1 5/6/1
            0.183656f, 0.268895f,
            0.155393f, 0.359873f,
            0.276912f, 0.117385f,
            // f 9/3/1 10/12/1 11/13/1
            0.140984f, 0.456455f,
            0.140984f, 0.554928f,
            0.155393f, 0.651511f,
            // f 11/13/1 12/14/1 13/15/1
            0.155393f, 0.651511f,
            0.183656f, 0.742489f,
            0.224688f, 0.824368f,
            // f 13/15/1 14/16/1 15/17/1
            0.224688f, 0.824368f,
            0.276912f, 0.894f,
            0.338321f, 0.94871f,
            // f 15/17/1 16/18/1 13/15/1
            0.338321f, 0.94871f,
            0.406555f, 0.986395f,
            0.224688f, 0.824368f,
            // f 17/1/1 18/19/1 19/20/1
            0.478993f, 1.005606f,
            0.552849f, 1.005606f,
            0.625286f, 0.986394f,
            // f 19/20/1 20/21/1 21/22/1
            0.625286f, 0.986394f,
            0.693519f, 0.94871f,
            0.754929f, 0.894f,
            // f 21/22/1 22/23/1 23/24/1
            0.754929f, 0.894f,
            0.807153f, 0.824367f,
            0.848185f, 0.742489f,
            // f 23/24/1 24/25/1 21/22/1
            0.848185f, 0.742489f,
            0.876448f, 0.65151f,
            0.754929f, 0.894f,
            // f 25/2/1 26/26/1 27/27/1
            0.890856f, 0.554928f,
            0.890856f, 0.456454f,
            0.876447f, 0.359872f,
            // f 27/27/1 28/28/1 29/29/1
            0.876447f, 0.359872f,
            0.848183f, 0.268894f,
            0.807151f, 0.187016f,
            // f 29/29/1 30/30/1 31/31/1
            0.807151f, 0.187016f,
            0.754927f, 0.117384f,
            0.693518f, 0.062675f,
            // f 31/31/1 32/32/1 29/29/1
            0.693518f, 0.062675f,
            0.625284f, 0.02499f,
            0.807151f, 0.187016f,
            // f 2/5/1 3/7/1 5/6/1
            0.478992f, 0.00578000000000001f,
            0.406555f, 0.024991f,
            0.276912f, 0.117385f,
            // f 5/6/1 8/11/1 9/3/1
            0.276912f, 0.117385f,
            0.155393f, 0.359873f,
            0.140984f, 0.456455f,
            // f 9/3/1 11/13/1 17/1/1
            0.140984f, 0.456455f,
            0.155393f, 0.651511f,
            0.478993f, 1.005606f,
            // f 13/15/1 16/18/1 17/1/1
            0.224688f, 0.824368f,
            0.406555f, 0.986395f,
            0.478993f, 1.005606f,
            // f 17/1/1 19/20/1 25/2/1
            0.478993f, 1.005606f,
            0.625286f, 0.986394f,
            0.890856f, 0.554928f,
            // f 21/22/1 24/25/1 25/2/1
            0.754929f, 0.894f,
            0.876448f, 0.65151f,
            0.890856f, 0.554928f,
            // f 25/2/1 27/27/1 29/29/1
            0.890856f, 0.554928f,
            0.876447f, 0.359872f,
            0.807151f, 0.187016f,
            // f 29/29/1 32/32/1 1/4/1
            0.807151f, 0.187016f,
            0.625284f, 0.02499f,
            0.552847f, 0.00577899999999998f,
            // f 1/4/1 5/6/1 9/3/1
            0.552847f, 0.00577899999999998f,
            0.276912f, 0.117385f,
            0.140984f, 0.456455f,
            // f 11/13/1 13/15/1 17/1/1
            0.155393f, 0.651511f,
            0.224688f, 0.824368f,
            0.478993f, 1.005606f,
            // f 19/20/1 21/22/1 25/2/1
            0.625286f, 0.986394f,
            0.754929f, 0.894f,
            0.890856f, 0.554928f,
            // f 25/2/1 29/29/1 1/4/1
            0.890856f, 0.554928f,
            0.807151f, 0.187016f,
            0.552847f, 0.00577899999999998f,
            // f 1/4/1 9/3/1 25/2/1
            0.552847f, 0.00577899999999998f,
            0.140984f, 0.456455f,
            0.890856f, 0.554928f,
    };

    private int[] textureNames;
    private VideoPlayer videoPlayer;
    private boolean videoSizeAcquired = false;

    public ShaperVideoQuad() {
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
//            Log.d("VideoPlayer Size: width: ", videoPlayer.getVideoWidth()+"|height:"+videoPlayer.getVideoHeight());
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

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, INDEX_BUF.length,
                GLES20.GL_UNSIGNED_SHORT, indexBuffer);
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
