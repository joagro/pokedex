package com.example.pokedex.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String Dude() {
        return "this entrypoint is open for only the admin dude";

    }
}
