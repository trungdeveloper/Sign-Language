package com.example.signlanguage.Fragment.DictionaryTab;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.TabDetail.ResultTabActivity;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Subcategory;
import com.example.signlanguage.model.Tab;

import java.util.ArrayList;
import java.util.List;

public class DictionaryTabFragment extends Fragment implements DictionaryAdapter.OnItemClicked {
    private View rootView;
    RecyclerView recyclerViewTab;
    DictionaryAdapter dictionaryAdapter;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_dictionary_tab, container, false);
        recyclerViewTab = rootView.findViewById(R.id.itemDictionary);
        recyclerViewTab.setLayoutManager(new LinearLayoutManager(getActivity()));
        getItemTab();
        return rootView;
    }


    private void getItemTab(){
        final VolleyApi volley =new VolleyApi(getContext());
        String urlJsonArryCategoty = "http://signlanguage.somee.com/api/dictionary";
        volley.getSubcategoryData(urlJsonArryCategoty, new VolleyApi.OnSubCategoryResponse() {
            @Override
            public void OnSubCategoryResponse(List<Subcategory> subcategories) {
                dictionaryAdapter = new DictionaryAdapter(this, subcategories);
                recyclerViewTab.setAdapter(dictionaryAdapter);
                dictionaryAdapter.setOnClick(DictionaryTabFragment.this);
            }
        });

    }

    @Override
    public void onClickResultItem(int position) {
        Intent intent = new Intent(getActivity(), ResultTabActivity.class);
        intent.putExtra("subCategory_ID",dictionaryAdapter.TabsItem.get(position).getId());
        intent.putExtra("keyword",dictionaryAdapter.TabsItem.get(position).getKeyword());
        intent.putExtra("image",dictionaryAdapter.TabsItem.get(position).getImage());
        intent.putExtra("video",dictionaryAdapter.TabsItem.get(position).getVideo());
        startActivity(intent);
    }
}
