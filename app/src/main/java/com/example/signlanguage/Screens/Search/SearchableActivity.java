package com.example.signlanguage.Screens.Search;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.ResultActivity.ResultTabActivity;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.NameComparator;
import com.example.signlanguage.model.Subcategory;

import java.util.Collections;
import java.util.List;

public class SearchableActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClicked{
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerView);

        VolleyApi volley =new VolleyApi(this);
        String urlJsonArry = getResources().getString(R.string.API_URL)+"posts?limit=1000";
        volley.getPosts(urlJsonArry,"sources",new VolleyApi.OnSubCategoryResponse() {
            @Override
            public void OnSubCategoryResponse(List<Subcategory> subcategories) {
                Collections.sort(subcategories, new NameComparator());
                recyclerAdapter = new RecyclerAdapter(subcategories);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchableActivity.this));
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.setOnClick(SearchableActivity.this);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menu.findItem(R.id.btn_open_search).setVisible(false);
        SearchView searchView = (SearchView) menuItem.getActionView();
        menuItem.expandActionView();
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

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View arg0) {
                finish();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClickDetailTab(int position) {
        Intent intent = new Intent(this, ResultTabActivity.class);
        intent.putExtra("id",recyclerAdapter.moviesList.get(position).getId());
        intent.putExtra("keyword",recyclerAdapter.moviesList.get(position).getKeyword());
        intent.putExtra("image",recyclerAdapter.moviesList.get(position).getImage());
        intent.putExtra("video",recyclerAdapter.moviesList.get(position).getVideo());
        startActivity(intent);
    }
}