package mx.unam.dgtic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class HomeController {

    @GetMapping({"/", "home", "inicio"})
    public String home() {
        return "home";
    }
}
