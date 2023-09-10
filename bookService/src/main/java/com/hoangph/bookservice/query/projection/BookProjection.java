package com.hoangph.bookservice.query.projection;

import com.hoangph.bookservice.command.domain.Book;
import com.hoangph.bookservice.command.repository.BookRepository;
import com.hoangph.bookservice.query.queies.BookQueryAll;
import com.hoangph.bookservice.query.queies.BookQueryId;
import com.hoangph.bookservice.query.service.dto.BookAllRequest;
import com.hoangph.bookservice.query.service.dto.BookResponse;
import com.hoangph.bookservice.query.service.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class BookProjection {
    private final BookRepository bookRepository;

    @QueryHandler
    public BookResponse handQueryById(BookQueryId bookQueryId){
        BookResponse bookResponse = new BookResponse();
        Book book = bookRepository.findById(bookQueryId.getId()).orElse(null);
        BeanUtils.copyProperties(book, bookResponse);
        return bookResponse;
    }

    @QueryHandler
    public BookAllRequest handQuery(BookQueryAll bookQueryAll){
        Page<BookResponse> books = bookRepository.findAll(bookQueryAll.getPageable()).map(book -> {
            BookResponse bookResponse = new BookResponse();
            BeanUtils.copyProperties(book, bookResponse);
            return bookResponse;
        });
        return new BookAllRequest(PageResponse.convert(books.getContent(), books.getTotalPages()));
    }
}
