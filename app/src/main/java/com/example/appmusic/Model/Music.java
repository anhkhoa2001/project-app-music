package com.example.appmusic.Model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Music {

    private int id;
    private String name;
    private String source;
    private int musicTracks;//tinh theo s
    private String image;
    private List<Singer> singers;
    private int listens;
    private int likes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getMusicTracks() {
        return musicTracks;
    }

    public void setMusicTracks(int musicTracks) {
        this.musicTracks = musicTracks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public int getListens() {
        return listens;
    }

    public void setListens(int listens) {
        this.listens = listens;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Music() {
    }

    public String singersToString() {
        List<String> nameSingers = new ArrayList<>();
        for(Singer singer:singers) {
            nameSingers.add(singer.getName());
        }
        return TextUtils.join(" ft ", nameSingers);
    }

}
