package com.example.pokedex.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenController {
    @GetMapping("/dude")
    public String Dude(){
        return "dude";

    }
}
