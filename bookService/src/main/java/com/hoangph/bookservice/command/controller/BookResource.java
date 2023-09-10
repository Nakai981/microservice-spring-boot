package com.hoangph.bookservice.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangph.bookservice.command.command.BookCreatedCommand;
import com.hoangph.bookservice.command.command.BookDeleteCommand;
import com.hoangph.bookservice.command.command.BookUpdatedCommand;
import com.hoangph.bookservice.command.domain.Book;
import com.hoangph.bookservice.command.repository.BookRepository;
import com.hoangph.bookservice.command.service.dto.request.BookRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookResource {
    private final ObjectMapper mapper;

    private final CommandGateway commandGateway;
    private final BookRepository bookRepository;

    @PostMapping("/v1/books")
    public ResponseEntity<String> addBookAxon(@RequestBody @Valid BookRequest request){
        request.setId(UUID.randomUUID().toString());
        commandGateway.sendAndWait(mapper.convertValue(request, BookCreatedCommand.class));
        return ResponseEntity.created(URI.create("/api/v1/books/id"))
                .body("Add success");
    }


    @PutMapping("/v1/books")
    public ResponseEntity<String> updateBookAxon(@RequestBody @Valid BookRequest request){
        if (request.getId() == null)
            return ResponseEntity.badRequest().build();
        Book book = bookRepository.findById(request.getId()).orElse(null);
        if (book == null)
            return ResponseEntity.notFound().build();
        commandGateway.sendAndWait(mapper.convertValue(request, BookUpdatedCommand.class));
        return ResponseEntity.ok()
                .body("Updated success");
    }

    @DeleteMapping("/v1/books/{id}")
    public ResponseEntity<String> deleteBookAxon(@PathVariable String id){
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            return ResponseEntity.notFound().build();
        commandGateway.sendAndWait(new BookDeleteCommand(id));
        return ResponseEntity.ok()
                .body("Delete success");
    }


}
