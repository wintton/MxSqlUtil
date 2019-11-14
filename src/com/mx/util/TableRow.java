package com.mx.util;

import java.util.ArrayList;
import java.util.List;

public class TableRow {
	private int index;
	private List<String> columnValue = new ArrayList<>();
	private String result = "";
	private StringBuffer paramstr = new StringBuffer();

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<String> getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(List<String> columnValue) {
		this.columnValue = columnValue;
	}

	public String getParamstr() {

		if (result.equals("") && paramstr.length() > 1) {
			result = paramstr.deleteCharAt(paramstr.length() - 1).toString();
		}
		return result;
	}

	public void setParamstr(StringBuffer paramstr) {
		this.paramstr = paramstr;
	}

	public void append(String str) {
		// try {
		// str = URLEncoder.encode(str, "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		str = str.trim();
		this.paramstr.append(str);
	}
}
