package com.tujia.myssm.dao.bi;

import com.tujia.myssm.bean.CityCashBack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: songlinl
 * @create: 2021/08/06 14:43
 */
@Repository
public interface BiCityCashBackDao {
    /**
     * 查询
     *
     * @return
     */
    List<CityCashBack> selectToDay();
}
