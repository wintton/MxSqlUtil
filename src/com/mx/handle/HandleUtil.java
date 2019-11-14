package com.mx.handle;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.json.JSONException;
import org.json.JSONObject;

import com.mx.util.ExcelUtil;
import com.mx.util.TableRow;

public abstract class HandleUtil {

	public static boolean isExport = false;
	public static String address = "";
	public static String account = "";
	public static String password = "";
	public static String driver = "com.mysql.jdbc.Driver";
	private String name = "名称";
	protected Date date = new Date();
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public HandleUtil(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void handle(String path, JTextArea text_result) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();

		if (address == null || address.length() == 0 || address.indexOf("请输入") >= 0) {
			JOptionPane.showMessageDialog(null, "数据库地址不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		if (address.indexOf("jdbc:mysql://") < 0) {
			JOptionPane.showMessageDialog(null, "数据库地址格式不正确", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		if (account == null || account.length() == 0 || account.indexOf("请输入") >= 0) {
			JOptionPane.showMessageDialog(null, "数据库账号不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		if (password == null || password.length() == 0 || password.indexOf("请输入") >= 0) {
			JOptionPane.showMessageDialog(null, "数据库密码不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		text_result.setText("开始数据库连接\n");
		text_result.append("数据库地址：" + address + "\n");
		text_result.append("数据库账号：" + account + "\n");
		text_result.append("数据库密码：" + password + "\n");
		text_result.append("连接中\n");
		int i = 0;
		List<TableRow> repList = new ArrayList<>();
		try {

			Class.forName(driver);
			Connection sqlConn = DriverManager.getConnection(address, account, password);
			if (sqlConn.isClosed()) {
				text_result.append("连接失败，请检查地址，账号或密码是否正确\n");
				return;
			}
			text_result.append("连接成功\n");
			List<TableRow> datalist = ExcelUtil.readExcelRow(new File(path));
			text_result.append("读取文件成功\n");
			if (datalist.size() <= 1) {
				text_result.append("文件为空\n");
				sqlConn.close();
				text_result.append("\n与数据库断开连接\n");
				return;
			}
			TableRow headTable = new TableRow();
			headTable.setIndex(0);
			headTable.getColumnValue().add("访问时间");
			headTable.getColumnValue().add("访问参数");
			headTable.getColumnValue().add("访问结果");
			repList.add(headTable);

			try {
				for (TableRow each : datalist) {
					if (each.getParamstr().length() == 0) {
						continue;
					}
					i++;
					JSONObject reslut = getResult(getParamMap(each.getParamstr()), sqlConn);
					headTable = new TableRow();
					headTable.setIndex(i);
					text_result.append("\n访问参数：" + each.getParamstr() + "\n");
					text_result.append("访问结果：\n");
					String record = reslut == null ? "null" : reslut.toString();
					text_result.append(reslut.toString());
					text_result.selectAll();
					date.setTime(System.currentTimeMillis());
					headTable.getColumnValue().add(dateFormat.format(date));
					headTable.getColumnValue().add(each.getParamstr());
					headTable.getColumnValue().add(record);
					repList.add(headTable);
				}
			} catch (Exception e) {
				text_result.append("\n发生错误" + e.toString() + "\n");
			}
			sqlConn.close();
			text_result.append("\n与数据库断开连接\n");
			if (isExport) {
				text_result.append("\n开始导出\n");
				text_result.append("导出中\n");
				String filename = path.substring(0, path.length() - 4);
				ExcelUtil.writeExcel(new File(filename + "_rep.xls"), repList);
				text_result.append("写入成功\n");
				text_result.append("文件位置：" + filename + "_rep.xls\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text_result.append("\n访问错误：" + e.toString() + "\n");
			if (isExport) {
				text_result.append("\n开始导出\n");
				text_result.append("导出中\n");
				String filename = path.substring(0, path.length() - 4);
				try {
					ExcelUtil.writeExcel(new File(filename + "_rep.xls"), repList);
					text_result.append("写入成功\n");
					text_result.append("文件位置：" + filename + "_rep.xls\n");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					text_result.append("写入失败，请检查文件是否使用中\n");
					text_result.append("文件位置：" + filename + "_rep.xls\n");
				}
			}

		}
		text_result.append("\n操作完成\n");
		String timeshow = (System.currentTimeMillis() - time) / 1000 + "秒";
		text_result.append("\n总共耗时：" + timeshow);
		text_result.append("\n操作条数：" + i + "条");
		text_result.selectAll();

	}

	public abstract JSONObject getResult(HashMap<String, String> paramMap, Connection sqlConn) throws JSONException;

	public HashMap<String, String> getParamMap(String params) {
		String[] paramStrs = params.split("&");
		HashMap<String, String> result = new HashMap<>();
		for (String each : paramStrs) {
			String[] eachStrs = each.split("=");
			if (eachStrs.length == 2) {
				result.put(eachStrs[0], eachStrs[1]);
			}
		}
		return result;
	}

}