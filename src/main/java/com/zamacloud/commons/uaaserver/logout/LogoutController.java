package com.zamacloud.commons.uaaserver.logout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Fact S Musingarimi
 * 5/9/19
 * 10:54 AM
 */
@Controller
public class LogoutController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping({"/logout"})
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        (new SecurityContextLogoutHandler()).logout(request, null, null);

        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            LOGGER.warn("Logout failure {}", e.getMessage());
        }

    }
}
