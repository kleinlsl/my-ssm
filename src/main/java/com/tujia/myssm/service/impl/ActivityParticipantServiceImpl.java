package com.tujia.myssm.service.impl;

import com.tujia.myssm.bean.ActivityParticipant;
import com.tujia.myssm.dao.ActivityParticipantDao;
import com.tujia.myssm.service.ActivityParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: songlinl
 * @create: 2021/08/02 14:18
 */
@Service
public class ActivityParticipantServiceImpl implements ActivityParticipantService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityParticipantServiceImpl.class);

    private final static Integer NUMBER_OF_VERSIONS = 5;

    @Resource
    private ActivityParticipantDao activityParticipantDao;

//    @Override
//    public APIResponse<String> saveUpload(List<Long> unitIds) {
//        ActivityParticipant activityParticipant = buildActivityParticipant(unitIds);
//        int count = activityParticipantDao.insertSelective(activityParticipant);
//        if (count > 0) {
//            TMonitor.recordOne("ActivityParticipantServiceImpl.saveUploadSuccess");
//            return APIResponse.returnSuccess(activityParticipant.getMd5());
//        }
//        TMonitor.recordOne("ActivityParticipantServiceImpl.saveUploadFail");
//        return APIResponse.returnFail(EnumErrorCode.Start.getCode(), "保存失败");
//    }

    @Override
    public int add(ActivityParticipant pojo) {
        return activityParticipantDao.insertSelective(pojo);
    }

    @Override
    public int updateStatusBatchByIds(int status, List<Integer> ids) {
        return activityParticipantDao.updateStatusBatchByIds(status, ids);
    }

    @Override
    public ActivityParticipant queryByMd5(String md5) {
        return activityParticipantDao.selectByMd5(md5);
    }

    @Override
    public int updateActivityCodeAndVersion(String uniqueCode, String activityCode) {
        ActivityParticipant activityParticipant = activityParticipantDao.selectByMd5(uniqueCode);
        ActivityParticipant maxVersion = activityParticipantDao.selectMaxVersionByActivityCode(activityCode);

        activityParticipant.setActivityCode(activityCode);
        if (maxVersion != null) {
            // 更新版本号 删除老版本
            activityParticipant.setVersion(maxVersion.getVersion() + 1);
            if (activityParticipant.getVersion() - NUMBER_OF_VERSIONS > 0){
                int count = activityParticipantDao.deleteByActivityCodeAndVersion(activityCode, activityParticipant.getVersion() - NUMBER_OF_VERSIONS);
                logger.info("ActivityParticipantServiceImpl.updateActivityCodeAndVersion,删除老版本,activityCode：{},version:{},res:{}",
                        activityCode, activityParticipant.getVersion() - NUMBER_OF_VERSIONS, count > 0);
            }
        }

        return activityParticipantDao.updateByPrimaryKeySelective(activityParticipant);
    }

    @Override
    public ActivityParticipant queryMaxVersionByActivityCode(String activityCode) {
        if (StringUtils.isEmpty(activityCode)){
            return null;
        }
        return activityParticipantDao.selectMaxVersionByActivityCodeWithBLOBs(activityCode);
    }

    @Override
    public List<ActivityParticipant> queryByActivityCode(String activityCode) {
        if (StringUtils.isEmpty(activityCode)){
            return null;
        }
        return activityParticipantDao.selectByActivityCode(activityCode);
    }

//    private ActivityParticipant buildActivityParticipant(List<Long> unitIds) {
//        ActivityParticipant activityParticipant = new ActivityParticipant();
//
//        byte[] unitIdBytes = RoaringMapUtils.serializeToBytes(unitIds);
//        String md5 = DigestUtils.md5DigestAsHex(unitIdBytes) + "_" + System.currentTimeMillis();
//
//        activityParticipant.setData(unitIdBytes);
//        activityParticipant.setVersion(1);
//        activityParticipant.setData(unitIdBytes);
//        activityParticipant.setMd5(md5);
//        activityParticipant.setStatus(1);
//
//        return activityParticipant;
//    }

}
