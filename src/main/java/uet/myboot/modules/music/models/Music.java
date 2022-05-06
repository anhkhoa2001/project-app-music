package uet.myboot.modules.music.models;

import uet.myboot.modules.genre.models.MusicGenre;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.parent.main.Main;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "music")
public class Music {
    @Id
    @Basic
    private int id;

    @Basic
    private String name;

    @Basic
    private String source;

    @Basic
    private int musicTracks;//tinh theo s

    @Basic
    private String image;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "music_singer",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id"))
    List<Singer> singers;

    @Basic
    private int listens;

    @Basic
    private int likes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "music_musicgenre",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "music_genre_id"))
    List<MusicGenre> musicGenres;

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

    public String getSource() {
        return Main.URL_IP + source;
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
        return Main.URL_IP + image;
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

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public Music() {
    }

    public Music(String name, String source, int musicTracks, String image, int listens, int likes) {
        this.name = name;
        this.source = source;
        this.musicTracks = musicTracks;
        this.image = image;
        this.listens = listens;
        this.likes = likes;
    }

    public List<MusicGenre> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(List<MusicGenre> musicGenres) {
        this.musicGenres = musicGenres;
    }

}
