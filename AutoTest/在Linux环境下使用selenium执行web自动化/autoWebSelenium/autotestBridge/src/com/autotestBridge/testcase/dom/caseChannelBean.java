package com.autotestBridge.testcase.dom;

public class caseChannelBean {

	private int id;
	private String chname;
	private int sync_accord;
	private int sync_mode;
	private int accord_start;
	private int accord_algorithm;
	private int requery_latency;
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
	public String getChname() {
		return chname;
	}
	public void setChname(String chname) {
		this.chname = chname;
	}
	public int getSync_accord() {
		return sync_accord;
	}
	public void setSync_accord(int sync_accord) {
		this.sync_accord = sync_accord;
	}
	public int getSync_mode() {
		return sync_mode;
	}
	public void setSync_mode(int sync_mode) {
		this.sync_mode = sync_mode;
	}
	public int getAccord_start() {
		return accord_start;
	}
	public void setAccord_start(int accord_start) {
		this.accord_start = accord_start;
	}
	public int getAccord_algorithm() {
		return accord_algorithm;
	}
	public void setAccord_algorithm(int accord_algorithm) {
		this.accord_algorithm = accord_algorithm;
	}
	public int getRequery_latency() {
		return requery_latency;
	}
	public void setRequery_latency(int requery_latency) {
		this.requery_latency = requery_latency;
	}
	public String getDescribe_info() {
		return describe_info;
	}
	public void setDescribe_info(String describe_info) {
		this.describe_info = describe_info;
	}
}
