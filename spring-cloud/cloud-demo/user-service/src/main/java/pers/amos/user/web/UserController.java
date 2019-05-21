package pers.amos.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.amos.user.pojo.User;
import pers.amos.user.service.UserService;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/19 - 20:38
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id){
        return userService.queryById(id);
    }
}
