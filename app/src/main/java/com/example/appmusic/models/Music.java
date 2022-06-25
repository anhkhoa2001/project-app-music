package com.example.appmusic.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Music extends AMusic {
    private List<Singer> singers;
    private List<User> users;

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Music() {}

    public String singersToString() {
        List<String> nameSingers = new ArrayList<>();
        for(Singer singer:singers) {
            nameSingers.add(singer.getName());
        }
        return TextUtils.join(" ft ", nameSingers);
    }

    public User getUserByUsername(String username) {
        User user = null;
        for(User u : getUsers()) {
            if(u.getUsername().equals(username)) {
                user = u;
            }
        }

        return user;
    }

    public String convertToElementString() {
        return this.getId() + " =-= " + this.getName() + " =-= " + this.getSource() + " =-= " + this.getMusicTracks()
                            + " =-= " + this.getImage() + " =-= " + this.singersToString() + " =-= " + this.getListens()
                            + " =-= " + this.getLikes() + " =-= " + this.isType();
    }

    public Music(int id, String name, String source, int musicTracks, String image, int listens, int likes) {
        super.setId(id);
        super.setName(name);
        super.setMusicTracks(musicTracks);
        super.setSource(source);
        super.setLikes(likes);
        super.setListens(listens);
        super.setImage(image);
    }
}
