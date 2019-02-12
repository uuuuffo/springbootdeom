package com.bootdo.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MysqlDbUtils {

	public static final String sql_name = "mysql_database.sql";

	/**
	 * 数据库备份
	 * 
	 * @param host
	 *            数据库路径
	 * @param port
	 *            数据库端口
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param databaseName
	 *            数据库名称
	 * @param savePath
	 *            保存路径
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean dbBackup(String host, String port, String username, String password, String databaseName,
			String savePath) throws InterruptedException {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + sql_name), "utf8"));
			Process process = Runtime.getRuntime().exec(" mysqldump -h" + host + " -P" + port + " -u" + username + " -p"
					+ password + " --set-charset=UTF8 " + databaseName);
			InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				printWriter.println(line);
			}
			printWriter.flush();
			if (process.waitFor() == 0) {// 0 表示线程正常终止。
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 数据库表备份
	 * 
	 * @param host
	 *            数据库路径
	 * @param port
	 *            数据库端口
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param databaseName
	 *            数据库名称
	 * @param tableName
	 *            需要备份的表名
	 * @param savePath
	 *            保存路径
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean tableBackup(String host, String port, String username, String password, String databaseName,
			String tableName, String savePath) throws InterruptedException {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try {
			printWriter = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(savePath + tableName + ".sql"), "utf8"));
			Process process = Runtime.getRuntime().exec(" mysqldump -h" + host + " -P" + port + " -u" + username + " -p"
					+ password + " --set-charset=UTF8 " + databaseName + " " + tableName);
			InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				printWriter.println(line);
			}
			printWriter.flush();
			if (process.waitFor() == 0) {// 0 表示线程正常终止。
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 数据库还原
	 * 
	 * @param host
	 *            数据库路径
	 * @param port
	 *            数据库端口
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param databaseName
	 *            数据库名称
	 * @param savePath
	 *            保存路径
	 * @return
	 * @throws Exception
	 */
	public static boolean dbRecover(String host, String port, String username, String password, String databaseName,
			String savePath) throws Exception {
		File saveFile = new File(savePath + sql_name);
		if (!saveFile.exists()) {// 如果目录不存在
			return false;
		}
		// 获取操作数据库的相关属性
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("mysql -h" + host + " -P" + port + " -u" + username + " -p" + password
				+ " --default-character-set=utf8 " + databaseName);
		OutputStream outputStream = process.getOutputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(saveFile)));
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = br.readLine()) != null) {
			sb.append(str + "\r\n");
		}
		str = sb.toString();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
		writer.write(str);
		writer.flush();
		outputStream.close();
		br.close();
		writer.close();
		return true;
	}
}
