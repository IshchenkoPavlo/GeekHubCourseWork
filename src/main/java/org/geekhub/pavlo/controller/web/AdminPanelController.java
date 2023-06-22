package org.geekhub.pavlo.controller.web;

import org.geekhub.pavlo.model.Role;
import org.geekhub.pavlo.model.User;
import org.geekhub.pavlo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminPanelController {
    private final UserService userService;

    @Autowired
    public AdminPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin-panel")
    public String showAdminPanel() {
        return "AdminPanel";
    }

    @GetMapping("/user-edit/{id}")
    public String editUser(@PathVariable("id") Integer userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("metodName", "patch");
        model.addAttribute("pageCaption", "Edit user");
        model.addAttribute("buttonCaption", "Update user");
        return "EditUser";
    }

    @GetMapping("/user-add")
    public String editUser(Model model) {
        User user = new User();
        user.setName("");
        user.setPassword("");
        user.setRole("");
        model.addAttribute("user", user);

        model.addAttribute("roles", Role.values());
        model.addAttribute("metodName", "post");
        model.addAttribute("pageCaption", "Add new user");
        model.addAttribute("buttonCaption", "Add user");
        return "EditUser";
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
