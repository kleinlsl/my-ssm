package com.tujia.myssm.dao.master;

import org.apache.ibatis.annotations.Param;
import com.tujia.myssm.api.model.OpLog;

/**
 *
 * @author: songlinl
 * @create: 2021/11/04 17:01
 */
public interface OpLogMapper {

    int insert(OpLog opLog);

    OpLog selectById(@Param("id") long id);

}
