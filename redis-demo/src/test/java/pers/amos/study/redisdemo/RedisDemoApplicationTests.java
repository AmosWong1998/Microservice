package pers.amos.study.redisdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("test", "hello");
        String test = redisTemplate.opsForValue().get("test");
        System.out.println(test);
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps("user:123");
        ops.put("name", "amos");
        ops.put("age", "21");
        ops.put("gender", "male");
    }

}
