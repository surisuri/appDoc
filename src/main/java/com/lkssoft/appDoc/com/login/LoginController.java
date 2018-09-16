package com.lkssoft.appDoc.com.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lkssoft.appDoc.com.security.CustomLogoutSuccessHandler;

@Controller
public class LoginController { 
	
	@RequestMapping(value = "/perform_logout", method = RequestMethod.GET)
	public ModelAndView perform_logout(HttpServletRequest request, HttpServletResponse response) {

		CustomLogoutSuccessHandler cls = new CustomLogoutSuccessHandler();
		
		Authentication atc = SecurityContextHolder.getContext().getAuthentication();
		try {
			cls.onLogoutSuccess(request, response, atc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  ModelAndView model = new ModelAndView();
	  model.setViewName("login?logout");

	  return model;

	}
}