package com.autotestBridge.resource;

public class resource {
	
	public static String BASEURL = "http://172.16.110.33:8080";
	public static String ERRPICPATH = "/home/lming/图片";	//错误图片保存路径。
	public static String CORRPICPATH = "/home/lming/图片";	//正确图片保存路径
	public static int STSLEEP = 500;
	public static int MNSLEEP = 200;
	
	//用例目录
	public static String CASEPTH="./testcase/";
	//datasource用例
	public static String DSCASE="caseDatasource.csv";
	//datatable用例
	public static String TBCASE="caseDatatable.csv";
	//canal用例
	public static String CLCASE="caseCanal.csv";
	//master slave用例
	public static String MSCASE="caseMasterSlave.csv";
	//channel用例
	public static String CHCASE="caseChannel.csv";
	//pipeline用例
	public static String PICASE="casePipeline.csv";
	
	


}
