package com.example.appmusic.models;

import java.util.List;

public class Singer {

    private int id;
    private String name;
    List<Music> musics;

    public Singer() {

    }

    public List<Music> getMusics() {
        return musics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public Singer(String name, List<Music> musics) {
        this.name = name;
        this.musics = musics;
    }
}
