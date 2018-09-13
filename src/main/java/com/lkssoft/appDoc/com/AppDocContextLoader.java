package com.lkssoft.appDoc.com;

import org.springframework.stereotype.Service;

import com.lkssoft.appDoc.com.vo.AppDocContext;

@Service
public class AppDocContextLoader {
	
	public static AppDocContext appDocContext;

	public static void createAppDocContext() {
		if( appDocContext == null) {
			appDocContext = new AppDocContext();
		}
	}
	
	public static AppDocContext getAppDocContext() {
		if( appDocContext == null ) {
			appDocContext = new AppDocContext();
		}
		return appDocContext;
	}
}
