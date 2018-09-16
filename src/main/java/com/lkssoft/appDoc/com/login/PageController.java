package com.lkssoft.appDoc.com.login;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
		
	/**
	 * login page
	 * 
	 * @param error
	 * @param logout
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	 
	  model.setViewName("login");

	  return model;

	}


	/**
	 * for 403 access denied page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

	  ModelAndView model = new ModelAndView();

	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		model.addObject("username", userDetail.getUsername());
	  }

	  model.setViewName("403");
	  return model;

	}
	
	/**
	 * welcome page - full calendar view 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@RequestMapping(value = {"/", "/welcome**"}, method=RequestMethod.GET)
	public ModelAndView welcome() {
		
		SecurityContext context = SecurityContextHolder.getContext();
		ModelAndView mv = new ModelAndView("/schedule/scheduleBoard");
		mv.addObject("c_username", context.getAuthentication().getName());
		
		return mv;
	}
	
	/**
	 * login duplication
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login_duplicate", method=RequestMethod.GET)
	public ModelAndView login_duplicate() {
		
		ModelAndView mv = new ModelAndView("login_duplicate");
		return mv;
	}	
	
	/**
	 * admin page 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_ADMIN only!");
	  model.setViewName("admin");
	  return model;

	}	
}