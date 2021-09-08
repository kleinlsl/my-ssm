package com.tujia.myssm.dao.bi;

import com.tujia.myssm.BaseTest;
import com.tujia.myssm.bean.CityCashBack;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;


/**
 * @author: songlinl
 * @create: 2021/08/11 13:57
 */
public class BiCityCashBackDaoTest extends BaseTest {

    @Resource
    private BiCityCashBackDao biCityCashBackDao;
    @Test
    public void selectToDay() {
        List<CityCashBack> list = biCityCashBackDao.selectToDay();
        System.out.println("list = " + list);
    }
}