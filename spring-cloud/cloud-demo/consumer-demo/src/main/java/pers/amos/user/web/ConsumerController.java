package pers.amos.user.web;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.amos.user.client.user.UserClient;
import pers.amos.user.pojo.User;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/19 - 20:56
 *
 * @DefaultProperties 类级别的服务降级的声明 这样服务降级的方法queryByIdFallback可以为整个类服务
 */
@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "defaultFallback")
public class ConsumerController {



   /* 查询1.0
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        根据服务id获取实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        从实例列表中获取一个实例
        ServiceInstance instance = instances.get(0);
        String host = instance.getHost();
        int port = instance.getPort();
        String url = "http://"+ host + ":" + port + "/user/" + id;

        User user = restTemplate.getForObject(url, User.class);
        return user;
    }
    */


    /**
     * 查询2.0
     * @param id
     * @HystrixCommand 开启服务降级和线程隔离 fallbackMethod：如果服务失败则调用该方法对服务进行降级
     * @note 为了方便下面服务降级方法的编写 将返回类型变为String JSON会自动封装成String并返回
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    @HystrixCommand(
            commandProperties = {
                    //5次失败则开启熔断器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    //休眠10s
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    //错误百分比 60%
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            }
    )
     @GetMapping("/{id}")
    public String queryById(@PathVariable("id") Long id) {

        //模拟网络延迟 引发熔断
        if(id % 2 == 0){
            throw new RuntimeException("触发熔断的异常");
        }

        String url = "http://user-service/user/" + id;
        String user = restTemplate.getForObject(url, String.class);
        return user;
    }
     */


    @Autowired
    private UserClient userClient;

    /**
     * 查询3.0 使用Feign
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id){
        return userClient.queryById(id);
    }


    /**
     * 服务降级返回错误信息的方法 要求：该方法的形参和返回类型必须和上面的方法一致
     * 返回值一般为String
     * @param id
     * @return
     */
    public String queryByIdFallback(Long id) {
        return "对不起，系统繁忙！";
    }

    /**
     * 类级别的服务降级的方法 形参为空
     * @return
     */
    public String defaultFallback() {
        return "对不起，系统繁忙啊！";
    }

}
