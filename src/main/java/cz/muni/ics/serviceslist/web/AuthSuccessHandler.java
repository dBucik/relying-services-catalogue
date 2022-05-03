package cz.muni.ics.serviceslist.web;

import static cz.muni.ics.serviceslist.web.GuiConstants.PATH_UNAUTHORIZED;
import static cz.muni.ics.serviceslist.web.configuration.SecurityConfiguration.ROLE_ADMIN;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Slf4j
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {
        if (authentication != null && authentication.getAuthorities() != null &&
            authentication.getAuthorities().contains(ROLE_ADMIN))
        {
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            log.debug("Authenticated user is not an admin, redirecting to unauthorized");
            SecurityContextHolder.clearContext();
            response.sendRedirect(PATH_UNAUTHORIZED);
        }
    }
}
