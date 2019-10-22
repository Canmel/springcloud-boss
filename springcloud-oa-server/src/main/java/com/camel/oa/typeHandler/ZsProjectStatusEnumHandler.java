package com.camel.oa.typeHandler;

import com.camel.oa.enums.ProjectStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ProjectStatus.class})
public class ProjectStatusEnumHandler extends BaseTypeHandler<ProjectStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ProjectStatus resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ProjectStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ProjectStatus.getEnumByValue(code);
    }

    @Override
    public ProjectStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ProjectStatus.getEnumByValue(code);
    }

    @Override
    public ProjectStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ProjectStatus.getEnumByValue(code);
    }
}
