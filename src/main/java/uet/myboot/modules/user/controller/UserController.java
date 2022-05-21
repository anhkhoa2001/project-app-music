package uet.myboot.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.myboot.modules.user.models.User;
import uet.myboot.modules.user.service.UserService;
import uet.myboot.parent.main.EResponse;
import uet.myboot.parent.main.JwtTokenUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public String login(@Valid @RequestBody final User user) {
        boolean isno = userService.isUser(user);

        final String token = isno ? jwtTokenUtil.generateToken(user) : "ERROR!!!";
        return token;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/auth")
    public EResponse authentication(@Valid @RequestBody final String token) {
        try {
            List<User> users = userService.getAll();
            int count = 0;
            for(User user:users) {
                if(jwtTokenUtil.validateToken(token, user)) {
                    count++;
                }
            }

            return count == 1 ? EResponse.SUCCESS : EResponse.FAILED;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return EResponse.TIME_OUT;
        }
    }


}
