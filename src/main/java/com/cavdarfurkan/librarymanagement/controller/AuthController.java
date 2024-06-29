package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.auth.UserRegisterDTO;
import com.cavdarfurkan.librarymanagement.auth.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "registerRoleChooser";
    }

    @GetMapping("/register-reader")
    public String register(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "registerReader";
    }

    @GetMapping("/register-publisher")
    public String registerPublisher(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "registerPublisher";
    }

    @PostMapping("/register-reader")
    public String processRegister(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registerReader";
        }
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "", "Passwords do not match");
            return "registerReader";
        }

        try {
            userService.registerUser(userRegisterDTO, "READER");
        } catch (Exception e) {
            bindingResult.rejectValue("username", "", e.getMessage());
            return "registerReader";
        }
        return "redirect:/login";
    }

    @PostMapping("/register-publisher")
    public String processRegisterPublisher(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registerPublisher";
        }
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "", "Passwords do not match");
            return "registerPublisher";
        }

        try {
            userService.registerUser(userRegisterDTO, "PUBLISHER");
        } catch (Exception e) {
            bindingResult.rejectValue("username", "", e.getMessage());
            return "registerPublisher";
        }
        return "redirect:/login";
    }
}
