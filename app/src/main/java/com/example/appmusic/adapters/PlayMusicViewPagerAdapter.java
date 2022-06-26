package com.example.appmusic.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appmusic.fragments.MusicDiscFragment;
import com.example.appmusic.fragments.PlayListFragment;


public class PlayMusicViewPagerAdapter extends FragmentStateAdapter {

    public MusicDiscFragment musicDiscFragment;
    public PlayListFragment playListFragment;

    public PlayMusicViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                                     MusicDiscFragment musicDiscFragment, PlayListFragment playListFragment) {
        super(fragmentManager, lifecycle);
        this.musicDiscFragment = musicDiscFragment;
        this.playListFragment = playListFragment;
    }

//    public PlayMusicViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return playListFragment;
            default:
                return musicDiscFragment;
        }
    }

    public MusicDiscFragment getMusicDiscFragment() {
        return musicDiscFragment;
    }

    public void setMusicDiscFragment(MusicDiscFragment musicDiscFragment) {
        this.musicDiscFragment = musicDiscFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
