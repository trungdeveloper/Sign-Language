package com.example.signlanguage.Fragment.TutorialTab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.TabDetail.Tab_Detail_Activity;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Tab;

import java.util.List;

public class TutorialTabFragment extends Fragment  implements TutorialAdapter.OnItemClicked {
    private View rootView;
    RecyclerView recyclerViewTab;
    TutorialAdapter tutorialAdapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_tutotials_tab, container, false);

        recyclerViewTab = rootView.findViewById(R.id.ItemTutorial);
        recyclerViewTab.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getItemTab();
    }

    private void getItemTab(){
        VolleyApi volley =new VolleyApi(getContext());
        String urlJsonArryCategoty = "http://signlanguage.somee.com/api/categories/2d897d69-4bac-455a-b96f-2851b6d91142";

        volley.makeObjectArrayRequest(urlJsonArryCategoty, new VolleyApi.OnTabResponse() {
            @Override
            public void onResponse(List<Tab> tabs) {
                tutorialAdapter = new TutorialAdapter(this, tabs);
                recyclerViewTab.setAdapter(tutorialAdapter);
                tutorialAdapter.setOnClick(TutorialTabFragment.this);
            }


        });
    }

    @Override
    public void onClickDetailTab(int position) {
        Intent intent = new Intent(getActivity(), Tab_Detail_Activity.class);
        startActivity(intent);
    }

}
