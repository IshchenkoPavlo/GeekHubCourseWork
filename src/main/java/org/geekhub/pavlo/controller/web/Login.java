package org.geekhub.pavlo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Login {
    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, ModelMap model) {
        if (request.getParameter("error") == null) {
            model.addAttribute("error", "");
        } else {
            model.addAttribute("error", "Invalid username and password!");
        }

        return "Login";
    }
}
