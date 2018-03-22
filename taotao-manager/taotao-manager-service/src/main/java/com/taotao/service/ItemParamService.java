package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * 商品规格参数业务层接口
 * @author Scen
 * @date 2018/3/22 10:42
 */
public interface ItemParamService {
    /**
     * 根据查询对应商品规格
     * @param cid
     * @return
     */
    TaotaoResult getItemParamByCid(Long cid);

    /**
     * 添加商品规格参数
     * @param itemParam
     * @return
     */
    TaotaoResult insertItemParam(TbItemParam itemParam);
}
