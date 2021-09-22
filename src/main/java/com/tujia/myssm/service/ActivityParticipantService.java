package com.tujia.myssm.service;

import java.util.List;
import com.tujia.myssm.api.model.ActivityParticipant;

/**
 * @author: songlinl
 * @create: 2021/08/02 12:39
 */
public interface ActivityParticipantService {

//    APIResponse<String> saveUpload(List<Long> unitIds);

    int add(ActivityParticipant pojo);

    /**
     * 批量更新状态
     */
    int updateStatusBatchByIds(int status, List<Integer> ids);

    /**
     * 根据md5查
     */
    ActivityParticipant queryByMd5(String md5);

    /**
     * 更新activityCode 和 version ， 并删除过期版本
     * @param uniqueCode
     * @param activityCode
     * @return
     */
    int updateActivityCodeAndVersion(String uniqueCode, String activityCode);

    /**
     * 查询最新的版本
     * @param activityCode
     * @return
     */
    ActivityParticipant queryMaxVersionByActivityCode(String activityCode);

    /**
     * 查询所有版本
     * @param activityCode
     * @return
     */
    List<ActivityParticipant> queryByActivityCode(String activityCode);

}
