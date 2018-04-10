package com.scen.controller;

import com.scen.common.utils.JsonUtils;
import com.scen.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传controller
 *
 * @author Scen
 * @date 2018/3/10 21:34
 */
@Controller
@RequestMapping("/pic")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    /**
     * 图片上传
     *
     * @param uploadFile
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile) {
        return JsonUtils.objectToJson(pictureService.uploadPicture(uploadFile));
    }
}
