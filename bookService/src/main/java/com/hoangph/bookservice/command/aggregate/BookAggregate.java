package com.hoangph.bookservice.command.aggregate;

import com.hoangph.bookservice.command.command.BookCreatedCommand;
import com.hoangph.bookservice.command.command.BookDeleteCommand;
import com.hoangph.bookservice.command.command.BookUpdatedCommand;
import com.hoangph.bookservice.command.event.BookCreatedEvent;
import com.hoangph.bookservice.command.event.BookDeleteEvent;
import com.hoangph.bookservice.command.event.BookUpdatedEvent;
import com.hoangph.bookservice.command.service.dto.request.BookRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAggregate {


    @AggregateIdentifier
    private  String id;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(BookCreatedCommand request){
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(request, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @CommandHandler
    public void handleUpdate(BookUpdatedCommand request){
        BookUpdatedEvent bookUpdated = new BookUpdatedEvent();
        BeanUtils.copyProperties(request, bookUpdated);
        AggregateLifecycle.apply(bookUpdated);
    }

    @CommandHandler
    public void handleDelete(BookDeleteCommand request){
        BookDeleteEvent bookDeleted = new BookDeleteEvent();
        BeanUtils.copyProperties(request, bookDeleted);
        AggregateLifecycle.apply(bookDeleted);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event){
        this.setId(event.getId());
        this.setAuthor(event.getAuthor());
        this.setName(event.getName());
        this.setIsReady(event.getIsReady());
    }
    @EventSourcingHandler
    public void on(BookUpdatedEvent event){
        this.setId(event.getId());
        this.setAuthor(event.getAuthor());
        this.setName(event.getName());
        this.setIsReady(event.getIsReady());
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event){
        this.setId(event.getId());
    }

}
