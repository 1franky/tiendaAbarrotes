package mx.unam.dgtic.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/")
@Log4j2
public class HomeController {

    @GetMapping({ "/", "home", "inicio"})
    public String home() {
        return "home";
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
}
