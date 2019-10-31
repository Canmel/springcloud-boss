package com.camel.oa.utils;

import com.baomidou.mybatisplus.enums.IEnum;
import com.camel.core.enums.BaseEnum;
import com.camel.oa.enums.ResourceStatus;
import com.camel.oa.enums.ResourceTyies;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.Serializable;
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
 * <>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({ResourceStatus.class, ResourceTyies.class})
public class UniversalEnumHandler extends BaseTypeHandler<IEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IEnum iEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, iEnum);
    }

    @Override
    public IEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return ResourceStatus.getEnumByValue(code);
    }

    @Override
    public IEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return ResourceStatus.getEnumByValue(code);
    }

    @Override
    public IEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ResourceStatus.getEnumByValue(code);
    }
}
