package com.example.signlanguage;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.model.NameComparator;
import com.example.signlanguage.model.Subcategory;

import java.util.Collections;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerView);

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

}