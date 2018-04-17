package com.scen.sso.service;

import com.scen.pojo.ScenResult;
import com.scen.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户操作业务层接口
 *
 * @author Scen
 * @date 2018/4/11 8:59
 */
public interface UserService {

    /**
     * 检查注册数据是否可用
     *
     * @param content
     * @param type
     * @return
     */
    ScenResult checkData(String content, Integer type);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    ScenResult createUser(TbUser user);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    ScenResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据用户使用浏览器里的cookie存储的session令牌查看用户登录信息
     *
     * @param token
     * @return
     */
    ScenResult getUserByToken(String token);

    /**
     * 根据用户使用浏览器里的cookie存储的session令牌注销用户
     *
     * @param token
     * @return
     */
    ScenResult logoutUserByToken(String token);
}
