package com.thesis.api.models.dtos;

import lombok.Data;

@Data
public class NewBookDTO {
    private String title;
    private String isbn;
    private String publishDate;
}