package io.swagger.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {
    // @RequestMapping(value = "/")
    // public String index() {
    //     System.out.println("swagger-ui.html");
    //     return "redirect:swagger-ui.html";
    // }
}