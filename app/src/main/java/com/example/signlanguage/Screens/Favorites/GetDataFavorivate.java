package com.example.signlanguage.Screens.Favorites;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.signlanguage.Database.AppDatabase;
import com.example.signlanguage.Database.Favorites;
import com.example.signlanguage.VolleyApi;

import java.util.List;

public class GetDataFavorivate extends Activity {

    public void getFavorivate(final AppDatabase db, final OnResponse listener) {
        new AsyncTask<Void, Void, List<Favorites>>(){
            @Override
            protected List<Favorites> doInBackground(Void... voids) {
                return db.FavoriteDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Favorites> favorites) {
                super.onPostExecute(favorites);
                listener.onResponse(favorites);
            }

        }.execute();
    }

    public interface OnResponse {
        void onResponse(List<Favorites> favorites);
    }
}
