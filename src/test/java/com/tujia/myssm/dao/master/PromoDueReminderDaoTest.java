package com.tujia.myssm.dao.master;

import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.junit.Test;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.model.PromoDueReminder;

/**
 *
 * @author: songlinl
 * @create: 2021/10/13 11:01
 */
public class PromoDueReminderDaoTest extends BaseTest {
    @Resource
    private PromoDueReminderDao mapper;

    @Test
    public void testSelectByMemberAndPromoCode() {
        PromoDueReminder promoDueReminder = mapper.selectByMemberAndPromoCode(1L, "111");
        System.err.println("promoDueReminder = " + promoDueReminder);
    }

    @Test
    public void testSaveOrUpdate() {
        PromoDueReminder promoDueReminder = new PromoDueReminder();
        promoDueReminder.setMemberId(1L);
        promoDueReminder.setPromoCode("1234");
        promoDueReminder.setNumber(1);
        promoDueReminder.setToTime(LocalDateTime.now().plusDays(1L));
        int count = mapper.saveOrUpdate(promoDueReminder);
        System.err.println("count = " + count);
    }
}

