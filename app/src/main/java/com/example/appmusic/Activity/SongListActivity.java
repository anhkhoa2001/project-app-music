package com.example.appmusic.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.appmusic.API.DonationApi;
import com.example.appmusic.Adapter.BannerAdapter;
import com.example.appmusic.Adapter.SongListAdapter;
import com.example.appmusic.Model.Music;
import com.example.appmusic.Model.MusicGenre;
import com.example.appmusic.Model.Song;
import com.example.appmusic.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SongListActivity extends AppCompatActivity {
    private int id;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingtoolbarLayout;
    private RecyclerView recyclerviewSongList;
    private List<Music> songList = new ArrayList<>();
    private String name;
    private SongListAdapter songListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        collapsingtoolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerviewSongList = findViewById(R.id.recyclerView_SongList);

        //set background
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Source")) {
                String source = intent.getStringExtra("Source");
                // Truy Vấn DB với id

                //source là link ảnh
                //set source vào collapsingtoolbarLayout
            }
        }
        getDataIntent(intent);
        collapsingtoolbarLayout.setBackgroundResource(R.drawable.mot_phut_banner);

    }

    public void getDataIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("ID")) {
                id = intent.getIntExtra("ID", 0);
                // Truy Vấn DB với id
                new GetAllTask().execute("/music/by-genre-id", String.valueOf(id));
            } else {
                id = intent.getIntExtra("Album_ID", 0);
                // Truy Vấn DB với id
                new GetAllTask().execute("/music/by-singer-id", String.valueOf(id));
            }
        }
    }

    private class GetAllTask extends AsyncTask<String, Void, List<Music>> {
        protected ProgressDialog dialog;
        public GetAllTask(){
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<Music> doInBackground(String... params) {
            try {
                return (List<Music>) DonationApi.getAllMusicById((String) params[0],(String) params[1]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Music> result) {
            super.onPostExecute(result);

            songList = result;
            Log.v("Music", "song list size" + songList);
            songListAdapter = new SongListAdapter(SongListActivity.this,songList);
            recyclerviewSongList.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
            recyclerviewSongList.setAdapter(songListAdapter);
        }
    }

    private class LoadImageURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImageURL(ImageView rs) {
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