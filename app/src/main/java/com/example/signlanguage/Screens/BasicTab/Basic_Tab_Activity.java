package com.example.signlanguage.Screens.BasicTab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.signlanguage.R;

public class Basic_Tab_Activity extends AppCompatActivity {
    RecyclerView rcViewBasicDetail;
    BasicDetailAdapter basicDetailAdapterAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_tab_detail);

        rcViewBasicDetail = findViewById(R.id.recyclerViewBasicTabDetail);
        rcViewBasicDetail.setLayoutManager(new LinearLayoutManager(this));


        basicDetailAdapterAdapter = new BasicDetailAdapter(this);
       // basicDetailAdapterAdapter.setOnClick();
        rcViewBasicDetail.setAdapter(basicDetailAdapterAdapter);



    }



}
