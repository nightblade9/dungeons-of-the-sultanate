package com.deengames.dungeonsofthesultanate.web.home;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController
{
    @GetMapping("/")
    // User is not available here, because this page is unauthenticated (no security context).
    public String index()
    {
        return "index"; // index.html
    }
}
