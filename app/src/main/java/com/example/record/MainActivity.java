package com.example.record;

import static java.lang.Thread.sleep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaRecorder recorder;
    String filename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// 10초마다 반복 - 녹음시작 녹음 중지 파일저장
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "voice3.wav");
        filename = file.getAbsolutePath();
        Log.d("MainActivity", "저장할 파일 명 : " + filename);
        /*Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {

            @Override
            public void run() {
                try {
                    recordAudio();
                    sleep(10000);
                }
              catch (Exception e){
                  stopRecording();
              }

            }
        };
    timer.schedule(myTask,0,30000);
    timer.cancel();
    stopRecording();*/


        Button start = findViewById(R.id.btn);
        start.setOnClickListener((v) -> {
            CountDownTimer CDT = new CountDownTimer(10 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    //반복실행할 구문
                    recordAudio();
                }
                public void onFinish() {
                    //마지막에 실행할 구문
                    stopRecording();
                }
            };

            CDT.start(); //CountDownTimer 실행

            CDT.cancel();// 타이머 종료
        });
        Button stop = findViewById(R.id.btn1);
        stop.setOnClickListener((v) -> {
            // 10초 후 바로 정지
            stopRecording();
        });
    }


    public void recordAudio(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //전화통화로 바꾸기
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(filename);

        try {
            recorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recorder.start();
        Toast.makeText(getApplicationContext(),"녹음시작함",Toast.LENGTH_SHORT).show();
    }

    public void stopRecording(){
        if(recorder != null ){
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(getApplicationContext(),"녹음중지함",Toast.LENGTH_SHORT).show();
        }
    }
}