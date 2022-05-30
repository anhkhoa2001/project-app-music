package uet.myboot.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.music.services.MusicService;
import uet.myboot.modules.user.models.User;
import uet.myboot.modules.user.service.UserService;
import uet.myboot.parent.main.EResponse;
import uet.myboot.parent.main.JwtTokenUtil;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MusicService musicService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<User> getAll() {
        return userService.filterUsers(userService.getAll());
    }

    @GetMapping("/like")
    public EResponse handleLike(@RequestParam("id") int id, @RequestParam("token") String token
                        ,  @RequestParam(value = "numberStatus") int numberStatus) {
        //status = 0 => them vao
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.getUserByUsername(username);
            Music music = musicService.getOne(id);
            Set<Music> favourites = user.getFavourites();
            System.out.println(numberStatus);
            if(numberStatus == 0) {
                favourites.add(music);
            } else {
                favourites.remove(music);
            }
            user.setFavourites(favourites);
            userService.update(user);
            return EResponse.SUCCESS;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return EResponse.FAILED;
        }

    }
}
