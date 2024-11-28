package mx.unam.dgtic.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.entity.User;
import mx.unam.dgtic.auth.service.UserService;
import mx.unam.dgtic.security.jwt.JWTTokenProvider;
import mx.unam.dgtic.security.request.JwtRequest;
import mx.unam.dgtic.security.request.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 16/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Controller
@Log4j2
public class HomeController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public HomeController(UserService userService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping({ "/", "/home", "/inicio"})
    public String home() {
        return "home";
    }

    @GetMapping("/index")
    public String index() {
        log.warn("Redirigido ");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        log.info("Logging user login success...");
        return "home";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        log.error("Login failure handler....");
        return "login";
    }

    @PostMapping("/token")
    public String authenticationToken(Model model, HttpSession session,
                                      @ModelAttribute LoginRequest loginRequest, HttpServletResponse res) throws Exception {
        log.info("Authentication token: {}", loginRequest);
        try{
            User user = userService.findByEmail(loginRequest.getUsername());
            log.info("User: {}", user);
            if (user.getIsActive().equals(Boolean.TRUE)){
                Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
                log.info("authentication {}", authentication);
                String jwtToken = jwtTokenProvider.generateJwtToken(authentication, user);
                log.info("jwtToken {}", jwtToken);
                JwtRequest jwtRequest = new JwtRequest(jwtToken, user.getId(), user.getEmail(),
                        jwtTokenProvider.getExpiryDuration(), authentication.getAuthorities());
                log.info("jwtRequest {}", jwtRequest);
                Cookie cookie = new Cookie("token", jwtToken);
                cookie.setMaxAge(Integer.MAX_VALUE);
                res.addCookie(cookie);
                session.setAttribute("msg","Login OK!");
            }

        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            session.setAttribute("msg","Bad Credentials");
            return "redirect:/login";
//            return "home";
        }
        return "redirect:/index";
//        return "home";
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS ", e);
        }
    }

}
