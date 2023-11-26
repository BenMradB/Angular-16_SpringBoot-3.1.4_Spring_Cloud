package com.bilel.SpringBoot_TP01.restcontrollers;

import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.entities.Token;
import com.bilel.SpringBoot_TP01.entities.User;
import com.bilel.SpringBoot_TP01.events.RegistrationEvent;
import com.bilel.SpringBoot_TP01.services.RoleService;
import com.bilel.SpringBoot_TP01.services.TokenService;
import com.bilel.SpringBoot_TP01.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("")
    public User register(@RequestBody User user, final HttpServletRequest request) {
        Role role = roleService.findRoleById(2L);
        userService.saveUser(user);
        user.setRoles(new ArrayList<>());
        userService.grantRoleToUser(user, role);
        // Publish event
        publisher.publishEvent(new RegistrationEvent(user, applicationURL(request)));
        return user;
    }

    public String applicationURL(HttpServletRequest request) {
        // APP_URL = http://${SERVER_NAME}:${PORT}${CONTEXT_PATH}
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String token) {
        return "Hello World" + token;
    }
}
