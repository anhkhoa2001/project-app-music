package com.example.appmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appmusic.API.DonationApi;
import com.example.appmusic.Adapter.PlayMusicViewPagerAdapter;
import com.example.appmusic.Fragment.MusicDiscFragment;
import com.example.appmusic.Fragment.PlayListFragment;
import com.example.appmusic.Model.Music;
import com.example.appmusic.Model.MusicGenre;
import com.example.appmusic.Model.Song;
import com.example.appmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GetDataFromIntent();
        setID();
//        eventClick();
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

    }



}