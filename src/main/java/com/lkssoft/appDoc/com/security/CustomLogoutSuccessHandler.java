package com.lkssoft.appDoc.com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	// @Autowired
	// private AuditService auditService;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		// String refererUrl = request.getHeader("Referer");
		// auditService.track("Logout from: " + refererUrl);


		if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
                System.out.println("User Successfully Logout");
                //you can add more codes here when the user successfully logs out,
                //such as updating the database for last active.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }		
		
		// perform other required operation
		response.setStatus(HttpStatus.OK.value());
		response.sendRedirect(request.getContextPath() + "/login?logout");

		super.onLogoutSuccess(request, response, authentication);
	}
}