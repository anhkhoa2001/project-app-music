package com.example.appmusic.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.appmusic.R;
import com.example.appmusic.activities.Base;
import com.example.appmusic.models.AMusic;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class PlayBarFragment extends Fragment {
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;
    ImageButton btnPreview, btnPlay, btnNext;
    TextView songName;
    private AMusic aMusic;

    public PlayBarFragment() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_bar, container, false);
        if(Base.getAll().size() > 0) {
            init();
        }

        return view;
    }

    private void init() {
        Base.getMusic();
        aMusic = Base.musicStatic;
        circleImageView = view.findViewById(R.id.img_dianhac);
//        playMusic(img);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());

        btnPreview = view.findViewById(R.id.btn_preview);
        btnPlay = view.findViewById((R.id.btn_play));
        btnNext = view.findViewById((R.id.btn_next));
        songName = view.findViewById((R.id.song_name));
        songName.setText(aMusic.getName());
        Bitmap bitmap = null;
        if(aMusic.isType()) {
            try {
                InputStream inputStream = new URL(aMusic.getImage()).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(aMusic.getImage()));
        }
        circleImageView.setImageBitmap(bitmap);
    }

}