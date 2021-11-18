package com.tujia.myssm.common.db.handlers;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import com.tujia.framework.handlers.JsonTypeHandler;
import com.tujia.myssm.api.model.OpLogDetail;

/**
 *
 * @author: songlinl
 * @create: 2021/11/04 17:24
 */
@MappedJdbcTypes({ JdbcType.VARCHAR })
@MappedTypes({ OpLogDetail.class })
public class OpLogDetailHandler extends JsonTypeHandler<OpLogDetail> {
    public static final String type = "type";

    @Override
    public OpLogDetail getResult(ResultSet rs, String columnName) throws SQLException {
        return super.getResult(rs, columnName);
    }

    @Override
    public OpLogDetail getResult(ResultSet rs, int columnIndex) throws SQLException {
        return super.getResult(rs, columnIndex);
    }

    @Override
    public OpLogDetail getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return super.getResult(cs, columnIndex);
    }
}
