package com.cavdarfurkan.librarymanagement.controller;

import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.service.BookService;
import com.cavdarfurkan.librarymanagement.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class PublisherController {

    private final PublisherService publisherService;
    private final BookService bookService;

    public PublisherController(PublisherService publisherService, BookService bookService) {
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    @GetMapping("/publisher/publish")
    public String publish(Model model) {
        model.addAttribute("book", new BookDTO());
        return "publisher/newBook";
    }

    @PostMapping("/publisher/publish")
    public String publish(@Valid @ModelAttribute("book") BookDTO book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "publisher/newBook";
        }

        try {
            BookDTO bookDTO = publisherService.publishBook(book);
            return "redirect:/books/" + bookDTO.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "publisher/newBook";
        }
    }

    @GetMapping("/publisher/published-books")
    public String publishedBooks(Model model) {
        try {
            Set<BookDTO> publishedBooks = publisherService.getAllPublishedBooksByUser();
            model.addAttribute("books", publishedBooks);
            return "publisher/publishedBooks";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/publisher/delete/{bookId}")
    public String delete(@PathVariable Long bookId, Model model) {
        try {
            publisherService.deleteBookById(bookId);
            return "redirect:/publisher/published-books";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/publisher/edit/{bookId}")
    public String edit(@PathVariable Long bookId, Model model) {
        try {
            BookDTO bookDTO = publisherService.getBookById(bookId);
            model.addAttribute("book", bookDTO);
            return "publisher/editBook";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PutMapping("/publisher/edit")
    public String edit(@Valid @ModelAttribute("book") BookDTO book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "publisher/editBook";
        }
        BookDTO updatedBook = bookService.updateBook(book);
        model.addAttribute("book", updatedBook);
        return "publisher/editBook";
    }
}
