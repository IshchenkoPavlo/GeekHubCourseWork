package org.geekhub.pavlo.controller.web;

import org.geekhub.pavlo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    public MainController() {
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("isAdmin", isAdmin());
        return "Main";
    }

    @GetMapping("/error-page")
    public String showErrorPage(@RequestParam("errorText") String errorText, Model model) {
        model.addAttribute("errorText", errorText);
        return "ErrorPage";
    }

    @GetMapping("/error")
    public String showError(@RequestParam("errorText") String errorText, Model model) {
        model.addAttribute("errorText", errorText);
        return "ErrorPage";
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()));
    }

}
