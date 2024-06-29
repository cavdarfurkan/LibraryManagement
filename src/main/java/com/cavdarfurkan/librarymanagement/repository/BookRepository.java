package com.cavdarfurkan.librarymanagement.repository;

import com.cavdarfurkan.librarymanagement.auth.User;
import com.cavdarfurkan.librarymanagement.model.book.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAllByReader(User reader);

    List<Book> findByReaderNotNull();
}
