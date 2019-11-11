package com.example.signlanguage.Screens.Favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.signlanguage.Database.AppDatabase;
import com.example.signlanguage.Database.Favorites;
import com.example.signlanguage.R;
import com.example.signlanguage.Screens.ResultActivity.ResultTabActivity;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnItemClicked {
    RecyclerView recyclerView;
    FavoritesAdapter favoritesAdapter;
    AppDatabase db;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").build();

        favoritesAdapter = new FavoritesAdapter();
        recyclerView = findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesAdapter.setOnClick(FavoritesActivity.this);
        recyclerView.setAdapter(favoritesAdapter);
        getAndShowFavorites();


    }

    private void getAndShowFavorites() {
        new AsyncTask<Void, Void, List<Favorites>>(){
            @Override
            protected List<Favorites> doInBackground(Void... voids) {
                return db.FavoriteDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Favorites> favorites) {
                super.onPostExecute(favorites);
                favoritesAdapter.TabsItem = favorites;
                favoritesAdapter.notifyDataSetChanged();

            }
        }.execute();
    }



    @Override
    public void onClickResultItem(int position) {
        Intent intent = new Intent(this, ResultTabActivity.class);
        intent.putExtra("id", favoritesAdapter.TabsItem.get(position).getId());
        intent.putExtra("keyword", favoritesAdapter.TabsItem.get(position).getKeyword());
        intent.putExtra("image", favoritesAdapter.TabsItem.get(position).getImage());
        intent.putExtra("video", favoritesAdapter.TabsItem.get(position).getVideo());
        startActivity(intent);

    }

    @Override
    public void OnclickDeleteFavorite(final int position) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.FavoriteDao().delete(favoritesAdapter.TabsItem.get(position));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                favoritesAdapter.TabsItem.remove(position);
                favoritesAdapter.notifyDataSetChanged();

            }
        }.execute();
    }
}
