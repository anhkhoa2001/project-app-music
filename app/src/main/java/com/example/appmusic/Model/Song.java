package com.example.appmusic.Model;

public class Song {
    private int id;
    private String name;
    private String image;
    private String singer;
    private String link;
    private String like;
    private int tracks;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public Song(int id, String name, String image, String singer) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.singer = singer;
        this.link = link;
        this.like = like;
    }
}
