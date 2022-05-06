package com.example.appmusic.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.Fragment.HomeFragment;
import com.example.appmusic.Fragment.SearchFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {


    private ArrayList<Fragment> fragmentArray = new ArrayList<>();
    private ArrayList<String> titleArray = new ArrayList<>();

    public MainViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 1:
                return new SearchFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }





}
