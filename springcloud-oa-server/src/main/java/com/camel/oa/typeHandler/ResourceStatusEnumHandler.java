package com.camel.oa.typeHandler;

import com.camel.oa.enums.ResourceStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ResourceStatus.class})
public class ResourceStatusEnumHandler extends BaseTypeHandler<ResourceStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ResourceStatus resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ResourceStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ResourceStatus.getEnumByValue(code);
    }

    @Override
    public ResourceStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ResourceStatus.getEnumByValue(code);
    }

    @Override
    public ResourceStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ResourceStatus.getEnumByValue(code);
    }
}
