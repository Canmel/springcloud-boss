package com.camel.oa.enumhandler;

import com.camel.oa.enums.QuestionTyies;
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
 * <问题类型转换器>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({QuestionTyies.class})
public class QuestionTyiesEnumHandler extends BaseTypeHandler<QuestionTyies> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, QuestionTyies tyies, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, tyies.getValue());
    }

    @Override
    public QuestionTyies getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return QuestionTyies.getEnumByValue(code);
    }

    @Override
    public QuestionTyies getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return QuestionTyies.getEnumByValue(code);
    }

    @Override
    public QuestionTyies getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return QuestionTyies.getEnumByValue(code);
    }
}
