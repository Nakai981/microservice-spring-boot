package com.hoangph.bookservice.query.controller;

import com.google.api.Page;
import com.hoangph.bookservice.command.command.BookCreatedCommand;
import com.hoangph.bookservice.command.domain.Book;
import com.hoangph.bookservice.command.service.dto.request.BookRequest;
import com.hoangph.bookservice.query.queies.BookQueryAll;
import com.hoangph.bookservice.query.queies.BookQueryId;
import com.hoangph.bookservice.query.service.dto.BookAllRequest;
import com.hoangph.bookservice.query.service.dto.BookResponse;
import com.hoangph.bookservice.query.service.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final QueryGateway queryGateway;

    @GetMapping("/v1/books/{id}")
    public ResponseEntity<BookResponse> findBookAxonById(@PathVariable String id){
        return ResponseEntity.ok()
                .body(queryGateway
                      .query(new BookQueryId(id), ResponseTypes.instanceOf(BookResponse.class))
                      .join());
    }
    @GetMapping("/v1/books")
    public ResponseEntity<BookAllRequest> findBookAxon(
          @PageableDefault(
                  size = 10,
                  page = 0,
                  sort = "id"
          ) Pageable pageable

    ) {
        if (PageResponse.rank(pageable, Book.class) > 0) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok()
                .body(queryGateway
                        .query(new BookQueryAll(pageable), ResponseTypes.instanceOf(BookAllRequest.class))
                        .join());
    }
}
