package com.sec.mis.lang;

import java.util.HashMap;
import java.util.Map;

public class BitStateUtil {

	private BitStateUtil() {}
	
	public static Long setBitState(Long operator, boolean val, Long bitState) {
		Long stateAfter = bitState;
		if(val)
			stateAfter = bitState | operator;
		else
			stateAfter = bitState & (~operator);
		return stateAfter;
	}
	
	public static boolean isState(Long operator, Long bitState) {
		Long ret = bitState & operator;
		return (ret.equals(0L)) ? false : true;
	}
	
	public static Long get2Pow(int exponent) {
		Double val = Math.pow(2, exponent);
		return val.longValue();
	}
	
	public static Map<String, Boolean> bitStets2BoolMap(Long bitState, Map<String, Long> stateValueMap) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		for (String key : stateValueMap.keySet()) {
			Long op = stateValueMap.get(key);
			Boolean val = BitStateUtil.isState(op, bitState);
			result.put(key, val); 
		}
		return result;
	}
	
	public static void main(String[] args) {
		//527312
		//System.out.println(BitStateUtil.isState(Const.OP_IS_PHONE_AUTH, 535504L));;
		//System.out.println(BitStateUtil.setBitState(8192L, true, 658386L));
		
		System.out.println(BitStateUtil.setBitState(512L, true, 0L));
		
	}
	
	
	
}
