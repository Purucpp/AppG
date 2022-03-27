package com.appg.BottomNavigation.MyMatches.Cricket.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appg.BottomNavigation.MyMatches.Cricket.Completed.Cricket_Completed;
import com.appg.BottomNavigation.MyMatches.Cricket.Live.Cricket_Live;
import com.appg.BottomNavigation.MyMatches.Cricket.MyMatchesCricket;
import com.appg.BottomNavigation.MyMatches.Cricket.Upcoming.Cricket_Upcoming;
import com.appg.BottomNavigation.MyMatches.FruitNinja.MyMatchesFruitNinja;




public class CricketAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public CricketAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                return new Cricket_Upcoming();
            case 1:
                return new Cricket_Live();
            case 2:
                return new Cricket_Completed();

            default: return null;

        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}


