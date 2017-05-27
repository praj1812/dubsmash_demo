package com.dekhoapp.android.app.base.drafts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dekhoapp.android.app.base.R;

import java.io.IOException;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.dekhoapp.android.app.base.R.id.startRecordBtn;
import static com.dekhoapp.android.app.base.R.id.stopRecordBtn;

/**
 * Created by prajakti on 5/26/2017.
 */

public class AccessCameraActivity3 extends Activity implements View.OnClickListener, SurfaceHolder.Callback {
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;

    MediaPlayer mp = new MediaPlayer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.activity_camera);

        SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);
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
    }

    public void onClick(View v) {
        Button startRecordBtn = (Button) findViewById(R.id.startRecordBtn);
        Button stopRecordBtn = (Button) findViewById(R.id.stopRecordBtn);
        if (recording) {
            recorder.stop();
            recording = false;
            startRecordBtn.setVisibility(View.VISIBLE);
            stopRecordBtn.setVisibility(View.INVISIBLE);

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            recording = true;
//            startRecordBtn.setVisibility(View.INVISIBLE);
//            stopRecordBtn.setVisibility(View.VISIBLE);

            Intent intent = getIntent();
            String uriStr = intent.getSerializableExtra("uriStr").toString();
            Log.d(TAG, "uriStr: " + uriStr);

            new ASyncTask().execute(uriStr);

//            try {
//                mp.setDataSource(uriStr);
//                mp.prepare();
//                mp.start();
//            } catch (IOException e) {
//                Log.e(TAG, "prepare() failed");
//            }
//
//            recorder.start();
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

    public class ASyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                mp.setDataSource(params[0]);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Log.e(TAG, "prepare() failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            startRecordBtn.setVisibility(View.INVISIBLE);
//            stopRecordBtn.setVisibility(View.VISIBLE);
            recorder.start();

            //TODO: Continue here
        }
    }
}
