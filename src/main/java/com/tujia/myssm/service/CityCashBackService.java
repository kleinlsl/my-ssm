package com.tujia.myssm.service;

import java.util.List;
import com.tujia.myssm.api.model.CityCashBack;

/**
 * @author: songlinl
 * @create: 2021/08/06 15:49
 */
public interface CityCashBackService {
    /**
     * 查询
     * @param cashBack
     * @return
     */
    List<CityCashBack> query(CityCashBack cashBack);

    Boolean saveBach(List<CityCashBack> cashBackList);
}
