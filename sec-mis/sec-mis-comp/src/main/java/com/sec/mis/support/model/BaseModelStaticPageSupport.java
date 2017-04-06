package com.sec.mis.support.model;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseModelStaticPageSupport extends BaseModel {
	
	private String autoLinkDivId = "";
	private Map<String, String> autoLinkKeyURL;
	private Map<String, String> dynamicURLParams = new HashMap<String, String>();
	private String relativePhysicalPathWithoutFileName = "";
	private String dynamicRelativeURL;
	private String staticRelativeURL;
	private boolean inited = false;
	private String url;
	private Boolean noContent;
	
	
	public void initStaticPageData() {
		
	}
	
	private void initStaticPageDataInner() {
		if(!this.inited){
			this.getDynamicURLParams().clear();
			this.initStaticPageData();
			this.inited = true;
		}
	}
	
	public String getStaticRelativeURL() {
		return staticRelativeURL;
	}
	
	public void setStaticRelativeURL(String staticRelativeURL) {
		this.staticRelativeURL = staticRelativeURL;
	}
	
	public String getDynamicRelativeURL() {
		return dynamicRelativeURL;
	}
	
	public void setDynamicRelativeURL(String dynamicRelativeURL) {
		this.dynamicRelativeURL = dynamicRelativeURL;
	}
	
	public String getAutoLinkDivId() {
		return autoLinkDivId;
	}
	
	public void setAutoLinkDivId(String autoLinkDivId) {
		this.autoLinkDivId = autoLinkDivId;
	}
	
	public Map<String, String> getAutoLinkKeyURL() {
		return autoLinkKeyURL;
	}
	
	public void setAutoLinkKeyURL(Map<String, String> autoLinkKeyURL) {
		this.autoLinkKeyURL = autoLinkKeyURL;
	}
	
	public void addParam(String name, String value) {
		this.getDynamicURLParams().put(name, value);
	}
	
	public Map<String, String> getDynamicURLParams() {
		return dynamicURLParams;
	}
	
	
	public String getRelativePhysicalPathWithoutFileName() {
		return relativePhysicalPathWithoutFileName;
	}
	
	public void setRelativePhysicalPathWithoutFileName(String relativePhysicalPathWithoutFileName) {
		this.relativePhysicalPathWithoutFileName = relativePhysicalPathWithoutFileName;
	}
	
}
