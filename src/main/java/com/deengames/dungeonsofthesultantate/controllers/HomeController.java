package com.deengames.dungeonsofthesultanate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController
{
    @GetMapping("/")
    public String index()
    {
        return "Hello, Dungeons of the Sultanate!";
    }
}
