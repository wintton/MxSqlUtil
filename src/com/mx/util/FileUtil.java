package com.mx.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class FileUtil {
	/**
	 * user: Rex date: 2016��12��29�� ����12:25:04
	 * 
	 * @param dir
	 *            void TODO �ж�·���Ƿ���ڣ�����������򴴽�
	 */
	public static void mkdirs(String dir) {
		if (StringUtils.isEmpty(dir)) {
			return;
		}

		File file = new File(dir);
		if (file.isDirectory()) {
			return;
		} else {
			file.mkdirs();
		}
	}

	public static String createDirs(String dir) {
		if (StringUtils.isEmpty(dir)) {
			return "创建失败,路径为空";
		}

		File file = new File(dir);
		if (file.exists()) {
			return "创建失败,路径为已存在";
		}
		if (!file.mkdirs()) {
			return "创建失败,错误路径";
		}
		return dir + "创建成功";
	}

}