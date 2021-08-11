package com.tujia.myssm.service;

import com.tujia.myssm.bean.CityCashBack;

import java.util.List;

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
