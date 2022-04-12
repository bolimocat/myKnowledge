package com.autotestBridge.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.autotestBridge.dom.dbLink;

public class commonkit {

	/**
	 * 按行读取配置文件
	 * @param filePath
	 * @return
	 */
		@SuppressWarnings("resource")
		public ArrayList<dbLink> fetchLine(String filePath) {
			ArrayList<dbLink> result = new ArrayList<dbLink>();
			try {
				File file = new File(filePath);
				BufferedReader reader = null;
				String line = null;
				reader = new BufferedReader(new FileReader(filePath));
				while((line = reader.readLine())!=null) {
					if(!(line.startsWith("#"))) {
						dbLink link = new dbLink();
						link.setDbline(line);
						result.add(link);
					}
					
				}
			} catch (Exception efetchLine) {
				efetchLine.printStackTrace();
			}
			return result;
		}
		
		/**
		 * 保存截图
		 * @param saveFile
		 * @param savePath
		 * @param f
		 */
		public void saveSnapshot(String saveFile,String savePath,File f ) {
			try {
	      		int byteread = 0;
	      		int bytesum = 0;
					InputStream inStream = new FileInputStream(f);
					FileOutputStream fs = new FileOutputStream(savePath+"/"+saveFile);
					byte[] buffer = new byte[1444]; 
					try {
						while ( (byteread = inStream.read(buffer)) != -1) { 
							bytesum += byteread; //字节数 文件大小 
						    fs.write(buffer, 0, byteread); 
						   
						}
						inStream.close(); 
						if(f.delete()){
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //读入原文件 
		}
}
