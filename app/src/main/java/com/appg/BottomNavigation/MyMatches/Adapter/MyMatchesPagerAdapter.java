package com.appg.BottomNavigation.MyMatches.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appg.BottomNavigation.MyMatches.Cricket.MyMatchesCricket;
import com.appg.BottomNavigation.MyMatches.FruitNinja.MyMatchesFruitNinja;


public class MyMatchesPagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public MyMatchesPagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                return new MyMatchesCricket();
            case 1:
                return new MyMatchesFruitNinja();

            default: return null;

        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}


