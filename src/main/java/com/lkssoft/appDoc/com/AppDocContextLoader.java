package com.lkssoft.appDoc.com;

public class AppDocContextLoader {
	
	public static AppDocContext appDocContext;

	public static void createAppDocContext() {
		if( appDocContext == null) {
			appDocContext = new AppDocContext();
		}
	}
	
	public static AppDocContext getAppDocContext() {
		return appDocContext;
	}
}
