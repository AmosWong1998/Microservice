package pers.amos.user.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.amos.user.pojo.User;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/21 - 8:42
 */

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/user/{id}")
    User queryById(@PathVariable("id") Long id);
}
