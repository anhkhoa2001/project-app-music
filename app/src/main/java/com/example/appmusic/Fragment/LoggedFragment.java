package com.example.appmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.appmusic.Activity.Base;
import com.example.appmusic.R;

public class LoggedFragment extends Fragment {

    Button logged;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logged, container, false);

        logged = view.findViewById(R.id.logged);

        logged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Base.token = null;
                Intent intent = new Intent(getActivity(), LoginFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
