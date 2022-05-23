package com.example.appmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appmusic.Adapter.PlayMusicViewPagerAdapter;
import com.example.appmusic.Fragment.MusicDiscFragment;
import com.example.appmusic.Fragment.PlayListFragment;
import com.example.appmusic.Model.Music;
import com.example.appmusic.R;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private String URL_MUSIC;
    private String URL_MUSIC_PRIOR;
    private String URL_MUSIC_AFTER;
    //true la nhac dang duoc mo

    private final Handler handler = new Handler();

    @Override
    public void onBackPressed() {
        Log.v("Music", "you clicked button back");
        mediaPlayer.pause();
        Intent intent = new Intent(this, SongListActivity.class);
        intent.putExtra(getIntent().getStringExtra("Type_ID"), getIntent().getIntExtra("Music_ID", 0));
        this.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GetDataFromIntent();
        setID();
//        eventClick();
        Intent intent = getIntent();
        if (intent != null) {
            URL_MUSIC = intent.hasExtra("Music_Source") ? intent.getStringExtra("Music_Source") : null;
            URL_MUSIC_PRIOR = intent.hasExtra("Music_Source_Prior") ? intent.getStringExtra("Music_Source_Prior") : null;
            URL_MUSIC_AFTER = intent.hasExtra("Music_Source_After") ? intent.getStringExtra("Music_Source_After") : null;
        }
        mediaPlayer = new MediaPlayer();

        try {
            Log.v("Music", "name music 1  " + URL_MUSIC);
            Log.v("Music", "name music pri  " + URL_MUSIC_PRIOR);
            Log.v("Music", "name music after  " + URL_MUSIC_AFTER);
            mediaPlayer.setDataSource(URL_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
        txtTotalTime.setText(convertMillisecondsToMinutes(mediaFileLengthInMilliseconds));

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.iconplay);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.iconpause);
                }
            }
        });

        seekBar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                Log.v("Music", mediaPlayer.getCurrentPosition() + "===");

                return true;
            }
        });


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
        eventHandlerRepeat();
        txtTime.setText(convertMillisecondsToMinutes(mediaPlayer.getCurrentPosition()));
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        } else {
            btnPlay.setImageResource(R.drawable.iconplay);
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
        btnPlay.setImageResource(R.drawable.iconpause);

        viewPager.setAdapter(adapternhac);
//        fragment_dia_nhac = (MusicDiscFragment) getSupportFragmentManager().findFragmentByTag("f" + position);

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat) {
                    repeat = false;
                } else {
                    repeat = true;
                }
                float deg = btnRepeat.getRotation() + 90F;
                btnRepeat.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            }
        });

        seekBar.setOnTouchListener(this);
    }

    public void eventHandlerRepeat() {
        if(repeat && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.iconpause);
        } else {

        }
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