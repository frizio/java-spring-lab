package dev.frizio.lab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.frizio.lab.model.Book;
import dev.frizio.lab.model.BookDto;
import dev.frizio.lab.model.BookRequestDto;
import dev.frizio.lab.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(this::toDto);
    }

    public BookDto createBook(BookRequestDto request) {
        Book book = new Book(request.title(), request.author(), request.year());
        return toDto(bookRepository.save(book));
    }

    public Optional<BookDto> updateBook(Long id, BookRequestDto request) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.update(request.title(), request.author(), request.year());
                    return toDto(bookRepository.save(book));
                });
    }

    public boolean deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return true;
                })
                .orElse(false);
    }

    private BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
    }
}
