package com.softdev.videoapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoViewActivity extends AppCompatActivity {

    private String filePath = "/storage/emulated/0/mediapicker/videos/83cc2061-ed29-40e0-8090-43b3bb8da1c1.mp4";
    VideoView videoView1, videoView2;
    RelativeLayout video_holder1, video_holder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        ArrayList<String> arrVideoPaths = getIntent().getExtras().getStringArrayList("video");

        video_holder1 = findViewById(R.id.video_holder1);
        video_holder2 = findViewById(R.id.video_holder2);

        videoView1 = (VideoView) findViewById(R.id.video1);
        videoView2 = (VideoView) findViewById(R.id.video2);

        if (arrVideoPaths != null && arrVideoPaths.size() > 0) {
            playVideo(arrVideoPaths.get(0), videoView1);
            playVideo(arrVideoPaths.get(1), videoView2);
        }

    }

    private void playVideo(String videoPath, VideoView videoView) {
        //Set MediaController  to enable play, pause, forward, etc options.
        MediaController mediaController = new MediaController(VideoViewActivity.this);
        mediaController.setAnchorView(videoView);
        //Location of Media File
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
//          Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "" + filePath);
        Uri uri = Uri.parse(videoPath);

        //Starting VideView By Setting MediaController and URI
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();


//        int i = videoView.getHeight() > videoView.getWidth() ? videoView.getHeight() : videoView.getWidth();
//        video_holder.setLayoutParams(new LinearLayout.LayoutParams(i, i));

        videoView.start();
    }


}
