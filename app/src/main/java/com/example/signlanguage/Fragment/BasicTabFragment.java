package com.example.signlanguage.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.signlanguage.R;
import com.example.signlanguage.Screens.AlphabetActivity;

public class BasicTabFragment extends Fragment {
    private View rootView;
    ImageView basicTab;



    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_basic_tab, container, false);

        basicTab = rootView.findViewById(R.id.alphabet);
        basicTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlphabetActivity();
            }
        });


        return rootView;
    }
    public void showAlphabetActivity (){
        Intent intent = new Intent(getActivity(), AlphabetActivity.class);
        startActivity(intent);
    }






}
