package com.sec.mis.support.model;

 
import com.sec.mis.lang.BitStateUtil;
import com.sec.mis.lang.StringUtils;

public abstract class BaseModelWithImage extends BaseModelWithBitState {
	
	public final static Long OP_IS_USE_SYSTEM_ALBUM = 16L;//位存储布尔值：是否使用系统相册
	
	private static final long serialVersionUID = 7047433471685459631L;
	
	private String imageName = "";
	
	public abstract Long getUserFileRelativeId();

	public boolean isSystemAlbum() {
		return BitStateUtil.isState(OP_IS_USE_SYSTEM_ALBUM, this.getBitState());
	}
	
	public void setSystemAlbum(boolean systemAlbum){
		this.setBitState(BitStateUtil.setBitState(
				OP_IS_USE_SYSTEM_ALBUM, systemAlbum, this.getBitState()));
	}

	public String getImageName() {
		return imageName;
	}
	
	public boolean isRecommendImage() {
		return StringUtils.isEmpty(this.imageName);
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
}
