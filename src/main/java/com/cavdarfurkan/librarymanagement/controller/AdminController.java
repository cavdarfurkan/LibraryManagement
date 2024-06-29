package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.auth.UserDetailsDTO;
import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.service.AdminService;
import com.cavdarfurkan.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final BookService bookService;

    public AdminController(AdminService adminService, BookService bookService) {
        this.adminService = adminService;
        this.bookService = bookService;
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/adminHome";
    }

    @GetMapping("/admin/user/{userId}")
    public String userDetails(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", adminService.getUserDetails(userId));
        model.addAttribute("publishedBooks", adminService.getPublishedBooksByUserId(userId));
        return "admin/userDetails";
    }

    @PutMapping("/admin/user/edit")
    public String edit(@Valid @ModelAttribute("user") UserDetailsDTO userDetailsDTO, Model model,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/userDetails";
        }

        UserDetailsDTO updatedUser = adminService.updateUser(userDetailsDTO);
        model.addAttribute("user", updatedUser);
        model.addAttribute("publishedBooks", adminService.getPublishedBooksByUserId(Long.valueOf(updatedUser.getId())));
        return "admin/userDetails";
    }

    @DeleteMapping("admin/user/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        adminService.deleteUserById(userId);
        return "redirect:/admin/home";
    }


    @DeleteMapping("/admin/delete/{bookId}")
    public String delete(@PathVariable Long bookId, String userId) {
        adminService.deleteBookById(bookId);
        return "redirect:/admin/user/" + userId;
    }

    @GetMapping("/admin/edit/{bookId}")
    public String edit(@PathVariable Long bookId, Model model) {
        try {
            BookDTO bookDTO = adminService.getBookById(bookId);
            model.addAttribute("book", bookDTO);
            return "publisher/editBook";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PutMapping("/admin/edit")
    public String edit(@Valid @ModelAttribute("book") BookDTO book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "publisher/editBook";
        }
        BookDTO updatedBook = bookService.updateBook(book);
        model.addAttribute("book", updatedBook);
        return "publisher/editBook";
    }
}
