package com.scen.sso.controller;

import com.scen.common.pojo.ScenResult;
import com.scen.common.utils.ExceptionUtil;
import com.scen.pojo.TbUser;
import com.scen.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户操作表现层
 *
 * @author Scen
 * @date 2018/4/11 9:19
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查注册字段
     *
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
        ScenResult result = null;
//        参数有效性校验
        if (StringUtils.isBlank(param)) {
            result = ScenResult.build(400, "校验内容不能为空");
        }
        if (type == null) {
            result = ScenResult.build(400, "校验内容类型不能为空");
        }
        if (type != 1 && type != 2 && type != 3) {
            result = ScenResult.build(400, "校验内容类型错误");
        }
//        校验出错
        if (result != null) {
            if (callback != null) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }
        }
//        调用服务
        try {
            result = userService.checkData(param, type);
        } catch (Exception e) {
            e.printStackTrace();
            result = ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        //        校验出错
        if (callback != null) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        } else {
            return result;
        }
    }


    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ScenResult createUser(TbUser user) {
        try {
            return userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ScenResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            return userService.userLogin(username, password, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            return ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 根据用户使用浏览器里的cookie存储的session令牌查看用户登录信息
     *
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        ScenResult result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
//        判断是否为jsonp调用
        if (StringUtils.isBlank(callback)) {
            return result;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }


    /**
     * 安全退出
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/logout/{token}")
    @ResponseBody
    public Object logoutUserByToken(@PathVariable String token, String callback) {
        ScenResult result = null;
        try {
            result = userService.logoutUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = ScenResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        //        判断是否为jsonp调用
        if (StringUtils.isBlank(callback)) {
            return result;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }
}
