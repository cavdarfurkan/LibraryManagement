package com.cavdarfurkan.librarymanagement.service;

import com.cavdarfurkan.librarymanagement.auth.User;
import com.cavdarfurkan.librarymanagement.auth.UserService;
import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.model.book.BookDTOMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final BookService bookService;
    private final UserService userService;

    public PublisherService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    @Transactional
    public BookDTO publishBook(BookDTO bookDTO) throws Exception {
        User user = userService.getCurrentUser();
        BookDTO publishedBook = bookService.saveBook(bookDTO);
        user.getPublishedBooks().add(BookDTOMapper.toBook(publishedBook));
        return publishedBook;
    }

    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public Set<BookDTO> getAllPublishedBooksByUser() throws Exception {
        User user = userService.getCurrentUser();
        return user.getPublishedBooks().stream()
                .map(BookDTOMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public BookDTO getBookById(Long id) throws Exception {
        User user = userService.getCurrentUser();
        BookDTO bookDTO = bookService.getBookById(id);
        boolean isOwnBook = user.getPublishedBooks()
                .stream()
                .anyMatch((book) -> bookDTO.getId().equals(book.getId()));

        if (!isOwnBook) {
            throw new Exception("User is not published this book");
        }

        return bookDTO;
    }

    @PreAuthorize("hasRole('ROLE_PUBLISHER')")
    public void deleteBookById(Long id) throws Exception {
        User user = userService.getCurrentUser();
        BookDTO bookDTO = bookService.getBookById(id);
        boolean isOwnBook = user.getPublishedBooks()
                .stream()
                .anyMatch((book) -> bookDTO.getId().equals(book.getId()));

        if (!isOwnBook) {
            throw new Exception("User is not published this book");
        }

        bookService.deleteBook(id);
    }
}
