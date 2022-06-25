package com.example.appmusic.models;

public class MusicOnDevice extends AMusic {
    private String singer;

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String convertToElementString() {
        return this.getId() + " =-= " + this.getName() + " =-= " + this.getSource() + " =-= " + this.getMusicTracks()
                + " =-= " + this.getImage() + " =-= " + this.getSinger() + " =-= " + this.getListens()
                + " =-= " + this.getLikes() + " =-= " + this.isType();
    }

    public MusicOnDevice(int id, String name, int source, int musicTracks, int image, String singer) {
        super.setId(id);
        super.setName(name);
        super.setMusicTracks(musicTracks);
        super.setSource(String.valueOf(source));
        super.setImage(String.valueOf(image));
        this.singer = singer;
    }
}
