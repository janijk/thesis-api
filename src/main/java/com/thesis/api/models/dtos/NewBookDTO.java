package com.thesis.api.models.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class NewBookDTO {
    private String title;
    private String isbn;
    private Date publishDate;
}