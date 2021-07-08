package com.example.pokedex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerMappingController {

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/swagger-ui/index.html";
    }
}