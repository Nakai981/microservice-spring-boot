package com.hoangph.bookservice.query.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAllRequest {
    private HashMap<String, ?> books;
}
