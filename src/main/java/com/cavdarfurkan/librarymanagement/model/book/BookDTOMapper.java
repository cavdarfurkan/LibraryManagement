package com.cavdarfurkan.librarymanagement.model.book;

public class BookDTOMapper {
    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        return dto;
    }

    public static Book toBook(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        return book;
    }
}
