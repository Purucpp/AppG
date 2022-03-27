package com.appg.BottomNavigation.Winner.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appg.BottomNavigation.Winner.Cricket.Winner_Cricket;
import com.appg.BottomNavigation.Winner.FruitNinja.Winner_FruitNinja;


public class WinnerPageAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public WinnerPageAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {





        switch (position){
            case 0:
                return new Winner_Cricket();
            case 1:
                return new Winner_FruitNinja();

            default: return null;


        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
