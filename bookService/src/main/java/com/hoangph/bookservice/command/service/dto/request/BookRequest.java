package com.hoangph.bookservice.command.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
