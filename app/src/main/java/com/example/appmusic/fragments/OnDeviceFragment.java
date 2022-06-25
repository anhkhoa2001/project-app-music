package com.example.appmusic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.adapters.SongListAdapter;
import com.example.appmusic.models.Music;

import java.util.List;

public class OnDeviceFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<Music> music;
    private SongListAdapter songListAdapter;
    Context context;
    ImageButton btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_on_device, container, false);
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

        return view;
    }

    public List<Music> getAllMusicInFolder() {

        return null;
    }

}
