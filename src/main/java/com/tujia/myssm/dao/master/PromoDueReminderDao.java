package com.tujia.myssm.dao.master;

import org.apache.ibatis.annotations.Param;
import com.tujia.myssm.api.model.PromoDueReminder;

/**
 *
 * @author: songlinl
 * @create: 2021/10/13 18:58
 */
public interface PromoDueReminderDao {

    /**
     * 保存键冲突则更新
     * @param pojo
     * @return
     */
    int saveOrUpdate(@Param("pojo") PromoDueReminder pojo);

    /**
     * 查询
     * @param memberId
     * @param promoCode
     * @return
     */
    PromoDueReminder selectByMemberAndPromoCode(@Param("memberId") long memberId, @Param("promoCode") String promoCode);
}
