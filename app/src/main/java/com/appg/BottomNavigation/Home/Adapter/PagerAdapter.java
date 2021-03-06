package com.appg.BottomNavigation.Home.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appg.BottomNavigation.Home.Cricket.First;
import com.appg.BottomNavigation.Home.FruitNinja.Second;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new First();
            case 1:
                return new Second();

            default: return null;


        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }



}
