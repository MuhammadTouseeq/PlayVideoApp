package com.softdev.videoapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView btnVideo1;
    ImageView btnVideo2;
    Button btnNext;
    RelativeLayout video_holder1, video_holder2;


    ArrayList<String> arrVideoPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video_holder1 = findViewById(R.id.video_holder1);
        video_holder2 = findViewById(R.id.video_holder2);

        btnVideo1 = (ImageView) findViewById(R.id.btnVideo1);
        btnVideo2 = (ImageView) findViewById(R.id.btnVideo2);
        btnNext = findViewById(R.id.btnNext);

        btnVideo1.setOnClickListener(this);
        btnVideo2.setOnClickListener(this);

        btnNext.setOnClickListener(this);

    }

    private void openVideoPicker() {
        Dexter.withActivity(MainActivity.this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE

                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            new VideoPicker.Builder(MainActivity.this)
                                    .mode(VideoPicker.Mode.GALLERY)
                                    .directory(VideoPicker.Directory.DEFAULT)
                                    .extension(VideoPicker.Extension.MP4)
                                    .enableDebuggingMode(true)
                                    .build();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
//                            UIHelper.showSettingsDialog(activity);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
            //Your Code
            for (int count = 0; count < mPaths.size(); count++)

            {
                arrVideoPaths.add(mPaths.get(count));
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnVideo1: {
                openVideoPicker();
            }
            break;
            case R.id.btnVideo2: {
                openVideoPicker();
            }
            break;
            case R.id.btnNext: {
                if (arrVideoPaths.size() < 2) {
                    Toast.makeText(this, "Add missing video", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(this, VideoViewActivity.class);
                intent.putStringArrayListExtra("video", arrVideoPaths);
                startActivity(intent);
            }
            break;
        }
    }
}
