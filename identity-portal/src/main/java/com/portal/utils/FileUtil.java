package com.portal.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;


/**
 * @author shizhao
 * @since 2018/8/27
 * 文件工具类
 * */
public class FileUtil {
	/**
	 * 生成路径
	 * @param dir  文件路径
	 */
	public static void mkdirs(String dir){
		if(StringUtils.isEmpty(dir)){
			return;
		}
		
		File file = new File(dir);
		if(file.isDirectory()){
			return;
		} else {
			file.mkdirs();
		}
	}
}
