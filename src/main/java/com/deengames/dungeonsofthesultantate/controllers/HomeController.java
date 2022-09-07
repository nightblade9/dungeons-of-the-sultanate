package com.deengames.dungeonsofthesultanate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("authenticatedAs", "night blade!");
        return "index"; // index.html
    }
}
