package dev.frizio.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.frizio.lab.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
