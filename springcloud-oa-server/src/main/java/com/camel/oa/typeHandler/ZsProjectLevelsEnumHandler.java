package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsProjectLevels;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsProjectLevels.class})
public class ZsProjectLevelsEnumHandler extends BaseTypeHandler<ZsProjectLevels> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsProjectLevels status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, status.getValue());
    }

    @Override
    public ZsProjectLevels getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsProjectLevels.getEnumByValue(code);
    }

    @Override
    public ZsProjectLevels getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsProjectLevels.getEnumByValue(code);
    }

    @Override
    public ZsProjectLevels getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsProjectLevels.getEnumByValue(code);
    }
}
