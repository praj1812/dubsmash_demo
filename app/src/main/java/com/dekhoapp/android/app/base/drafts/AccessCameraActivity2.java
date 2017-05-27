package com.dekhoapp.android.app.base.drafts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dekhoapp.android.app.base.R;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by prajakti on 5/26/2017.
 */

//public class AccessCameraActivity2 extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera);
//
//        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        cameraManager.openCamera();
//
////        Button recordButton = (Button) findViewById(R.id.startRecordBtn);
//
//    }
//}

public class AccessCameraActivity2 extends Activity implements SurfaceHolder.Callback {
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;

    Button recordButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recordButton = (Button) findViewById(R.id.startRecordBtn);

        recorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.activity_camera);

        SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

//        cameraView.setClickable(true);
//        cameraView.setOnClickListener(this);
    }

    private void initRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);
        recorder.setOutputFile("/sdcard/videocapture_example.mp4");
        recorder.setMaxDuration(50000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }

//        recordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (recording) {
//                    recorder.stop();
//                    recording = false;
//
//                    // Let's initRecorder so we can record again
//                    initRecorder();
//                    prepareRecorder();
//                } else {
//                    recording = true;
//                    Intent intent = getIntent();
//                    String uriStr = intent.getSerializableExtra("uriStr").toString();
//                    Log.d(TAG, "uriStr" + uriStr);
//                    MediaPlayer mp = new MediaPlayer();
//                    try {
//                        mp.setDataSource(uriStr);
//                        mp.prepare();
//                        Log.d(TAG, "prepared");
//
//                        mp.start();
//                        recorder.start();
//                    } catch (IOException e) {
//                        Log.e(TAG, "prepare() failed");
//                    }
//                }
//            }
//        });
    }

    public void onClick(View v) {
        if (recording) {
            recorder.stop();
            recording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            recording = true;
            Intent intent = getIntent();
            String uriStr = intent.getSerializableExtra("uriStr").toString();
            Log.d(TAG, "uriStr" + uriStr);
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(uriStr);
                mp.prepare();
                Log.d(TAG, "prepared");

                mp.start();
                recorder.start();
            } catch (IOException e) {
                Log.e(TAG, "prepare() failed");
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }
}
