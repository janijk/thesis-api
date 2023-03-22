package com.thesis.api.models.dtos;

import lombok.Data;

import java.util.Collection;

@Data
public class BookPageDTO {
    private int page;
    private int elements;
    private int totalPages;
    private long totalElements;
    private Collection<BookDTO> content;
}