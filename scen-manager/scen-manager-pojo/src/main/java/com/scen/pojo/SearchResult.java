package com.scen.pojo;

import java.util.List;

/**
 * 搜索返回数据包装
 *
 * @author Scen
 * @date 2018/4/8 15:39
 */
public class SearchResult {

    private List<Item> itemList;

    private Long recordCount;

    private Long curPage;
    private Long pageCount;


    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getCurPage() {
        return curPage;
    }

    public void setCurPage(Long curPage) {
        this.curPage = curPage;
    }
}
