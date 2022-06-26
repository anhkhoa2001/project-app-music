package com.example.appmusic.models;

public class MusicOnDB extends AMusic {

    private boolean status;
    private String singer;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
