package com.sec.mis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 验证，如 email
 * 
 *
 */
public class ValidUtils {

	public static boolean isMail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		return isMatch(email,
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	}

	public static boolean keyWordCheck(String keyWord) {
		String match = ".*?(select|update|delete|'|=|;|>|<|%|\\/|\\*).*?";
		return isMatch(keyWord, match);
	}

	public static boolean isMatch(String val, String patternStr) {
		if (StringUtils.isBlank(val)) return false;
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(val);
		return matcher.matches();
	}

	public static boolean isMobilePhone(String phone) {
		if (phone == null || phone.trim().length() != 11) {
			return false;
		}
		return isMatch(phone, "^(13|14|15|18|17)\\d{9}$");
	}
}
