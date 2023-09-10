package com.hoangph.bookservice.command.repository;

import com.hoangph.bookservice.command.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
