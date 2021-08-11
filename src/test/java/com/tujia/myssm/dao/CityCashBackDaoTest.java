package com.tujia.myssm.dao;

import com.alibaba.excel.util.DateUtils;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.bean.CityCashBack;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: songlinl
 * @create: 2021/08/06 15:22
 */
public class CityCashBackDaoTest extends BaseTest {

    @Resource
    private CityCashBackDao cityCashBackDao;

    @Test
    public void insert() {
        CityCashBack cityCashBack = new CityCashBack();
        cityCashBack.setAmount(15);
        cityCashBack.setCityId(10022L);
        cityCashBack.setDate(new Date());

        int count = cityCashBackDao.insert(cityCashBack);
        System.out.println("count = " + count);
        ;
    }

    @Test
    public void save() {
        CityCashBack cityCashBack = new CityCashBack();
        cityCashBack.setAmount(201);
        cityCashBack.setCityId(1002L);
        cityCashBack.setDate(new Date());

        int count = cityCashBackDao.save(cityCashBack);
        System.out.println("count = " + count);
        ;
    }

    @Test
    public void select() {
        CityCashBack cityCashBack = new CityCashBack();
//        cityCashBack.setAmount(20);
//        cityCashBack.setCityId(1002L);
        cityCashBack.setDate(new Date());

        List<CityCashBack> list = cityCashBackDao.select(cityCashBack);
        System.out.println("list = " + list);
        ;
    }

    @Test
    public void selectMaxDate() {

        Date date = cityCashBackDao.selectMaxDate();
        System.out.println("DateFormat = " + DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));

    }

    @Test
    public void selectAll() {
        List<CityCashBack> cityCashBacks = cityCashBackDao.selectAll(new Date());
        System.out.println("cityCashBacks = " + cityCashBacks);
    }

    @Test
    public void selectToDay() {
        List<CityCashBack> cityCashBacks = cityCashBackDao.selectToDay();
        System.out.println("cityCashBacks = " + cityCashBacks);
    }
}