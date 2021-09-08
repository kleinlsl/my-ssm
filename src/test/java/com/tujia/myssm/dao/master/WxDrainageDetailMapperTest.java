package com.tujia.myssm.dao.master;

import com.tujia.myssm.BaseTest;
import com.tujia.myssm.bean.wx.WxDrainageDetail;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * @author: songlinl
 * @create: 2021/09/02 13:14
 */
public class WxDrainageDetailMapperTest extends BaseTest {

    @Resource
    private WxDrainageDetailMapper wxDrainageDetailMapper;
    @Test
    public void saveOrUpdate(){
        WxDrainageDetail wxDrainageDetail = new WxDrainageDetail();
        wxDrainageDetail.setActivityChannelId(111);
        wxDrainageDetail.setActivityCode("11111111111111111111111");
        wxDrainageDetail.setMemberId(1234);
        wxDrainageDetail.setReceive(2);
        int count = wxDrainageDetailMapper.saveOrUpdate(wxDrainageDetail);
    }


}