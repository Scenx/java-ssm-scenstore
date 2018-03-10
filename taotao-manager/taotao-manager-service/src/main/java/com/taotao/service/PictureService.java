package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 图片上传业务层接口
 *
 * @author Scen
 * @date 2018/3/10 20:48
 */
public interface PictureService {
    /**
     * 图片上传
     *
     * @param uploadFile
     * @return
     */
    Map uploadPicture(MultipartFile uploadFile);
}
