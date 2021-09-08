package com.tujia.myssm.service.impl;

import com.tujia.myssm.bean.CityCashBack;
import com.tujia.myssm.dao.bi.BiCityCashBackDao;
import com.tujia.myssm.service.BiCityCashBackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: songlinl
 * @create: 2021/08/06 15:50
 */
@Service
public class BiCityCashBackServiceImpl implements BiCityCashBackService {
    @Resource
    private BiCityCashBackDao biCityCashBackDao;

    @Override
    public List<CityCashBack> queryToDay() {
        return biCityCashBackDao.selectToDay();
    }
}
