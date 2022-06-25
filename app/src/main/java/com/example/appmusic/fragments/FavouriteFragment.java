package com.example.appmusic.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.activities.Base;
import com.example.appmusic.adapters.SongListAdapter;
import com.example.appmusic.api.MusicApi;
import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<Music> musics;
    private SongListAdapter songListAdapter;
    Context context;
    ImageButton btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = view.findViewById(R.id.device_recycler);
        context = getActivity();
        btnBack = view.findViewById(R.id.device_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoggedFragment nextFrag= new LoggedFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_user, nextFrag)
                        .commit();
            }
        });

        new GetAllTaskByToken().execute("/user/get-by-token", Base.token);

        return view;
    }

    private class GetAllTaskByToken extends AsyncTask<String, Void, List<Music>> {
        protected ProgressDialog dialog;
        public GetAllTaskByToken(){
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("In progress taking....");
            this.dialog.show();
        }
        @Override
        protected List<Music> doInBackground(String... params) {
            try {
                return (List<Music>) MusicApi.getAllMusicByToken((String) params[0], (String) params[1]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Music> result) {
            super.onPostExecute(result);
            Log.v("Music", result.size() + "=====size");
            musics = result;
            List<AMusic> aMusics = new ArrayList<>();
            aMusics.addAll(musics);

            songListAdapter = new SongListAdapter(context, aMusics, 0, null);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(songListAdapter);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }
}
