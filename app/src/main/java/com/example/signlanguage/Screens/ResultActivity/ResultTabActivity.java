package com.example.signlanguage.Screens.ResultActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.signlanguage.Database.AppDatabase;
import com.example.signlanguage.Database.Favorites;
import com.example.signlanguage.MainActivity;
import com.example.signlanguage.R;
import com.example.signlanguage.Screens.Favorites.GetDataFavorivate;
import com.example.signlanguage.Screens.Search.SearchableActivity;
import com.example.signlanguage.receiver.NetworkStateChangeReceiver;

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

        if (!NetworkStateChangeReceiver.isConnected(ResultTabActivity.this)){
            NetworkStateChangeReceiver.buildDialog(ResultTabActivity.this, ResultTabActivity.this).show();
        }
        else {
            getDataFavorivate();
            getItemResult();
        }
        btnAddFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFavoritesOrDelete();
            }
        });
    }

    private void getDataFavorivate() {
        id = getIntent().getStringExtra("id");
        GetDataFavorivate favorivate = new GetDataFavorivate();
        favorivate.getFavorivate(db, new GetDataFavorivate.OnResponse() {
            @Override
            public void onResponse(List<Favorites> favorites) {
                if (IsFavorite(favorites, id) == 1) {
                    btnAddFavorites.setBackgroundResource(R.drawable.star);
                }
            }
        });

    }

    private int IsFavorite(List<Favorites> favorites, String id) {
        for (int i = 0; i < favorites.size(); i++) {
            if (id.equals(favorites.get(i).getId())) {
                return 1;
            }
        }
        return 0;
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


    private void insertFavoritesOrDelete() {
        id = getIntent().getStringExtra("id");
        GetDataFavorivate favorivate = new GetDataFavorivate();
        favorivate.getFavorivate(db, new GetDataFavorivate.OnResponse() {
            @Override
            public void onResponse(List<Favorites> favorites) {
                if (IsFavorite(favorites, id) == 1) {
                    deleteFavorite();
                } else {
                    new AsyncTask<Void, Void, Void>() {
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
        });

    }

    private void deleteFavorite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Favorites favorites = new Favorites(id, keyword, image, urlVideo);
                db.FavoriteDao().delete(favorites);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                btnAddFavorites.setBackgroundResource(R.drawable.starblank);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_open_search) {
            startActivity(new Intent(getApplicationContext(), SearchableActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
