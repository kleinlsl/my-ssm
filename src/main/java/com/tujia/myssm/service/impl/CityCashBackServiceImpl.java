package com.tujia.myssm.service.impl;

import com.tujia.myssm.bean.CityCashBack;
import com.tujia.myssm.dao.master.CityCashBackDao;
import com.tujia.myssm.service.CityCashBackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: songlinl
 * @create: 2021/08/06 15:50
 */
@Service
public class CityCashBackServiceImpl implements CityCashBackService {
    @Resource
    private CityCashBackDao cityCashBackDao;
    @Override
    public List<CityCashBack> query(CityCashBack cashBack) {
        if (cashBack==null){
            return null;
        }
        return cityCashBackDao.select(cashBack);
    }

    @Override
    @Transactional
    public Boolean saveBach(List<CityCashBack> cashBackList) {
        int count = cashBackList.stream().mapToInt(cityCashBack -> cityCashBackDao.save(cityCashBack)).sum();
        return count == cashBackList.size();
    }
}
