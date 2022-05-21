package com.example.appmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appmusic.Adapter.PlayMusicViewPagerAdapter;
import com.example.appmusic.Fragment.MusicDiscFragment;
import com.example.appmusic.Fragment.PlayListFragment;
import com.example.appmusic.Model.Music;
import com.example.appmusic.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayMusicActivity extends Base implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, View.OnTouchListener {

    Toolbar toolbar;
    TextView txtTime, txtTotalTime;
    SeekBar seekBar;
    ImageButton btnRandom, btnPreview, btnPlay, btnNext, btnRepeat;
    ViewPager2 viewPager;
    MediaPlayer mediaPlayer;
    MusicDiscFragment fragment_dia_nhac;
    PlayListFragment fragment_play_danhsach_baihat;
    public static List<Music> mangBaiHat = new ArrayList<>();
    public static PlayMusicViewPagerAdapter adapternhac;
    int position = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;
    private int mediaFileLengthInMilliseconds;
    String URL_MUSIC;

    private final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GetDataFromIntent();
        setID();
//        eventClick();
/*        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Music_Source")) {
                URL_MUSIC = intent.getStringExtra("Music_Source");
            }
        }*/
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource("http://192.168.2.105:8080/sounds/vn/Bray%20-%20Con%20trai%20cưng.mp3");
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
        Log.v("Music", "duration   " + mediaFileLengthInMilliseconds);
        txtTotalTime.setText(convertMillisecondsToMinutes(mediaFileLengthInMilliseconds));

        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.iconplay);
        }else {
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.iconpause);
        }

        primarySeekBarProgressUpdater();

        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    public String convertMillisecondsToMinutes(int miniseconds) {
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(miniseconds);
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(miniseconds)%60;

        return minutes + ":" + String.format("%02d", seconds);
    }

    private void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100));

        Log.v("Music", "time current" + mediaPlayer.getCurrentPosition()/1000 + 1);
        txtTime.setText(convertMillisecondsToMinutes(mediaPlayer.getCurrentPosition()));
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }
    }


    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangBaiHat.clear();
    }

    private void setID() {
        toolbar = findViewById(R.id.toolbar_play_nhac);
        seekBar = findViewById(R.id.seekbar_song);
        txtTime = findViewById(R.id.txt_time_song);
        txtTotalTime = findViewById(R.id.txt_total_time_song);
        btnRandom = findViewById(R.id.btn_ngaunhien);
        btnPreview = findViewById(R.id.btn_preview);
        btnPlay = findViewById(R.id.btn_play);
        btnNext = findViewById(R.id.btn_next);
        btnRepeat = findViewById(R.id.btn_repeat);
        viewPager = findViewById(R.id.viewpaper_play_nhac);


        adapternhac = new PlayMusicViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

        viewPager.setAdapter(adapternhac);
//        fragment_dia_nhac = (MusicDiscFragment) getSupportFragmentManager().findFragmentByTag("f" + position);


        seekBar.setOnTouchListener(this);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.seekbar_song){
             if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar) view;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }
}