package uet.myboot.modules.music.models;

import org.springframework.web.util.UriUtils;
import uet.myboot.modules.genre.models.MusicGenre;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.modules.user.models.User;
import uet.myboot.parent.main.Main;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @Basic
    private String source;

    @Basic
    private int musicTracks;//tinh theo s

    @Basic
    private String image;

    @ManyToMany
    @JoinTable(
            name = "music_singer",
            joinColumns = @JoinColumn(name = "singer_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id"))
    List<Singer> singers;

    @ManyToMany(mappedBy = "favourites", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<User> users;

    @Basic
    private int listens;

    @Basic
    private int likes;

    @ManyToMany
    @JoinTable(
            name = "music_musicgenre",
            joinColumns = @JoinColumn(name = "music_genre_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id"))
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

    public String getSource() throws UnsupportedEncodingException {
        String nameMusic = source.split("sounds/")[1].split("/")[1].split(".mp3")[0];

        return (Main.URL_IP + source).replaceAll(nameMusic,
                UriUtils.encodePath(nameMusic, "UTF-8"));
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }
}
