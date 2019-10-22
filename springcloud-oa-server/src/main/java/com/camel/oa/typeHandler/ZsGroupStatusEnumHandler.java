package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsGroupStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsGroupStatus.class})
public class ZsGroupStatusEnumHandler extends BaseTypeHandler<ZsGroupStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsGroupStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, status.getValue());
    }

    @Override
    public ZsGroupStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsGroupStatus.getEnumByValue(code);
    }

    @Override
    public ZsGroupStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsGroupStatus.getEnumByValue(code);
    }

    @Override
    public ZsGroupStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsGroupStatus.getEnumByValue(code);
    }
}
