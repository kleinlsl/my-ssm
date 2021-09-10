package com.tujia.myssm.dao.master;

import com.tujia.myssm.bean.ActivityParticipant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ActivityParticipantDao {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(ActivityParticipant record);

    int insertSelective(ActivityParticipant record);

    /**
     * 通过id查
     * @param id
     * @return
     */
    ActivityParticipant selectByPrimaryKey(@Param("id") Long id);

    List<ActivityParticipant> select(@Param("pojo") ActivityParticipant pojo);

    int updateByPrimaryKeySelective(@Param("pojo") ActivityParticipant pojo);

    int updateByPrimaryKeyWithBLOBs(ActivityParticipant record);

    int updateByPrimaryKey(ActivityParticipant record);

    /**
     * 批量更新状态
     */
    int updateStatusBatchByIds(@Param("status") int status,@Param("ids") List<Integer> ids);

    /**
     * 根据md5查
     */
    ActivityParticipant selectByMd5(@Param("md5") String md5);

    /**
     * 根据activityCode查
     */
    List<ActivityParticipant> selectByActivityCode(@Param("activityCode") String activityCode);

    /**
     * 查activityCode最大版本好
     */
    ActivityParticipant selectMaxVersionByActivityCode(@Param("activityCode") String activityCode);

    /**
     * 删除根据activityCode和version
     */
    int deleteByActivityCodeAndVersion(@Param("activityCode") String activityCode,@Param("version") int version);

    /**
     * 查activityCode最大版本带data
     */
    ActivityParticipant selectMaxVersionByActivityCodeWithBLOBs(@Param("activityCode") String activityCode);
}