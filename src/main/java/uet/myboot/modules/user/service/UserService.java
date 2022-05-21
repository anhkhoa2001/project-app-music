package uet.myboot.modules.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.modules.user.models.User;
import uet.myboot.modules.user.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean isUser(User user) {
        User userDB = null;

        for(User user1:getAll()) {
            if(user1.getUsername().equals(user.getUsername())) {
                userDB = user1;
            }
        }

        return userDB != null && userDB.getPassword().equals(user.getPassword());
    }

    public boolean save(User t) {
        int before = getAll().size();
        userRepository.save(t);
        int last = getAll().size();
        return before < last;
    }
}
