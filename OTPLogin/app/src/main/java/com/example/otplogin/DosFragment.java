package com.example.otplogin;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


public class DosFragment extends Fragment {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    public DosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_two, container, false);

        tabLayout = (TabLayout)RootView.findViewById(R.id.tablayout_id);
        //appBarLayout = (AppBarLayout) RootView.findViewById(R.id.appBarID);
        viewPager = (ViewPager) RootView.findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.AddFragment(new RechargeRecommendedFragment(), "Recomended Recharge");
        viewPagerAdapter.AddFragment(new RechargeUnlimitedFragment(), "Unlimited Recharge");
        viewPagerAdapter.AddFragment(new RechargeAllRounderFragment(), "All Rounder Recharge");
        viewPagerAdapter.AddFragment(new RechargeDataFragment(), "Data Recharge");
        viewPagerAdapter.AddFragment(new RechargeGroupFragment(), "Group Recharge");

        // Adding fragment to adapter
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return RootView;
    }



}
