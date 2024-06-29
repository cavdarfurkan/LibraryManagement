package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.service.BookService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(@CurrentSecurityContext SecurityContext securityContext, Model model) {
        model.addAttribute("username", securityContext.getAuthentication().getName());
        model.addAttribute("authorities", securityContext.getAuthentication().getAuthorities());

        model.addAttribute("books", bookService.getBooks());
        return "index";
    }
}
