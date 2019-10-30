package com.example.signlanguage.Fragment.BasicTab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.TabDetail.TabDetailActivity;
import com.example.signlanguage.model.Tab;
import com.example.signlanguage.VolleyApi;

import java.util.List;

public class BasicTabFragment extends Fragment implements BasicAdapter.OnItemClicked {
    private View rootView;
    RecyclerView recyclerViewTab;
    BasicAdapter basicAdapter;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_basic_tab, container, false);

        recyclerViewTab = rootView.findViewById(R.id.ItemBasic);
        recyclerViewTab.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getItemTab();
        return rootView;
    }

    private void getItemTab(){
        VolleyApi volley =new VolleyApi(getContext());
        String urlJsonArryCategoty = "http://signlanguage.somee.com/api/categories/bf516b98-5bf4-4f14-b467-87f7bafca53e";

        volley.makeObjectArrayRequest(urlJsonArryCategoty, new VolleyApi.OnTabResponse() {
            @Override
            public void onResponse(List<Tab> tabs) {
                basicAdapter = new BasicAdapter(this, tabs);
                recyclerViewTab.setAdapter(basicAdapter);
                basicAdapter.setOnClick(BasicTabFragment.this);

            }
        });
    }

    @Override
    public void onClickDetailTab(int position) {
        Intent intent = new Intent(getActivity(), TabDetailActivity.class);
        intent.putExtra("subCategory_ID",basicAdapter.TabsItem.get(position).getId());
        startActivity(intent);
    }
}
