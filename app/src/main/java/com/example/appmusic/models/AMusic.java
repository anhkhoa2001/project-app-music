package com.example.appmusic.models;

public abstract class AMusic {

    private int id;
    private String name;
    private int musicTracks;//tinh theo s
    private String image;
    private int listens;
    private int likes;
    private boolean type;
    private String singer;

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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
