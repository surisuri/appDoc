package com.lkssoft.appDoc.com.vo;

public class AppDocContext {
	
	/*
	 * appDoc Context Name
	 */
	private String systemName;

	/*
	 * appDoc Description 
	 */
	private String systemDescription;
	
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

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}
}
