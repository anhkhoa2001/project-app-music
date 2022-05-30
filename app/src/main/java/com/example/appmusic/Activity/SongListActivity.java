package com.example.appmusic.Activity;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.appmusic.API.MusicApi;
import com.example.appmusic.Adapter.AlbumAdapter;
import com.example.appmusic.Adapter.SongListAdapter;
import com.example.appmusic.Model.Music;
import com.example.appmusic.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SongListActivity extends Base {
    private int id;
    private String type_id;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingtoolbarLayout;
    private RecyclerView recyclerviewSongList;
    private List<Music> songList = new ArrayList<>();
    private String name;
    private SongListAdapter songListAdapter;
    private ImageView collapView;

    @Override
    public void onBackPressed() {
        Log.v("Music", "you clicked button back");
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        collapsingtoolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerviewSongList = findViewById(R.id.recyclerView_SongList);
        collapView = findViewById(R.id.collap_view);

        //set background
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Source")) {
                String source = intent.getStringExtra("Source");
                Log.v("Music", "collap source" + source);
                // Truy Vấn DB với id

                //source là link ảnh
                //set source vào collapsingtoolbarLayout
                new LoadImageURL(collapView).execute(source);
            }
        }
        getDataIntent(intent);
    }

    public void getDataIntent(Intent intent) {

        if (intent != null) {
            if (intent.hasExtra("Type_ID")) {
                type_id = intent.getStringExtra("Type_ID");
                id = intent.getIntExtra(intent.getStringExtra("Type_ID"), 0);
            }

            if (intent.hasExtra("ID")) {
                id = intent.getIntExtra("ID", 0);
                type_id = "ID";
                // Truy Vấn DB với id
                new GetAllTask().execute("/music/by-genre-id", String.valueOf(id));
            } else {
                type_id = "Album_ID";
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
                return (List<Music>) MusicApi.getAllMusicById((String) params[0],(String) params[1]);
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
            songListAdapter = new SongListAdapter(SongListActivity.this, songList, id, type_id);
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