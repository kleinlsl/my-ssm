package com.tujia.myssm.common.db.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.tujia.myssm.api.model.date.LocalDateRangeSet;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 18:13
 */
@Slf4j
public class LocalDateRangeSetHandler extends BaseTypeHandler<LocalDateRangeSet> {

    private LocalDateRangeSet getValue(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return LocalDateRangeSet.parse(value);
        } catch (Exception e) {
            log.error("LocalDateRangeSetHandler getValue is err, val:{}", value, e);
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateRangeSet parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public LocalDateRangeSet getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getValue(rs.getString(columnName));
    }

    @Override
    public LocalDateRangeSet getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getValue(rs.getString(columnIndex));
    }

    @Override
    public LocalDateRangeSet getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getValue(cs.getString(columnIndex));
    }
}
