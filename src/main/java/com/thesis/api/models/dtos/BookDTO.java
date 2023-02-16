package com.thesis.api.models.dtos;

import lombok.Data;

@Data
public class BookDTO {
    private int id;
    private String title;
    private String isbn;
    private String publishDate;
}
