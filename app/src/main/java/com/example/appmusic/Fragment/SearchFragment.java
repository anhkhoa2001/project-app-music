package com.example.appmusic.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.appmusic.API.DonationApi;
import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Adapter.SongListAdapter;
import com.example.appmusic.Model.Music;
import com.example.appmusic.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SearchFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<Music> musics;
    private SongListAdapter songListAdapter;
    Context context;
    EditText eventSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_album);
        context = getActivity();
        eventSearch = view.findViewById(R.id.eventSearch);

        new GetAllTask().execute("/music");

        eventSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v("Music", "khoa dam tam" + filterListMusics(musics, charSequence.toString()).size());

                songListAdapter = new SongListAdapter(context, filterListMusics(musics, charSequence.toString()), 0, null);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(songListAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    public List<Music> filterListMusics(List<Music> musics, String key) {
        List<Music> newMusics = new ArrayList<>();

        for (Music music:musics) {
           if(unAccent(music.getName().toLowerCase()).contains(key.toLowerCase())) {
               newMusics.add(music);
           }
        }
        return newMusics;
    }

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "");
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
                return (List<Music>) DonationApi.getAllMusic((String) params[0]);
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
            songListAdapter = new SongListAdapter(context, musics, 0, null);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(songListAdapter);

        }
    }
}