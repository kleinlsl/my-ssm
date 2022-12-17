package com.tujia.myssm.dao.master;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.model.HdsSaleBlacklist;
import com.tujia.myssm.utils.base.JsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 19:01
 */
public class HdsSaleBlacklistDaoTest extends BaseTest {

    @Resource
    private HdsSaleBlacklistDao hdsSaleBlacklistDao;

    @Test
    public void insert() {
        HdsSaleBlacklist blacklist = new HdsSaleBlacklist();
        //        blacklist.setDateRanges("2022-09-08_2022-09-10");
        int res = hdsSaleBlacklistDao.insert(blacklist);
        System.out.println(res);
    }

    @Test
    public void queryAll() {
        List<HdsSaleBlacklist> list = hdsSaleBlacklistDao.queryAll();
        System.out.println(JsonUtils.tryToJson(list));
    }
}