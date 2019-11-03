package com.example.signlanguage.Screens.TabDetail.Search;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.TabDetail.ResultTabActivity;
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
        String urlJsonArry = "http://signlanguage.somee.com/api/posts?limit=1000";
        volley.getAllPosts(urlJsonArry,new VolleyApi.OnSubCategoryResponse() {
            @Override
            public void OnSubCategoryResponse(List<Subcategory> subcategories) {
                Collections.sort(subcategories, new NameComparator());
                recyclerAdapter = new RecyclerAdapter(subcategories);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchableActivity.this));
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.setOnClick(SearchableActivity.this);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addItemDecoration(dividerItemDecoration);
//        invalidateOptionsMenu();
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
        intent.putExtra("id",recyclerAdapter.moviesListAll.get(position).getId());
        intent.putExtra("keyword",recyclerAdapter.moviesListAll.get(position).getKeyword());
        intent.putExtra("image",recyclerAdapter.moviesListAll.get(position).getImage());
        intent.putExtra("video",recyclerAdapter.moviesListAll.get(position).getVideo());
        startActivity(intent);
    }
}