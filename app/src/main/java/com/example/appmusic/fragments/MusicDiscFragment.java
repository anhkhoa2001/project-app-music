package com.example.appmusic.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.appmusic.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class MusicDiscFragment extends Fragment {

    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;
    long playTime;
    String img;

    public MusicDiscFragment(String img) {
        this.img = img;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_disc, container, false);
        circleImageView = view.findViewById(R.id.img_dianhac);
        playMusic(img);
        objectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0f, 360f);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

        return view;
    }

    public void playMusic(String img) {
        try {
            new LoadImageURL(circleImageView).execute(img);
        } catch (Exception e) {
            circleImageView.setImageResource(R.drawable.iconfloatingactionbutton);
        };
    }

    public void stopDisc() {
        playTime = objectAnimator.getCurrentPlayTime();
        objectAnimator.cancel();
    }

    public void startDisc() {
        objectAnimator.setCurrentPlayTime(playTime);
        objectAnimator.start();

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