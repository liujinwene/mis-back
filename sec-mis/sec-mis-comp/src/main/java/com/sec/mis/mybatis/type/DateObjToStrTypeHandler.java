package com.sec.mis.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.sec.mis.lang.DateUtils;

/**
 * java Date 与 数据库 varchar 之间的转换
 * 
 * @author mumubelinda
 *
 */
public class DateObjToStrTypeHandler implements TypeHandler<Date> {

	/**
	 * 数据库字段默认格式
	 */
	private static final String COLUMN_PATTERN_DEFAULT = "yyyyMMdd";
	private static final String[] COLUMN_PATTERNS_DEFAULT = { "yyyyMMdd",
			"yyyy-MM-dd" };

	public Date getResult(ResultSet rs, String columnName) throws SQLException {
		String columnValue = rs.getString(columnName);
		return this.getStringArray(columnValue);
	}

	public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
		String columnValue = rs.getString(columnIndex);
		return this.getStringArray(columnValue);
	}

	public Date getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String columnValue = cs.getString(columnIndex);
		return this.getStringArray(columnValue);
	}

	public void setParameter(PreparedStatement ps, int i, Date parameter,
			JdbcType jdbcType) throws SQLException {
		if (parameter == null)
			ps.setNull(i, Types.VARCHAR);
		else {
			ps.setString(i,
					DateFormatUtils.format(parameter, COLUMN_PATTERN_DEFAULT));
		}
	}

	private Date getStringArray(String columnValue) {
		if (columnValue == null)
			return null;

		return DateUtils.parseDate(columnValue, COLUMN_PATTERNS_DEFAULT);
	}

}
