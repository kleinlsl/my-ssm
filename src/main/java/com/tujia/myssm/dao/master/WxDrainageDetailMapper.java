package com.tujia.myssm.dao.master;

import com.tujia.myssm.bean.wx.WxDrainageDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: songlinl
 * @create: 2021/09/01 17:47
 */
public interface WxDrainageDetailMapper {

    /**
     * 保存键冲突则更新
     *
     * @param pojo
     * @return
     */
    int saveOrUpdate(@Param("pojo") WxDrainageDetail pojo);

    /**
     * 根据activityCode查
     *
     * @param activityCodes
     * @return
     */
    List<WxDrainageDetail> selectByActivityCodes(@Param("activityCodes") List<String> activityCodes);
}
