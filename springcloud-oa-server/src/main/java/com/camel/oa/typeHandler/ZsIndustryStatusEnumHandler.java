package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsIndustryStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsIndustryStatus.class})
public class ZsIndustryStatusEnumHandler extends BaseTypeHandler<ZsIndustryStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsIndustryStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, status.getValue());
    }

    @Override
    public ZsIndustryStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsIndustryStatus.getEnumByValue(code);
    }

    @Override
    public ZsIndustryStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsIndustryStatus.getEnumByValue(code);
    }

    @Override
    public ZsIndustryStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsIndustryStatus.getEnumByValue(code);
    }
}
