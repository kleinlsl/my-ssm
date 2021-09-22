package com.tujia.myssm.dao.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.util.DigestUtils;
import com.google.common.base.Joiner;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.model.ActivityParticipant;


/**
 * @author: songlinl
 * @create: 2021/08/01 18:02
 */
public class ActivityParticipantDaoTest extends BaseTest {
    @Resource
    private ActivityParticipantDao activityParticipantDao;

    @Test
    public void test() {
        System.out.println(Joiner.on(",").useForNull("null").join(Arrays.asList(1L, 2L, 3L)));
        System.out.println(new String("1234".getBytes()));
    }

    @Test
    public void insert() {
        ActivityParticipant activityParticipant = new ActivityParticipant();
        String dataStr = "12345,1234,222";
        activityParticipant.setData(dataStr.getBytes());
        activityParticipant.setUnitIds(dataStr);
        activityParticipant.setMd5(DigestUtils.md5DigestAsHex(dataStr.getBytes()));
        activityParticipant.setCreateTime(new Date());
        activityParticipant.setVersion(2);
        int count = activityParticipantDao.insertSelective(activityParticipant);
    }

    @Test
    public void insertOneShiWan() {
        long start = System.currentTimeMillis();
        String dataStr = "12345,1234,222";
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 1 * (1024) * 1024; i++) {
            list.add(String.format("%8s", i));
        }
        list.sort((o1, o2) -> -o2.compareTo(o1));
        dataStr = Joiner.on(",").skipNulls().join(list);
        System.out.println("dataStr = " + dataStr.getBytes().length);
        assert dataStr.getBytes().length <= 16 * 1024 * 1024;
        ActivityParticipant activityParticipant = new ActivityParticipant();
        activityParticipant.setData(dataStr.getBytes());
//        activityParticipant.setUnitIds(dataStr);
        String md5 = DigestUtils.md5DigestAsHex(dataStr.getBytes()) + "_" + System.currentTimeMillis();
        activityParticipant.setMd5(md5);
//        activityParticipant.setActivityCode(md5);
        activityParticipant.setCreateTime(new Date());
        activityParticipant.setVersion(2);
        int count = activityParticipantDao.insertSelective(activityParticipant);
        System.out.println(System.currentTimeMillis() - start);
    }


    @Test
    public void select() {
        ActivityParticipant activityParticipant = activityParticipantDao.selectByPrimaryKey(2L);
        System.out.println("activityParticipant = " + activityParticipant);
        System.out.println("activityParticipant.byteToString() = " + activityParticipant.byteToString());
    }

    @Test
    public void updateByPrimaryKeySelective() {
        ActivityParticipant activityParticipant = activityParticipantDao.selectByPrimaryKey(1L);
        activityParticipant.setData(null);
        activityParticipant.setVersion(10);
        int count = activityParticipantDao.updateByPrimaryKeySelective(activityParticipant);
        System.out.println(count);
//        System.out.println("activityParticipant = " + activityParticipant);
//        System.out.println("activityParticipant.byteToString() = " + activityParticipant.byteToString());
    }

    @Test
    public void selectByActivityCode() {
        long start = System.currentTimeMillis();
        List<ActivityParticipant> list = activityParticipantDao.selectByActivityCode("1");
        System.out.println(list.size());
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void updateStatusBatchByIds() {
        long start = System.currentTimeMillis();
        int list = activityParticipantDao.updateStatusBatchByIds(1, Arrays.asList(1, 3));
        System.out.println(list);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void selectMaxVersionByActivityCode() {
        long start = System.currentTimeMillis();
        ActivityParticipant maxVersion = activityParticipantDao.selectMaxVersionByActivityCode("1");
        System.out.println(maxVersion);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void StringToLong() {
        List<String> strings = Arrays.asList("123", " 123 ", "12 3", "","  000 0   ");
        List<Long> longs = strings.stream().filter(s -> filter(s.trim())).map(s -> Long.valueOf(s.trim())).collect(Collectors.toList());
        System.out.println(strings.size()-longs.size());
        System.out.println(longs.size());
        longs.sort((o1, o2) -> (int) (o1-o2));
        System.out.println(longs);
    }

    @Test
    public void testSelect(){
        ActivityParticipant activityParticipant = new ActivityParticipant();
        activityParticipant.setVersion(10);
        List<ActivityParticipant> list = activityParticipantDao.select(activityParticipant);
        System.out.println(list);
    }
    @Test
    public void deleteByActivityCodeAndVersion(){
        int list = activityParticipantDao.deleteByActivityCodeAndVersion("1",-1);
        System.out.println(list);
    }
    /**
     * 过滤数值型字符串
     * @param s
     * @return
     */
    private Boolean filter(String s) {
        if ("".equals(s)){
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }
}
