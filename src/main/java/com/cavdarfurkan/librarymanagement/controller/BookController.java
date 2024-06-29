package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{bookId}")
    @PreAuthorize("hasRole('ROLE_READER')")
    public String books(@PathVariable("bookId") Long bookId, Model model) {
        try {
            BookDTO bookDTO = bookService.getBookById(bookId);
            model.addAttribute("book", bookDTO);
            model.addAttribute("isBorrowed", bookService.isBorrowedBook(bookId));
            return "book";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
