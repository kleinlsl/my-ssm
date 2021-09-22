package com.tujia.myssm.dao.master;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.tujia.myssm.api.model.CityCashBack;

/**
 * @author: songlinl
 * @create: 2021/08/06 14:43
 */
public interface CityCashBackDao {
    /**
     *
     * @param pojo
     * @return
     */
    int insert(@Param("pojo") CityCashBack pojo);

    /**
     * 保存主键冲突更新
     * @param pojo
     * @return
     */
    int save(@Param("pojo") CityCashBack pojo);

    //####################

    /**
     * 查询
     * @param pojo
     * @return
     */
    List<CityCashBack> select(@Param("pojo") CityCashBack pojo);

    /**
     * 查询
     *
     * @param date
     * @return
     */
    List<CityCashBack> selectAll(@Param("date") Date date);

    /**
     * 查询
     *
     * @return
     */
    List<CityCashBack> selectToDay();

    /**
     * 查询
     *
     * @param
     * @return
     */
    Date selectMaxDate();


}
