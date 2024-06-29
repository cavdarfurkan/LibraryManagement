package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.auth.User;
import com.cavdarfurkan.librarymanagement.auth.UserService;
import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.service.BookService;
import com.cavdarfurkan.librarymanagement.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReaderController {

    private final ReaderService readerService;
    private final BookService bookService;
    private final UserService userService;

    public ReaderController(ReaderService readerService, BookService bookService, UserService userService) {
        this.readerService = readerService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/reader/borrowed-books")
    public String borrowedBooks(Model model) {
        try {
            User user = userService.getCurrentUser();
            List<BookDTO> books = bookService.getBooksByUser(user);
            model.addAttribute("books", books);
            return "reader/borrowedBooks";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/borrow/{bookId}")
    public String borrow(@PathVariable Long bookId, Model model) {
        try {
            readerService.borrowBook(bookId);
            return "redirect:/books/" + bookId;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/purchase/{bookId}")
    public String purchase(@PathVariable Long bookId, Model model) {
        try {
            readerService.purchaseBook(bookId);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
