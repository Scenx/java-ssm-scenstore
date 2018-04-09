package com.scen.pojo;

/**
 * @author Scen
 * @date 2018/3/23 9:40
 */
public class ItemBean extends TbItem {
    private String catName;

    private String statusName;


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
