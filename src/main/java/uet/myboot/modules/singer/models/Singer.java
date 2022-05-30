package uet.myboot.modules.singer.models;

import uet.myboot.modules.music.models.Music;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "singer")
public class Singer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @ManyToMany(mappedBy = "singers",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
