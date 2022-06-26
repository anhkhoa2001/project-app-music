package com.example.appmusic.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.appmusic.R;
import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;
import com.example.appmusic.models.MusicOnDevice;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class MusicDiscFragment extends Fragment {
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;
    AMusic music;

    public MusicDiscFragment(AMusic music) {
        this.music = music;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_disc, container, false);
        circleImageView = view.findViewById(R.id.img_dianhac);
        playMusic(music);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        return view;
    }

    public void playMusic(AMusic music) {
        Log.v("Music", "check view" + circleImageView);
        Bitmap bitmap = null;
        if(music.isType()) {
            try {
                InputStream inputStream = new URL(music.getImage()).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                circleImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            circleImageView.setImageResource(Integer.parseInt(music.getImage()));
        }
    }

    private class LoadImageURL extends AsyncTask<String, Void, Bitmap> {
        CircleImageView imageView;

        public LoadImageURL(CircleImageView rs) {
            this.imageView = rs;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(strings[0]).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}