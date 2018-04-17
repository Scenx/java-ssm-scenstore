package com.scen.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 商品展示bean
 *
 * @author Scen
 * @date 2018/4/10 11:16
 */
public class ItemInfo extends TbItem {
    @JsonIgnore
    public String[] getImages() {
        String image = getImage();
        if (image != null) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
