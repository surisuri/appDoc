package com.lkssoft.appDoc.com.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.lkssoft.appDoc.com.AppDocContextLoader;
import com.lkssoft.appDoc.com.vo.AppDocContext;

/**
 * appDoc Bean
 *  
 * @author suri
 *
 */
@Service
@PropertySource({ "classpath:property/systemInit.properties" })
public class AppDocSystemInit implements InitializingBean, DisposableBean{

	private static final Logger logger = LoggerFactory.getLogger(AppDocSystemInit.class);

	@Value("${appDoc.systemName}")
	private String systemName;
	
	@Value("${appDoc.systemDescription}")
	private String systemDescription;
	
	@Override
	public void afterPropertiesSet() {
		logger.info("appDocSystemInit webapplication context start.");
		
		// AppDocContex loading
		AppDocContext ctx = AppDocContextLoader.getAppDocContext();
		ctx.setSystemName(systemName);
		ctx.setSystemDescription(systemDescription);
		
		logger.info("systemName : " + ctx.getSystemName() );
		logger.info("systemDescription : " + ctx.getSystemDescription() );
	}
	
	@Override
	public void destroy() throws Exception{
		logger.info("system destroy");
	}
}