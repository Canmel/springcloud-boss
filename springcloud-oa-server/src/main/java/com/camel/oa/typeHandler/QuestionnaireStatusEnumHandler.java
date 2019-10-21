package com.camel.oa.typeHandler;

import com.camel.oa.enums.ProjectStatus;
import com.camel.oa.enums.QuestionnaireStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({QuestionnaireStatus.class})
public class QuestionnaireStatusEnumHandler extends BaseTypeHandler<QuestionnaireStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, QuestionnaireStatus resourceStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, resourceStatus.getValue());
    }

    @Override
    public QuestionnaireStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Integer code = resultSet.getInt(s);
        return QuestionnaireStatus.getEnumByValue(code);
    }

    @Override
    public QuestionnaireStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Integer code = resultSet.getInt(i);
        return QuestionnaireStatus.getEnumByValue(code);
    }

    @Override
    public QuestionnaireStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return QuestionnaireStatus.getEnumByValue(code);
    }
}
