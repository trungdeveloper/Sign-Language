package com.example.signlanguage.Screens.TabDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.signlanguage.R;

public class ResultTabActivity extends AppCompatActivity {
    TextView tvKeyword;
    VideoView video;
   ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tab);
        tvKeyword = findViewById(R.id.keyword);
        video = findViewById(R.id.video);
        progressDialog = new ProgressDialog(ResultTabActivity.this);
        getItemResult();
    }

    private void getItemResult() {
        String subCategory_ID = getIntent().getStringExtra("subCategory_ID");
        String image = getIntent().getStringExtra("image");
        String url = getIntent().getStringExtra("video");
        String keyword = getIntent().getStringExtra("keyword");
        tvKeyword.setText(keyword);
        progressDialog.setMessage("Loading video...");
        progressDialog.show();
        video.setVideoURI(Uri.parse(url));
        MediaController mediaController = new MediaController(this);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);
        video.start();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
            }
        });
    }
}
