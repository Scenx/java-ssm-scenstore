package com.scen.portal.interceptor;

import com.scen.common.utils.CookieUtils;
import com.scen.pojo.TbUser;
import com.scen.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author Scen
 * @date 2018/4/11 16:28
 */
public class LonginInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;

    @Value("${SSO_PAGE_LOGIN}")
    private String SSO_PAGE_LOGIN;

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @Value("${SSO_COOKIE_NAME}")
    private String SSO_COOKIE_NAME;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        在handler执行之前处理
//        判断用户是否登录
//        从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, SSO_COOKIE_NAME);
//        根据token换取用户信息，调用sso系统的接口
        TbUser user = userService.getUserByToken(token);
//        取不到用户信息
        if (user == null) {
//        跳转到登录界面，把用户请求的url作为参数传递给登录页面
            httpServletResponse.sendRedirect(SSO_BASE_URL + SSO_PAGE_LOGIN + "?redirect=" + httpServletRequest.getRequestURL());
//        返回false
            return false;
        }
//        取到用户信息，放行
//        返回值决定handler是否执行。true执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//在handler执行之后，返回model and view之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//返回model and view之后
//        响应用户之后
    }
}
