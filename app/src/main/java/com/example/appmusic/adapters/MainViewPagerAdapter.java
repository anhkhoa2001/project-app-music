package com.example.appmusic.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.fragments.HomeFragment;
import com.example.appmusic.fragments.SearchFragment;
import com.example.appmusic.fragments.UserFragment;

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
                return new UserFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
