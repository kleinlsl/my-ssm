package com.tujia.myssm.common.db.handlers;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.databind.JsonNode;
import com.tujia.framework.handlers.JsonTypeHandler;
import com.tujia.myssm.api.model.DefaultOpLogMark;
import com.tujia.myssm.api.model.OpLogDetail;
import com.tujia.myssm.api.model.OpLogMark;
import com.tujia.myssm.common.utils.JsonUtils;

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
        OpLogDetail detail = super.getResult(rs, columnName);
        detail.setMark(parseMark(rs));
        return detail;
    }

    @Override
    public OpLogDetail getResult(ResultSet rs, int columnIndex) throws SQLException {
        return super.getResult(rs, columnIndex);
    }

    @Override
    public OpLogDetail getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return super.getResult(cs, columnIndex);
    }

    private OpLogMark parseMark(ResultSet rs) {
        try {
            JsonNode jsonNode = JsonUtils.getObjectMapperInstance().readTree(rs.getString("detail"));
            int markType = jsonNode.get("markType").intValue();
            String markStr = jsonNode.get("mark").toString();
            OpLogMark mark = null;
            if (markType == 1) {
                mark = JsonUtils.readValue(markStr, DefaultOpLogMark.class);
            }
            return mark;
        } catch (Exception e) {
            return null;
        }
    }
}
