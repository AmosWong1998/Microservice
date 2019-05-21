package pers.amos.user.mapper;

import org.springframework.stereotype.Repository;
import pers.amos.user.pojo.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/19 - 20:35
 */

@Repository
public interface UserMapper extends Mapper<User> {

}
