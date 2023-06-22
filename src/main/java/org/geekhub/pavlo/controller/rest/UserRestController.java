package org.geekhub.pavlo.controller.rest;

import org.geekhub.pavlo.model.User;
import org.geekhub.pavlo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping (value = "/api/v1/user-registration")
    public ResponseEntity<?> registrationUser(@RequestBody User user) {
        validateUser(user);
        userService.registration(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/v1/users")
    public ResponseEntity<?> postUser(@RequestBody User user) {
        validateUser(user);
        userService.add(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/api/v1/users")
    public ResponseEntity<?> pathUser(@RequestBody User user) {
        validateUser(user);
        userService.update(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    private void validateUser(User user) {
        if (user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name not filled");
        }
        if (user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password not filled");
        }
    }
}
