package com.lkssoft.appDoc.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MeasuringInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(MeasuringInterceptor.class); 
	
	/**
	 * Dispatcher servlet에서 controller로 진입하기 전 실행
	 *  
	 * @param request
	 * @param response
	 * @param handler 
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("mi.beginTime", System.currentTimeMillis());
		return true;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param handler 
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long beginTime = (long) request.getAttribute("mi.beginTime");
		long endTime = System.currentTimeMillis();
		
		logger.debug(request.getRequestURI() + "����ð� : " + (endTime - beginTime));
	}
}