package com.camel.oa.enumhandler;

import com.camel.oa.enums.ZsYesOrNo;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <是否枚举转换>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ZsYesOrNo.class})
public class ZsYesOrNoEnumHandler extends BaseTypeHandler<ZsYesOrNo> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ZsYesOrNo resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public ZsYesOrNo getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ZsYesOrNo.getEnumByValue(code);
    }

    @Override
    public ZsYesOrNo getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ZsYesOrNo.getEnumByValue(code);
    }

    @Override
    public ZsYesOrNo getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ZsYesOrNo.getEnumByValue(code);
    }
}
