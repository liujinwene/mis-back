package com.sec.mis.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

public class XNumberUtils extends NumberUtils {

	public static boolean eq(BigDecimal num1, BigDecimal num2) {
		if (num1 != null && num2 != null) {
			return num1.compareTo(num2) == 0;
		}
		return false;
	}

	/**
	 * 大于：if num1 > num2 return true ; else return false;
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean gt(BigDecimal num1, BigDecimal num2) {
		BigDecimal _num1 = num1 == null ? BigDecimal.ZERO : num1;
		BigDecimal _num2 = num2 == null ? BigDecimal.ZERO : num2;
		return _num1.compareTo(_num2) > 0;
	}

	/**
	 * 大于等于：if num1 >= num2 return true ; else return false;
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean ge(BigDecimal num1, BigDecimal num2) {
		BigDecimal _num1 = num1 == null ? BigDecimal.ZERO : num1;
		BigDecimal _num2 = num2 == null ? BigDecimal.ZERO : num2;
		return _num1.compareTo(_num2) >= 0;
	}

	public static boolean lt(BigDecimal num1, BigDecimal num2) {
		return gt(num2, num1);
	}

	public static boolean le(BigDecimal num1, BigDecimal num2) {
		return ge(num2, num1);
	}

	public static String format(double value, int scale, RoundingMode roundingMode) {
		NumberFormat fmt = NumberFormat.getNumberInstance();
		fmt.setMinimumFractionDigits(scale);
		fmt.setMaximumFractionDigits(scale);
		fmt.setRoundingMode(roundingMode);
		fmt.setGroupingUsed(false);
		return fmt.format(value);
	}

	public static double parse(String value) throws ParseException {
		NumberFormat fmt = NumberFormat.getNumberInstance();
		fmt.setGroupingUsed(true);
		return fmt.parse(value).doubleValue();
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(format(123233.123, 2, RoundingMode.FLOOR));
		System.out.println(parse("1,123,000.123"));
	}

	/**
	 * 获取一个n位随机数，并且n位数不重复
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomCodeStr(Integer length) {

		Set<Integer> set = getRandomNumber(length);

		// 使用迭代器

		Iterator<Integer> iterator = set.iterator();

		// 临时记录数据

		String temp = "";

		while (iterator.hasNext()) {

			temp += iterator.next();

		}

		return temp;

	}

	private static Set<Integer> getRandomNumber(Integer length) {

		// 使用SET以此保证写入的数据不重复

		Set<Integer> set = new HashSet<Integer>();

		// 随机数

		Random random = new Random();

		while (set.size() < length) {

			// nextInt返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）

			// 和指定值（不包括）之间均匀分布的 int 值。

			set.add(random.nextInt(10));

		}

		return set;

	}

	public static String formatNumber(int value, int scale) {

		String numStr = "" + value;
		int numScale = numStr.length();
		if (numScale < scale) {
			for (int i = 0; i < scale - numScale; i++) {
				numStr = "0" + numStr;
			}
		}
		return numStr;
	}

}
