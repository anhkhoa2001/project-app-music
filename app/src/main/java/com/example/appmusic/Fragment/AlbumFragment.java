package com.example.appmusic.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.API.MusicApi;
import com.example.appmusic.Adapter.AlbumAdapter;
import com.example.appmusic.Model.Singer;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    TextView albumMore;
    List<Singer> albums = new ArrayList<>();
    AlbumAdapter albumAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_album);
        albumMore = view.findViewById(R.id.album_more);
        new GetAllTask().execute("/singer/album", "3");

        return view;
    }

    private class GetAllTask extends AsyncTask<String, Void, List<Singer>> {
        public GetAllTask(){
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<Singer> doInBackground(String... params) {
            try {
                return (List<Singer>) MusicApi.getAllAlbum((String) params[0], (String) params[1]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Singer> result) {
            super.onPostExecute(result);
            Log.v("Music","Size in album create view" + result.size());
            albums = result;
            albumAdapter = new AlbumAdapter(getActivity(), albums);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(albumAdapter);
        }
    }
}