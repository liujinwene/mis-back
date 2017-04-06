package com.sec.mis.support.model;

import com.sec.mis.lang.BitStateUtil;

public abstract class BaseModelWithBitState extends BaseModel {

	private static final long serialVersionUID = 7047433471685459631L;
	
	private Long bitState = 0L;
	
	public Long getBitState() {
		return bitState;
	}
	
	public void setBitState(Long bitState) {
		this.bitState = bitState;
	}
	
	public boolean isState(Long operator) {
		return BitStateUtil.isState(operator, this.getBitState());
	}
    
    public void setState(Long operator, boolean val) {
    	this.setBitState(BitStateUtil.setBitState(operator, val, this.getBitState()));
	}
	
}
