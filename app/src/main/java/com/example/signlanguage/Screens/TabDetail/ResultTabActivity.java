package com.example.signlanguage.Screens.TabDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.signlanguage.Database.AppDatabase;
import com.example.signlanguage.Database.FavoriteDao;
import com.example.signlanguage.Database.Favorites;
import com.example.signlanguage.R;

import java.sql.SQLClientInfoException;
import java.util.List;

public class ResultTabActivity extends AppCompatActivity {
    TextView tvKeyword;
    VideoView video;
    ProgressDialog progressDialog;
    Button btnAddFavorites;
    String id, image, urlVideo, keyword;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tab);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").build();


        tvKeyword = findViewById(R.id.keyword);
        video = findViewById(R.id.video);
        btnAddFavorites = findViewById(R.id.btn_add_favorite);
        progressDialog = new ProgressDialog(ResultTabActivity.this);

        getItemResult();
        btnAddFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFavorites();
            }
        });
    }

    private void getItemResult() {
        id = getIntent().getStringExtra("id");
        image = getIntent().getStringExtra("image");
        urlVideo = getIntent().getStringExtra("video");
        keyword = getIntent().getStringExtra("keyword");
        tvKeyword.setText(keyword);
        progressDialog.setMessage("Loading video...");
        progressDialog.show();
        video.setVideoURI(Uri.parse(urlVideo));
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

    private void insertFavorites() {
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    Favorites favorites = new Favorites(id, keyword, image, urlVideo);
                    db.FavoriteDao().insertAll(favorites);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    btnAddFavorites.setBackgroundResource(R.drawable.star);
                }
            }.execute();
        }

}
