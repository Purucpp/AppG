package com.appg.BottomNavigation.MyMatches.FruitNinja;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appg.BottomNavigation.MyMatches.Cricket.Adapter.CricketAdapter;
import com.appg.BottomNavigation.MyMatches.FruitNinja.Adapter.FruitNinjaAdapter;
import com.appg.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MyMatchesFruitNinja extends Fragment {


    ViewPager pager;
    TabLayout mTabLayout;
    TabItem firstItem,secondItem,thirdIteam;
    FruitNinjaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_my_matches_fruit_ninja, container, false);




        pager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tablayout);

        firstItem = view.findViewById(R.id.firstItem);
        secondItem = view.findViewById(R.id.secondItem);
        thirdIteam=view.findViewById(R.id.thirdItem);




        adapter = new FruitNinjaAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTabLayout.getTabCount());
        pager.setAdapter(adapter);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                pager.setCurrentItem(tab.getPosition());

//                Toast.makeText(getActivity(), "This is my Toast message!",
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //  pager.setCurrentItem(tab.getPosition());



            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                //  pager.setCurrentItem(tab.getPosition());



            }
        });


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));



        return view;
    }
}