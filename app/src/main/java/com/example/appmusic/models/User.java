package com.example.appmusic.models;

import java.util.Set;

public class User {
    private int id;
    private String username;
    private String email;
    private String name;
    private String password;
    private String phone;
    Set<Music> favourites;

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<Music> getFavourites() {
        return favourites;
    }

    public void setFavourites(Set<Music> favourites) {
        this.favourites = favourites;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String name,String username, String password, String email, String phone) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

}