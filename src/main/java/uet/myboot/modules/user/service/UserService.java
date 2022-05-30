package uet.myboot.modules.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.user.models.User;
import uet.myboot.modules.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean isUser(String username, String password) {

        return userRepository.existsUserByUsernameAndPassword(username, password);
    }

    //tao hoac cap nhat user
    public boolean create(User t) {
        int before = getAll().size();
        userRepository.save(t);
        int last = getAll().size();
        return before < last;
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public List<User> filterUsers(List<User> users) {
        for(User user:users) {
            for(Music music:user.getFavourites()) {
                music.setUsers(Collections.emptyList());
                music.setMusicGenres(Collections.emptyList());
                music.setSingers(Collections.emptyList());
            }
        }

        return users;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

}
