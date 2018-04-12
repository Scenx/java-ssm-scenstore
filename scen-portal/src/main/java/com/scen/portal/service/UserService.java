package com.scen.portal.service;

import com.scen.pojo.TbUser;

/**
 * 用户业务层接口
 *
 * @author Scen
 * @date 2018/4/11 16:36
 */
public interface UserService {
    /**
     * 根据令牌取用户信息
     *
     * @param token
     * @return
     */
    TbUser getUserByToken(String token);
}
