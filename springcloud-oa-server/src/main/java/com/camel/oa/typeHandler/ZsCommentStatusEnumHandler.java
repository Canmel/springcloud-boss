package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsCommentStatus;
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
@MappedTypes({ZsCommentStatus.class})
public class ZsCommentStatusEnumHandler extends BaseTypeHandler<ZsCommentStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsCommentStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, status.getValue());
    }

    @Override
    public ZsCommentStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsCommentStatus.getEnumByValue(code);
    }

    @Override
    public ZsCommentStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsCommentStatus.getEnumByValue(code);
    }

    @Override
    public ZsCommentStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsCommentStatus.getEnumByValue(code);
    }
}
