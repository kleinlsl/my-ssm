package com.tujia.myssm.dao.bi;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.model.CityCashBack;


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