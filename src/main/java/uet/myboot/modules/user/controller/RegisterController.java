package uet.myboot.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.myboot.modules.user.models.User;
import uet.myboot.modules.user.service.UserService;
import uet.myboot.parent.main.EResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registor")
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping
    public EResponse registor(@Valid @RequestBody final User user) {
        try {
            List<User> users = userService.getAll();
            int count = 0;
            for(User user1 : users) {
                if(user.getUsername().equals(user1.getUsername())) {
                    count++;
                }
            }
            boolean isno = false;
            if(count == 0) {
                isno = userService.save(user);
            }

            return isno ? EResponse.SUCCESS : EResponse.FAILED;
        } catch (Exception e) {
            return EResponse.TIME_OUT;
        }
    }
}
