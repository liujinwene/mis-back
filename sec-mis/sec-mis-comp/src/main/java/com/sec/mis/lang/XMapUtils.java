package com.sec.mis.lang;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class XMapUtils extends MapUtils {

	public static BigDecimal getBigValue(Map<?, ?> map, Object key, BigDecimal defaultValue) {
		Object obj = MapUtils.getObject(map, key, defaultValue);
		return (BigDecimal) obj;
	}
}
