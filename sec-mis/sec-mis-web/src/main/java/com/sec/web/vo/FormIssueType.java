package com.sec.web.vo;

import java.util.ArrayList;
import java.util.List;

public class FormIssueType {
	
	private String name;
	
	private String label;
	
	private List<KeyValue> issueTypes;
	
	public FormIssueType(String name, String label){
		this.name = name;
		this.label = label;
		issueTypes = new ArrayList<KeyValue>();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String lable) {
		this.label = lable;
	}

	public List<KeyValue> getIssueTypes() {
		return issueTypes;
	}

	public void setIssueTypes(List<KeyValue> issueTypes) {
		this.issueTypes = issueTypes;
	}
	
	public void add(KeyValue kv){
		issueTypes.add(kv);
	}
}
