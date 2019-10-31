package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsTalentederStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsTalentederStatus.class})
public class ZsTalentederStatusEnumHandler extends BaseTypeHandler<ZsTalentederStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsTalentederStatus resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ZsTalentederStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsTalentederStatus.getEnumByValue(code);
    }

    @Override
    public ZsTalentederStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsTalentederStatus.getEnumByValue(code);
    }

    @Override
    public ZsTalentederStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsTalentederStatus.getEnumByValue(code);
    }
}
