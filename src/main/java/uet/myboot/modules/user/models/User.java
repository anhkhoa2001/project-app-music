package uet.myboot.modules.user.models;

import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.singer.models.Singer;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Basic
    private String username;

    @Basic
    private String role;

    @Basic
    private String email;

    @Basic
    private String name;

    @Basic
    private String password;

    @Basic
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "music_like",
            joinColumns = {@JoinColumn(name = "id_music")},
            inverseJoinColumns = {@JoinColumn(name = "id_user")})
    Set<Music> favourites;

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String username, String role, String email, String name, String password, String phone) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public Set<Music> getFavourites() {
        return favourites;
    }

    public void setFavourites(final Set<Music> favourites) {
        this.favourites = favourites;
    }
}