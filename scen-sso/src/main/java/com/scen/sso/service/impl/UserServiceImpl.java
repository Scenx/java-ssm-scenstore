package com.scen.sso.service.impl;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.CookieUtils;
import com.scen.common.utils.JsonUtils;
import com.scen.mapper.TbUserMapper;
import com.scen.pojo.TbUser;
import com.scen.pojo.TbUserExample;
import com.scen.sso.dao.JedisClient;
import com.scen.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户操作业务层实现类
 *
 * @author Scen
 * @date 2018/4/11 9:01
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Value("${SSO_COOKIE_NAME}")
    private String SSO_COOKIE_NAME;
    @Override
    public ScenResult checkData(String content, Integer type) {
//        创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
//        对数据进行校验 1、2、3分别代表username、phone、email
//        用户名校验
        if (1 == type) {
            criteria.andUsernameEqualTo(content);
//            电话校验
        } else if (2 == type) {
            criteria.andPhoneEqualTo(content);
//            邮箱校验
        } else {
            criteria.andEmailEqualTo(content);
        }
//        执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return ScenResult.ok(true);
        }
        return ScenResult.ok(false);
    }

    @Override
    public ScenResult createUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
//        md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return ScenResult.ok();
    }

    @Override
    public ScenResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
//      如果没有此用户名
        if (null == list || list.size() == 0) {
            return ScenResult.build(400, "用户名或者密码错误");
        }
        TbUser user = list.get(0);
//        比对密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return ScenResult.build(400, "用户名或者密码错误");
        }
//        生成token
        String token = UUID.randomUUID().toString();
//        保存用户到redis之前把用户密码清空
        user.setPassword(null);
//        把信息写到redis
        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
//        添加写cookie逻辑,cookie的有效期是关闭浏览器失效
        CookieUtils.setCookie(request, response, SSO_COOKIE_NAME, token);

//        返回token
        return ScenResult.ok(token);
    }

    @Override
    public ScenResult getUserByToken(String token) {
//        根据token从redis中查询用户信息
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
//        判断是否为空
        if (StringUtils.isBlank(json)) {
            return ScenResult.build(400, "此session已经过期，请重新登录");
        }
//        更新过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
//        返回用户信息
        return ScenResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
    }

    @Override
    public ScenResult logoutUserByToken(String token) {
        try {
            jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
            return ScenResult.ok("");
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, "服务器繁忙。。。");
        }
    }

}
