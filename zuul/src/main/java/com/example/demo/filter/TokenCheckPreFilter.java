package com.example.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

//@Component
public class TokenCheckPreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取HttpServletRequest
        HttpServletRequest request = requestContext.getRequest();
        //获取传递过来的请求参数
        String token = request.getParameter("token");
        //如果token是空的，返回权限不足，一般返回的状态码是401
        if (StringUtils.isEmpty(token)) {
            //设置false，此时的zuul不路由此请求
            requestContext.setSendZuulResponse(false);
            //设置401
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            //设置响应的消息
            requestContext.setResponseBody("you dont have enough permissions");
        }
        return null;
    }
}
