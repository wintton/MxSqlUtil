package com.mx.handle;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

//处理管理类
public class HandleManager {

	private List<HandleUtil> handleList;

	public HandleManager() {
		super();
		handleList = new ArrayList<HandleUtil>();
	}

	public HandleManager(List<HandleUtil> handleList) {
		super();
		this.handleList = handleList;
	}

	public boolean add(HandleUtil handleUtil) {
		return handleList.add(handleUtil);
	}

	public boolean remove(HandleUtil handleUtil) {
		return handleList.remove(handleUtil);
	}

	public List<HandleUtil> getHandleList() {
		return handleList;
	}

	public void setHandleList(List<HandleUtil> handleList) {
		this.handleList = handleList;
	}

	public void handle(String exportfilepath, JTextArea text_result, int index) {
		HandleUtil handleUtil = handleList.get(index);
		handleUtil.handle(exportfilepath, text_result);
	}
}
