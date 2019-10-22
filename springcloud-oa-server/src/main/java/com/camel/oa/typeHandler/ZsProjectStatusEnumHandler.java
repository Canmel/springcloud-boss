package com.camel.oa.typeHandler;

import com.camel.oa.enums.ProjectStatus;
import com.camel.oa.enums.ZsProjectStatus;
import com.camel.oa.model.ZsProject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsProjectStatus.class})
public class ZsProjectStatusEnumHandler extends BaseTypeHandler<ZsProjectStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsProjectStatus resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ZsProjectStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsProjectStatus.getEnumByValue(code);
    }

    @Override
    public ZsProjectStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsProjectStatus.getEnumByValue(code);
    }

    @Override
    public ZsProjectStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsProjectStatus.getEnumByValue(code);
    }
}
