package pers.amos.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.amos.user.mapper.UserMapper;
import pers.amos.user.pojo.User;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/19 - 20:37
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id){

        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
