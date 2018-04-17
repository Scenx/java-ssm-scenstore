package com.scen.portal.service.impl;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.HttpClientUtil;
import com.scen.pojo.TbUser;
import com.scen.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户业务层实现类
 *
 * @author Scen
 * @date 2018/4/11 16:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;

    @Override
    public TbUser getUserByToken(String token) {
        try {
//        调用sso的服务取用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
//        把json转换为scenre
            ScenResult result = ScenResult.formatToPojo(json, TbUser.class);
            if (result.getStatus() == 200) {
                TbUser user = (TbUser) result.getData();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
