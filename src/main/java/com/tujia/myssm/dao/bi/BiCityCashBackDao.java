package com.tujia.myssm.dao.bi;

import java.util.List;
import com.tujia.myssm.api.model.CityCashBack;

/**
 * @author: songlinl
 * @create: 2021/08/06 14:43
 */
public interface BiCityCashBackDao {
    /**
     * 查询
     *
     * @return
     */
    List<CityCashBack> selectToDay();
}
