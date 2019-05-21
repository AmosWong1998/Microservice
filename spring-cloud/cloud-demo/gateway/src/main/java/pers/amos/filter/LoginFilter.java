package pers.amos.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author amos
 * 与你同在
 * @create 2019/5/21 - 10:47
 *
 * 登陆拦截器
 * 简单模拟：当请求参数中含有access-token时就认为 用户已经登陆
 * 没有时返回403错误
 */

@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 拦截器的类型
     * 前置拦截器
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 拦截器的优先级
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 是否应该拦截
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 拦截器的业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取请求的上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取请求request
        HttpServletRequest request = ctx.getRequest();
        //获取请求中的accessToken
        String accessToken = request.getParameter("access-token");
        //判断accessToken中是否为空 如果为空则返回403
        if (StringUtils.isBlank(accessToken)){
            //不存在 未登录 则拦截
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;
    }


}
