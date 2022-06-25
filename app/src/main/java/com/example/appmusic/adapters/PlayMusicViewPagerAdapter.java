package com.example.appmusic.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.fragments.MusicDiscFragment;


public class PlayMusicViewPagerAdapter extends FragmentStateAdapter {

    public PlayMusicViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
/*            case 0:
                return new PlayListFragment();*/
            default:
                return new MusicDiscFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
