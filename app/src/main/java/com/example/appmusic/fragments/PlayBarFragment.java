package com.example.appmusic.fragments;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.appmusic.R;
import com.example.appmusic.models.AMusic;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PlayBarFragment extends Fragment {
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;
    ImageButton btnPreview, btnPlay, btnNext;
    TextView songName;
    boolean isPlaying = false;
    long playTime = 0;
    private AMusic aMusic;
    private boolean mBound;
    private Intent intentInService;
    private int media_length = 0;
    private boolean isPlay = false;
    private List<AMusic> aMusics;
    private int music_id;

    public PlayBarFragment() {
    }

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
/*        init();*/

        return view;
    }

/*    private void init() {
        Base.getMusic();
        aMusic = Base.musicStatic;
        aMusics = Base.getAll();
        music_id = aMusic.getId();
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
        if(aMusic.isType()) {
            new LoadImageURL(circleImageView).execute(aMusic.getImage());
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(aMusic.getImage()));
            circleImageView.setImageBitmap(bitmap);
        }
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMusicNotiPre();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMusicNotiNext();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    btnPlay.setImageResource(R.drawable.iconplay);
                    onMusicNotiPause();
                } else {
                    btnPlay.setImageResource(R.drawable.ic_pause_white);
                    onMusicNotiPlay();
                }
            }
        });
    }*/

/*    @Override
    public void onMusicNotiPre() {
        Log.v("Music", "da click vao day roi nha");
        music_id = music_id != 0 ? --music_id : aMusics.size() - 1;;
        aMusic = aMusics.get(music_id);
        mService.getPlayer().reset();
        runMusic(aMusic);
        isPlaying = true;
        btnPlay.setImageResource(R.drawable.ic_pause_white);
    }

    public void runMusic(AMusic music) {
        if(music.isType()) {
            try {
                mService.startMedia(music.getSource());
                new LoadImageURL(circleImageView).execute(music.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(getContext(), Integer.parseInt(music.getSource())));
            mService.getPlayer().start();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(music.getImage()));
            circleImageView.setImageBitmap(bitmap);
        }
        songName.setText(music.getName());
    }

    @Override
    public void onMusicNotiPlay() {
        Log.v("Music", "da click vao day roi nha pause");
        objectAnimator.setCurrentPlayTime(playTime);
        objectAnimator.start();
        if(isPlay) {
            mService.getPlayer().seekTo(media_length);
            mService.getPlayer().start();
        } else {
            if(aMusic.isType()) {
                try {
                    mService.startMedia(aMusic.getSource());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mService.setPlayer(MediaPlayer.create(getContext(), Integer.parseInt(aMusic.getSource())));
                mService.getPlayer().start();
            }
            isPlay = true;
        }
        isPlaying = true;
    }

    @Override
    public void onMusicNotiPause() {
        Log.v("Music", "da click vao day roi nha play");
        playTime = objectAnimator.getCurrentPlayTime();
        objectAnimator.cancel();
        mService.getPlayer().pause();
        media_length = mService.getPlayer().getCurrentPosition();
        isPlaying = false;
    }

    @Override
    public void onMusicNotiNext() {
        music_id = music_id < aMusics.size() - 1 ? ++music_id : 0;
        aMusic = aMusics.get(music_id);
        mService.getPlayer().reset();
        runMusic(aMusic);
        isPlaying = true;
        btnPlay.setImageResource(R.drawable.ic_pause_white);
    }

    public void createNewNotification(AMusic music, int icon) {
        CreateNotification.createNotification(getContext(), music, icon, 1,
                10);
    }*/

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