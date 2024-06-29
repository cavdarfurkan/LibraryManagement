package com.cavdarfurkan.librarymanagement.service;

import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.model.book.BookDTOMapper;
import com.cavdarfurkan.librarymanagement.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibrarianService {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public LibrarianService(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public List<BookDTO> getBorrowedBooks() {
        return bookRepository.findByReaderNotNull().stream()
                .map(BookDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public void returnBook(Long bookId) throws Exception {
        bookService.returnBook(bookId);
    }
}
