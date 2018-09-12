package com.lkssoft.appDoc.com;

public class AppDocContext {
	
	/*
	 * appDoc Context Name
	 */
	private String systemName;

	private AppDocSessionVO sessionVO;
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public AppDocSessionVO getSessionVO() {
		return sessionVO;
	}

	public void setSessionVO(AppDocSessionVO sessionVO) {
		this.sessionVO = sessionVO;
	}
}
