package com.example.appmusic.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.appmusic.Activity.Base;
import com.example.appmusic.R;


public class UserFragment extends Fragment {
    FrameLayout layoutUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //kiểu tra điều kiện đã đăng nhập hay chưa
        if (Base.isLogged()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_user, new LoggedFragment())
                    .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_user, new LoginFragment())
                    .commit();
        }

    }
}