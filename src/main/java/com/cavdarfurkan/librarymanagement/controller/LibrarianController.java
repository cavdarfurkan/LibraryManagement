package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.service.BookService;
import com.cavdarfurkan.librarymanagement.service.LibrarianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LibrarianController {

    private final LibrarianService librarianService;
    private final BookService bookService;

    public LibrarianController(LibrarianService librarianService, BookService bookService) {
        this.librarianService = librarianService;
        this.bookService = bookService;
    }

    @GetMapping("/librarian/return-books")
    public String returnBooks(Model model) {
        model.addAttribute("books", librarianService.getBorrowedBooks());
        return "librarian/returnBooks";
    }

    @PostMapping("/return/{bookId}")
    public String returnBook(@PathVariable Long bookId, Model model) {
        try {
            librarianService.returnBook(bookId);
            return "redirect:/librarian/return-books";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/librarian/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/";
    }
}
