package com.scen.rest.controller;

import com.scen.common.pojo.ScenResult;
import com.scen.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * redis缓存服务表现层
 *
 * @author Scen
 * @date 2018/4/4 17:59
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
    @Autowired
    private RedisService redisService;


    /**
     * redis（内容）同步
     *
     * @param contentCid
     * @return
     */
    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public ScenResult contentCacheSync(@PathVariable Long contentCid) {
        return redisService.syncContent(contentCid);
    }

}
