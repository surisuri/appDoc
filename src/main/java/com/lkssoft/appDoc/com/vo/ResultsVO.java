package com.lkssoft.appDoc.com.vo;

import java.util.List;

public class ResultsVO<T> {
	private boolean result;

	private List<T> objList;

	private String msg;
	
	public List<T> getObjList() {
		return objList;
	}

	public void setObjList(List<T> objList) {
		this.objList = objList;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
