package com.cavdarfurkan.librarymanagement.service;

import com.cavdarfurkan.librarymanagement.auth.User;
import com.cavdarfurkan.librarymanagement.auth.UserService;
import com.cavdarfurkan.librarymanagement.model.book.Book;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {

    private final BookService bookService;
    private final UserService userService;

    public ReaderService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_READER')")
    public void borrowBook(Long bookId) throws Exception {
        User user = userService.getCurrentUser();
        bookService.borrowBook(bookId, user);
    }

    @PreAuthorize("hasRole('ROLE_READER')")
    public void purchaseBook(Long bookId) throws Exception {
        User user = userService.getCurrentUser();

        Book purchasedBook = bookService.purchaseBook(bookId, user);
        user.setMoney(user.getMoney() - purchasedBook.getPrice());
    }
}
