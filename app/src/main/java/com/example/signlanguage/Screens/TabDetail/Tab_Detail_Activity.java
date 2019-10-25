package com.example.signlanguage.Screens.TabDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.signlanguage.R;

public class Tab_Detail_Activity extends AppCompatActivity {
    RecyclerView rcViewBasicDetail;
    TABDetailAdapter basicDetailAdapterAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_detail);

        rcViewBasicDetail = findViewById(R.id.recyclerViewBasicTabDetail);
        rcViewBasicDetail.setLayoutManager(new LinearLayoutManager(this));


        basicDetailAdapterAdapter = new TABDetailAdapter(this);
       // basicDetailAdapterAdapter.setOnClick();
        rcViewBasicDetail.setAdapter(basicDetailAdapterAdapter);
    }

}
