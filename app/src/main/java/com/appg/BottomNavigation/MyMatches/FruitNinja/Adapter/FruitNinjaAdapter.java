package com.appg.BottomNavigation.MyMatches.FruitNinja.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appg.BottomNavigation.MyMatches.Cricket.Completed.Cricket_Completed;
import com.appg.BottomNavigation.MyMatches.Cricket.Live.Cricket_Live;
import com.appg.BottomNavigation.MyMatches.Cricket.Upcoming.Cricket_Upcoming;
import com.appg.BottomNavigation.MyMatches.FruitNinja.Completed.FruitNinja_Completed;
import com.appg.BottomNavigation.MyMatches.FruitNinja.Live.FruitNinja_Live;
import com.appg.BottomNavigation.MyMatches.FruitNinja.Upcoming.FruitNinja_Upcoming;


public class FruitNinjaAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public FruitNinjaAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                return new FruitNinja_Upcoming();
            case 1:
                return new FruitNinja_Live();
            case 2:
                return new FruitNinja_Completed();

            default: return null;

        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}


