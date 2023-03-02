package com.thesis.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 150)
    @Size(max = 150, message = "Title maximum length is 150 characters")
    @Pattern(regexp = "^[a-zA-Z0-9-':., ]+$", message = "Title contains forbidden characters")
    @NotBlank(message = "Title cannot be null")
    private String title;
    @Column(nullable = false, length = 17)
    @Pattern(regexp = "[0-9-]{17}", message = "Invalid ISBN format")
    @NotBlank(message = "ISBN cannot be null")
    private String isbn;
    @Column(nullable = false)
    @NotNull(message = "Publish date cannot be null")
    @PastOrPresent(message = "Publish date cannot be in the future")
    private Date publishDate;

    /*private int price;
    private User author;*/
}