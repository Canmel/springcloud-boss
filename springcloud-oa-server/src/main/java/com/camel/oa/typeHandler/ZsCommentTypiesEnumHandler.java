package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsCommentTyies;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsCommentTyies.class})
public class ZsCommentTypiesEnumHandler extends BaseTypeHandler<ZsCommentTyies> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsCommentTyies resourceTyies, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceTyies.getValue());
    }

    @Override
    public ZsCommentTyies getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsCommentTyies.getEnumByValue(code);
    }

    @Override
    public ZsCommentTyies getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsCommentTyies.getEnumByValue(code);
    }

    @Override
    public ZsCommentTyies getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsCommentTyies.getEnumByValue(code);
    }
}
