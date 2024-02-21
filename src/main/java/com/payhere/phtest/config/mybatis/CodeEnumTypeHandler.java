package com.payhere.phtest.config.mybatis;

import com.payhere.phtest.common.enumulation.CodeGroup;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@MappedTypes(CodeGroup.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeGroup> implements TypeHandler<CodeGroup> {

    private final Class<E> type;
    private final E[] enumConstants;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        this.type = type;
        this.enumConstants = type.getEnumConstants();
        if (!type.isInterface() && this.enumConstants == null) {
            throw new IllegalArgumentException(type.getSimpleName());
        }
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, CodeGroup parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, Optional.ofNullable(parameter).map(CodeGroup::getCode).orElse(null));
    }

    @Override
    public CodeGroup getResult(ResultSet rs, String columnName) throws SQLException {
        return this.getCode(rs.getString(columnName));
    }

    @Override
    public CodeGroup getResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.getCode(rs.getString(columnIndex));
    }

    @Override
    public CodeGroup getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.getCode(cs.getString(columnIndex));
    }

    /**
     * 코드값으로 enum값 반환
     *
     * @param code 코드 (DB에 저장되어 있는 값)
     * @return 코드
     */
    private CodeGroup getCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return Arrays.stream(this.enumConstants)
                .filter(e -> StringUtils.equals(e.getCode(), code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert %s to %s".formatted(code, this.type.getSimpleName())));
    }

}
