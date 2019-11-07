package com.camel.oa.enumhandler;

import com.camel.oa.enums.ZsGender;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ___====-_  _-====___
 * _--^^^#####//      \\#####^^^--_
 * _-^##########// (    ) \\##########^-_
 * -############//  |\^^/|  \\############-
 * _/############//   (@::@)   \\############\_
 * /#############((     \\//     ))#############\
 * -###############\\    (oo)    //###############-
 * -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 * _#/|##########/\######(   /\   )######/\##########|\#_
 * |/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 * `  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 * `   `  `      `   / | |  | | \   '      '  '   '
 * (  | |  | |  )
 * __\ | |  | | /__
 * (vvv(VVV)(VVV)vvv)
 * <是否枚举转换>
 *
 * @author baily
 * @date 2019/10/31
 * @since 1.0
 **/
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsGender.class})
public class ZsGenderEnumHandler extends BaseTypeHandler<ZsGender> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsGender resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ZsGender getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsGender.getEnumByValue(code);
    }

    @Override
    public ZsGender getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsGender.getEnumByValue(code);
    }

    @Override
    public ZsGender getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsGender.getEnumByValue(code);
    }
}
