package com.tujia.myssm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.tujia.myssm.api.model.CityCashBack;
import com.tujia.myssm.dao.bi.BiCityCashBackDao;
import com.tujia.myssm.service.BiCityCashBackService;

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
