package com.camel.oa.typeHandler;

import com.camel.oa.enums.ProjectTyies;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ProjectTyies.class})
public class ProjectTyiesEnumHandler extends BaseTypeHandler<ProjectTyies> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ProjectTyies resourceTyies, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceTyies.getValue());
    }

    @Override
    public ProjectTyies getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ProjectTyies.getEnumByValue(code);
    }

    @Override
    public ProjectTyies getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ProjectTyies.getEnumByValue(code);
    }

    @Override
    public ProjectTyies getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ProjectTyies.getEnumByValue(code);
    }
}
