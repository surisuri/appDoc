package com.lkssoft.appDoc.com.controller;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lkssoft.appDoc.com.exception.BizException;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

@ControllerAdvice
public class GlobalExeptionHandler {
	
	@ExceptionHandler(BizException.class)
	public String handleBizException(Exception e, Model model) {
		
		model.addAttribute("type", e.getClass().getSimpleName()	);
		model.addAttribute("msg", e.getMessage());
		
		return "error";
	}
	
	@ExceptionHandler(MyBatisSystemException.class)
	public String handleMBSException(Exception e, Model model) {
		
		model.addAttribute("type", e.getClass().getSimpleName()	);
		model.addAttribute("msg", e.getMessage());
		
		return "error";
	}
	
	@ExceptionHandler(MysqlDataTruncation.class)
	public String handleMSDTException(Exception e, Model model) {
		
		model.addAttribute("type", e.getClass().getSimpleName()	);
		model.addAttribute("msg", e.getMessage());
		
		return "error";
	} 
}
