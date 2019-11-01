package com.example.signlanguage.Screens.TabDetail.TabDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.TabDetail.ResultTabActivity;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.NameComparator;
import com.example.signlanguage.model.Subcategory;

import java.util.ArrayList;
import java.util.Collections;
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
        getData();
    }

    private void getData(){
        VolleyApi volley =new VolleyApi(TabDetailActivity.this);
        String subCategory_ID = getIntent().getStringExtra("subCategory_ID");
        String url = "http://signlanguage.somee.com/api/subcategories/"+subCategory_ID;

        volley.getSubcategoryData(url, new VolleyApi.OnSubCategoryResponse() {
            @Override
            public void OnSubCategoryResponse(List<Subcategory> subcategories) {
                Collections.sort(subcategories, new NameComparator());
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
}
