package com.sec.mis.lang;

public class DateDistanceVo {
	/**
	 * 相差月数:小于一个与月返回0
	 */
	private int monthDistance;
	/** 相差天数 */
	private int dayDistance;

	public int getMonthDistance() {
		return monthDistance;
	}

	public void setMonthDistance(int monthDistance) {
		this.monthDistance = monthDistance;
	}

	public int getDayDistance() {
		return dayDistance;
	}

	public void setDayDistance(int dayDistance) {
		this.dayDistance = dayDistance;
	}

}
