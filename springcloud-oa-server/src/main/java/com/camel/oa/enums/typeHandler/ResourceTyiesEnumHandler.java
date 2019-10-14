package com.camel.oa.enums.typeHandler;

import com.camel.oa.enums.ResourceTyies;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ResourceTyies.class})
public class ResourceTyiesEnumHandler extends BaseTypeHandler<ResourceTyies> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ResourceTyies resourceTyies, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceTyies.getValue());
    }

    @Override
    public ResourceTyies getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ResourceTyies.getEnumByValue(code);
    }

    @Override
    public ResourceTyies getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ResourceTyies.getEnumByValue(code);
    }

    @Override
    public ResourceTyies getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ResourceTyies.getEnumByValue(code);
    }
}
