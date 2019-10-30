package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsProjectIndustryTypies;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsProjectIndustryTypies.class})
public class ZsProjectIndustryTypiesEnumHandler extends BaseTypeHandler<ZsProjectIndustryTypies> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsProjectIndustryTypies status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, status.getValue());
    }

    @Override
    public ZsProjectIndustryTypies getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsProjectIndustryTypies.getEnumByValue(code);
    }

    @Override
    public ZsProjectIndustryTypies getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsProjectIndustryTypies.getEnumByValue(code);
    }

    @Override
    public ZsProjectIndustryTypies getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsProjectIndustryTypies.getEnumByValue(code);
    }
}
