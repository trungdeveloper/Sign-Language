package com.example.signlanguage;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.signlanguage.Fragment.BasicTabFragment;
import com.example.signlanguage.Fragment.DictionaryTabFragment;
import com.example.signlanguage.Fragment.TutorialTabFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public PageAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                BasicTabFragment basicTab = new BasicTabFragment();
                return basicTab;
            case 1:
                TutorialTabFragment tutorialTab = new TutorialTabFragment();
                return tutorialTab;
            case 2:
                DictionaryTabFragment dictionaryTab = new DictionaryTabFragment();
                return dictionaryTab;
             default:
                 return null;

        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }


}