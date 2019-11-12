package com.example.signlanguage.Screens.TabDetail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.signlanguage.MainActivity;
import com.example.signlanguage.R;
import com.example.signlanguage.Screens.ResultActivity.ResultTabActivity;
import com.example.signlanguage.Screens.Search.SearchableActivity;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.NameComparator;
import com.example.signlanguage.model.Subcategory;
import com.example.signlanguage.receiver.NetworkStateChangeReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TabDetailActivity extends AppCompatActivity implements TabDetailAdapter.OnItemClicked{
    RecyclerView rcViewDetail;
    TabDetailAdapter tabDetailAdapterAdapter;

    public final static List<Subcategory> ListSubcategories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_detail);

        rcViewDetail = findViewById(R.id.recyclerViewBasicTabDetail);
        rcViewDetail.setLayoutManager(new LinearLayoutManager(this));

        if (!NetworkStateChangeReceiver.isConnected(TabDetailActivity.this)){
            NetworkStateChangeReceiver.buildDialog(TabDetailActivity.this, TabDetailActivity.this).show();
        }
        else {
            getData();
        }

    }

    private void getData(){
        VolleyApi volley =new VolleyApi(TabDetailActivity.this);
        String subCategory_ID = getIntent().getStringExtra("subCategory_ID");
        String url = getResources().getString(R.string.API_URL)+"subcategories/"+subCategory_ID+"?limit=1000";

            volley.getPosts(url,"posts", new VolleyApi.OnSubCategoryResponse() {
                @Override
                public void OnSubCategoryResponse(List<Subcategory> subcategories) {
                    Collections.sort(subcategories, new NameComparator());
                    Collections.sort(subcategories, new Comparator<Subcategory>() {
                        public int compare(Subcategory o1, Subcategory o2) {
                            return extractInt(o1.getKeyword()) - extractInt(o2.getKeyword());
                        }
                        int extractInt(String s) {
                            String num = s.replaceAll("\\D", "");
                            // return 0 if no digits found
                            return num.isEmpty() ? 0 : Integer.parseInt(num);
                        }
                    });
                    tabDetailAdapterAdapter = new TabDetailAdapter(this, subcategories, TabDetailActivity.this);
                    rcViewDetail.setAdapter(tabDetailAdapterAdapter);
                    tabDetailAdapterAdapter.setOnClick(TabDetailActivity.this);
                }
            });


    }


    @Override
    public void onClickDetailTab(int position) {
        Intent intent = new Intent(this, ResultTabActivity.class);
        intent.putExtra("id",tabDetailAdapterAdapter.SubCategoryItem.get(position).getId());
        intent.putExtra("keyword",tabDetailAdapterAdapter.SubCategoryItem.get(position).getKeyword());
        intent.putExtra("image",tabDetailAdapterAdapter.SubCategoryItem.get(position).getImage());
        intent.putExtra("video",tabDetailAdapterAdapter.SubCategoryItem.get(position).getVideo());
        startActivity(intent);
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
