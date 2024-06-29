package com.cavdarfurkan.librarymanagement.service;

import com.cavdarfurkan.librarymanagement.auth.User;
import com.cavdarfurkan.librarymanagement.model.book.Book;
import com.cavdarfurkan.librarymanagement.model.book.BookDTO;
import com.cavdarfurkan.librarymanagement.model.book.BookDTOMapper;
import com.cavdarfurkan.librarymanagement.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getBooks() {
        return Streamable.of(bookRepository.findAll()).map(BookDTOMapper::toDTO).toList();
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        return BookDTOMapper.toDTO(book);
    }

    public List<BookDTO> getBooksByUser(User user) {
        return bookRepository.findAllByReader(user)
                .stream()
                .map(BookDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void borrowBook(Long bookId, User reader) throws Exception {
        if (isBorrowedBook(bookId)) {
            throw new Exception("This book is already borrowed");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(NoSuchElementException::new);
        book.setReader(reader);
    }

    @Transactional
    public void returnBook(Long bookId) throws Exception {
        if (!isBorrowedBook(bookId)) {
            throw new Exception("This book is not borrowed");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(NoSuchElementException::new);
        book.setReader(null);
    }

    public boolean isBorrowedBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new NoSuchElementException();
        }

        return book.get().getReader() != null;
    }

    public Book purchaseBook(Long bookId, User reader) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (isBorrowedBook(book.get().getId())) {
            throw new Exception("Can not purchase borrowed book");
        }

        if (reader.getMoney() >= book.get().getPrice()) {
            deleteBook(bookId);
            return book.get();
        } else {
            throw new Exception("You don't have enough money to purchase book");
        }
    }

    public BookDTO saveBook(BookDTO bookDTO) {
        Book savedBook = bookRepository.save(BookDTOMapper.toBook(bookDTO));
        return BookDTOMapper.toDTO(savedBook);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        Book updatedBook = bookRepository.save(BookDTOMapper.toBook(bookDTO));
        return BookDTOMapper.toDTO(updatedBook);
    }
}
