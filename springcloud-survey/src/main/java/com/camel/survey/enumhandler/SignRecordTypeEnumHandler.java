//package com.camel.survey.enumhandler;
//
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// *
// *                 ___====-_  _-====___
// *           _--^^^#####//      \\#####^^^--_
// *        _-^##########// (    ) \\##########^-_
// *       -############//  |\^^/|  \\############-
// *     _/############//   (@::@)   \\############\_
// *    /#############((     \\//     ))#############\
// *   -###############\\    (oo)    //###############-
// *  -#################\\  / VV \  //#################-
// * -###################\\/      \//###################-
// *_#/|##########/\######(   /\   )######/\##########|\#_
// *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
// *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
// *   `   `  `      `   / | |  | | \   '      '  '   '
// *                    (  | |  | |  )
// *                   __\ | |  | | /__
// *                  (vvv(VVV)(VVV)vvv)
// * <项目状态转换器>
// * @author baily
// * @since 1.0
// * @date 2019/10/31
// **/
//@MappedJdbcTypes(JdbcType.INTEGER)
//@MappedTypes({SignRecordType.class})
//public class SignRecordTypeEnumHandler extends BaseTypeHandler<SignRecordType> {
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SignRecordType resourceStatus, JdbcType jdbcType) throws SQLException {
//        preparedStatement.setInt(i, resourceStatus.getValue());
//    }
//
//    @Override
//    public SignRecordType getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        Integer code = resultSet.getInt(s);
//        return SignRecordType.getEnumByValue(code);
//    }
//
//    @Override
//    public SignRecordType getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        Integer code = resultSet.getInt(i);
//        return SignRecordType.getEnumByValue(code);
//    }
//
//    @Override
//    public SignRecordType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        int code = callableStatement.getInt(i);
//        return SignRecordType.getEnumByValue(code);
//    }
//}
