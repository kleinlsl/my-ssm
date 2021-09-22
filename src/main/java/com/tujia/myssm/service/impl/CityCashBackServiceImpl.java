package com.tujia.myssm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tujia.myssm.api.model.CityCashBack;
import com.tujia.myssm.common.utils.ApplicationContextUtil;
import com.tujia.myssm.dao.master.CityCashBackDao;
import com.tujia.myssm.service.CityCashBackService;
import com.tujia.myssm.service.CommonService;

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
        CommonService commonService = ApplicationContextUtil.getBean(CommonService.class);
        if (cashBack == null) {
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
