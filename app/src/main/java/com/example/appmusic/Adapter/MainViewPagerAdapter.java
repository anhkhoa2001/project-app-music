package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.Activity.Base;
import com.example.appmusic.Fragment.HomeFragment;
import com.example.appmusic.Fragment.LoginFragment;
import com.example.appmusic.Fragment.SearchFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    private Context context;

    private ArrayList<Fragment> fragmentArray = new ArrayList<>();
    private ArrayList<String> titleArray = new ArrayList<>();

    public MainViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new SearchFragment();
            case 2:
                return new LoginFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
