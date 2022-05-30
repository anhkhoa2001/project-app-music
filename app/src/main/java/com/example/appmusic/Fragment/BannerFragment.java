package com.example.appmusic.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmusic.API.MusicApi;
import com.example.appmusic.Adapter.BannerAdapter;
import com.example.appmusic.Model.MusicGenre;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class BannerFragment extends Fragment {

    List<MusicGenre> banners = new ArrayList<>();
    BannerAdapter bannerAdapter;
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    Runnable runnable;
    Handler handler;
    int currentItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        viewPager = view.findViewById(R.id.view_pager);
        circleIndicator = view.findViewById(R.id.indicator_banner);
        new GetAllTask().execute("/music-genre/top");
        return view;
    }

    private class GetAllTask extends AsyncTask<String, Void, List<MusicGenre>> {
        protected ProgressDialog dialog;
        public GetAllTask(){
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<MusicGenre> doInBackground(String... params) {
            try {
                return (List<MusicGenre>) MusicApi.getAllMusicGenre((String) params[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<MusicGenre> result) {
            super.onPostExecute(result);
            banners = result;
            Log.v("Music","Size in post" + banners);
            bannerAdapter = new BannerAdapter(getActivity(), banners);
            viewPager.setAdapter(bannerAdapter);
            circleIndicator.setViewPager(viewPager);
            handler = new Handler();
            runnable = () -> {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if (currentItem>= viewPager.getAdapter().getCount()){
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable,4500);
            };
            handler.postDelayed(runnable,4500);
        }
    }


}