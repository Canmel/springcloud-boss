package com.camel.oa.typeHandler;

import com.camel.oa.enums.ZsMerchantStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsMerchantStatus.class})
public class ZsMerchantStatusEnumHandler extends BaseTypeHandler<ZsMerchantStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsMerchantStatus resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ZsMerchantStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsMerchantStatus.getEnumByValue(code);
    }

    @Override
    public ZsMerchantStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsMerchantStatus.getEnumByValue(code);
    }

    @Override
    public ZsMerchantStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsMerchantStatus.getEnumByValue(code);
    }
}
