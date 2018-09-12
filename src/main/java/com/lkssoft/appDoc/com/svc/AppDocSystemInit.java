package com.lkssoft.appDoc.com.svc;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.lkssoft.appDoc.com.AppDocContext;
import com.lkssoft.appDoc.com.AppDocContextLoader;

/**
 * appDoc Bean 로딩 후 context 초기화
 *  
 * @author suri
 *
 */
@PropertySource({ "classpath:config/property/systemInit.properties" })
public class AppDocSystemInit implements InitializingBean, DisposableBean{

	private static final Logger logger = LoggerFactory.getLogger(AppDocSystemInit.class);

	@Value("${appDoc.systemName}")
	private String systemName;
	
	@Override
	public void afterPropertiesSet() {
		logger.info("appDocSystemInit webapplication context 정보를 세팅한다.");
		
		// AppDocContext를 초기화 한다.
		AppDocContext ctx = AppDocContextLoader.getAppDocContext();
		ctx.setSystemName(systemName);
		
		logger.info("systemName : " + ctx.getSystemName());
	}
	
	@Override
	public void destroy() throws Exception{
		logger.info("system 종료");
	}
}