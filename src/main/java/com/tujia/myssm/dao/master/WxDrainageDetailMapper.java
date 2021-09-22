package com.tujia.myssm.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.tujia.myssm.api.model.wx.WxDrainageDetail;

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
