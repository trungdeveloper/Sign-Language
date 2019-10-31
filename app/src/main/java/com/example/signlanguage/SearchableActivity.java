package com.example.signlanguage;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.Screens.TabDetail.TabDetailActivity;
import com.example.signlanguage.Screens.TabDetail.TabDetailAdapter;
import com.example.signlanguage.model.NameComparator;
import com.example.signlanguage.model.Subcategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

//    List<String> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        moviesList = new ArrayList<>();
//        moviesList.add("Iron Man");
//        moviesList.add("The Incredible Hulk");
//        moviesList.add("Iron Man 2");
//        moviesList.add("Thor");
//        moviesList.add("Captain America: The First Avenger");
//        moviesList.add("The Avengers");
//        moviesList.add("Iron Man 3");
//        moviesList.add("Thor: The Dark World");
//        moviesList.add("Captain America: The Winter Soldier");
//        moviesList.add("Guardians of the Galaxy");
//        moviesList.add("Avengers: Age of Ultron");
//        moviesList.add("Ant-Man");
//        moviesList.add("Captain America: Civil War");
//        moviesList.add("Doctor Strange");
//        moviesList.add("Guardians of the Galaxy Vol. 2");
//        moviesList.add("Spider-Man: Homecoming");
//        moviesList.add("Thor: Ragnarok");
//        moviesList.add("Black Panther");
//        moviesList.add("Avengers: Infinity War");
//        moviesList.add("Ant-Man and the Wasp");
//        moviesList.add("Captain Marvel");
//        moviesList.add("Avengers: Endgame");
//        moviesList.add("Spider-Man: Far From Home");
//
        recyclerView = findViewById(R.id.recyclerView);
//        recyclerAdapter = new RecyclerAdapter(moviesList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(recyclerAdapter);

        VolleyApi volley =new VolleyApi(this);

        volley.getAllPosts(new VolleyApi.OnSubCategoryResponse() {
            @Override
            public void OnSubCategoryResponse(List<Subcategory> subcategories) {
                Collections.sort(subcategories, new NameComparator());
                recyclerAdapter = new RecyclerAdapter(subcategories);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchableActivity.this));
                recyclerView.setAdapter(recyclerAdapter);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}