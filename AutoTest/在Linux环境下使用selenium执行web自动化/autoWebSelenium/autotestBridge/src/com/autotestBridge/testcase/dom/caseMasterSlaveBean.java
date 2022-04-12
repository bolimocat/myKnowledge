package com.autotestBridge.testcase.dom;

public class caseMasterSlaveBean {

	private int id;
	private String source_info;
	private String master_info;
	private String describe_info;
	private String casename;
	
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource_info() {
		return source_info;
	}
	public void setSource_info(String source_info) {
		this.source_info = source_info;
	}
	public String getMaster_info() {
		return master_info;
	}
	public void setMaster_info(String master_info) {
		this.master_info = master_info;
	}
	public String getDescribe_info() {
		return describe_info;
	}
	public void setDescribe_info(String describe_info) {
		this.describe_info = describe_info;
	}
}
