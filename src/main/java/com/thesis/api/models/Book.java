package com.thesis.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @Size(max = 150)
    @NotBlank(message = "Title cannot be null")
    private String title;
    @Column(nullable = false, length = 17)
    @Pattern(regexp = "[0-9-]{17}", message = "Invalid ISBN format")
    @NotBlank(message = "ISBN cannot be null")
    private String isbn;
    @Column(nullable = false, length = 10)
    @Pattern(regexp = "^[0-9]{2}-[0-9]{2}-[0-9]{4}", message = "Required date format is dd-mm-yyyy")
    @NotBlank(message = "Publish date cannot be null")
    private String publishDate;

    //private User author;
    //private Enum type;  // eBook / normal etc
}