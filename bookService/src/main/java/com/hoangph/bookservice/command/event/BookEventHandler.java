package com.hoangph.bookservice.command.event;

import com.hoangph.bookservice.command.domain.Book;
import com.hoangph.bookservice.command.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookEventHandler {
    private final BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookDeleteEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.delete(book);
    }

}
