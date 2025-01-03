package mx.unam.dgtic.security.logout;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Logout Handler");
        Cookie[] cookies2 = request.getCookies();
        for (Cookie cookie : cookies2) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                log.info("Logout successfully");
                response.sendRedirect(request.getContextPath());
            }
        }
    }
}
