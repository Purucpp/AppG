package com.appg.BottomNavigation.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.appg.BottomNavigation.Home.Adapter.PagerAdapter;
import com.appg.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class BottomHome extends Fragment  {

    ViewPager pager;
    TabLayout mTabLayout;
    TabItem firstItem,secondItem;
    PagerAdapter adapter;

    int i=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_home, container, false);

        pager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tablayout);

        firstItem = view.findViewById(R.id.firstItem);
        secondItem = view.findViewById(R.id.secondItem);



        this.adapter = new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTabLayout.getTabCount());
        pager.setAdapter(adapter);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                pager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //pager.setCurrentItem(tab.getPosition());



            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

               // pager.setCurrentItem(tab.getPosition());



            }
        });


        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        return view;
    }



}
