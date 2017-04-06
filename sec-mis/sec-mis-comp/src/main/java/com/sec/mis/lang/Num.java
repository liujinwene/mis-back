package com.sec.mis.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Num {

	private BigDecimal base;

	private Num(BigDecimal base) {
		this.base = base;
	}

	public static Num create(double val) {
		return create(String.valueOf(val));
	}

	public static Num create(String val) {
		return create(new BigDecimal(val));
	}

	public static Num create(BigDecimal val) {
		return new Num(val);
	}

	/**
	 * 加上
	 * 
	 * @param val
	 * @return
	 */
	public Num add(BigDecimal val) {
		base = base.add(val);
		return this;
	}

	/**
	 * 加上
	 * 
	 * @param val
	 * @return
	 */
	public Num add(String val) {
		return add(new BigDecimal(val));
	}

	/**
	 * 加上
	 * 
	 * @param val
	 * @return
	 */
	public Num add(double val) {
		return add(String.valueOf(val));
	}

	/**
	 * 减去
	 * 
	 * @param val
	 * @return
	 */
	public Num sub(BigDecimal val) {
		base = base.subtract(val);
		return this;
	}

	/**
	 * 减去
	 * 
	 * @param val
	 * @return
	 */
	public Num sub(String val) {
		return this.sub(new BigDecimal(val));
	}

	/**
	 * 减去
	 * 
	 * @param val
	 * @return
	 */
	public Num sub(Double val) {
		return this.sub(String.valueOf(val));
	}

	/**
	 * 乘以
	 * 
	 * @param val
	 * @return
	 */
	public Num mul(BigDecimal val) {
		base = base.multiply(val);
		return this;
	}

	/**
	 * 乘以
	 * 
	 * @param val
	 * @return
	 */
	public Num mul(double val) {
		return mul(String.valueOf(val));
	}

	/**
	 * 乘以
	 * 
	 * @param val
	 * @return
	 */
	public Num mul(String val) {
		return mul(new BigDecimal(val));
	}

	/**
	 * 除以
	 * 
	 * @param val
	 * @return
	 */
	public Num div(String val, int scale, RoundingMode mode) {
		return this.div(new BigDecimal(val), scale, mode);
	}

	/**
	 * 除以
	 * 
	 * @param val
	 * @return
	 */
	public Num div(double val, int scale, RoundingMode mode) {
		return this.div(String.valueOf(val), scale, mode);
	}

	/**
	 * 除以
	 * 
	 * @param val
	 * @return
	 */
	public Num div(BigDecimal val, int scale, RoundingMode mode) {
		base = base.divide(val, scale, mode);
		return this;
	}

	/**
	 * @see BigDecimal#movePointLeft(int)
	 * @return
	 */
	public Num left(int n) {
		base = base.movePointLeft(n);
		return this;
	}

	/**
	 * @see BigDecimal#movePointRight(int)
	 * @return
	 */
	public Num right(int n) {
		base = base.movePointRight(n);
		return this;
	}

	public BigDecimal bigValue() {
		return base;
	}

	public BigDecimal bigValue(int scale, RoundingMode roundingMode) {
		return base.setScale(scale, roundingMode);
	}

	public double doubleValue() {
		return base.doubleValue();
	}

	public int intValue() {
		return base.intValue();
	}

	/**
	 * 精确度后的数字直接去掉
	 * 
	 * @param scale
	 * @return
	 */
	public Num floor(int scale) {
		base = base.setScale(scale, RoundingMode.FLOOR);
		return this;
	}

	public double floorValue(int scale) {
		return base.setScale(scale, RoundingMode.FLOOR).doubleValue();
	}

	public Num halfup(int scale) {
		base = base.setScale(scale, RoundingMode.HALF_UP);
		return this;
	}

	public double halfupValue(int scale) {
		return base.setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 格式化double 为小数两位数
	 * 
	 * @param sum
	 * @return
	 */
	public static String formatDouble(Double sum) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(sum);
	}

	/**
	 * 格式化double 为小数两位数，返回double
	 * 
	 * @param sum
	 * @return
	 */
	public static Double formatDoubleForDouble(Double sum) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(sum));
	}
	
	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(100);
		System.out.println(a);
		System.out.println(Num.create(a).add(100).bigValue());
		System.out.println(a);
//
//		System.out.println(Num.create(12.923).add(12.825).halfup(2).doubleValue());
//		System.out.println(Num.create(12.923).add(12.825).floor(2).doubleValue());
//		System.out.println(Num.create(18).left(2).doubleValue());
		
		BigDecimal val = new BigDecimal(100.1234567);
		System.out.println(val.setScale(2, RoundingMode.UP));
		System.out.println(val);
		
	}

}
