package com.example.appmusic.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.appmusic.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class MusicDiscFragment extends Fragment {

    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_disc, container, false);
        circleImageView = view.findViewById(R.id.img_dianhac);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return view;
    }

    public void playMusic(String image) {
        circleImageView.setImageResource(R.drawable.mot_phut_banner);
    }

    public void stopDisc() {
        objectAnimator.cancel();
    }

    public void startDisc() {
        objectAnimator.start();
    }
}