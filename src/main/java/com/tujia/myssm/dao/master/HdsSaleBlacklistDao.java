package com.tujia.myssm.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.tujia.myssm.api.model.HdsSaleBlacklist;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 18:54
 */
public interface HdsSaleBlacklistDao {

    int insert(@Param("pojo") HdsSaleBlacklist pojo);

    List<HdsSaleBlacklist> queryAll();
}
