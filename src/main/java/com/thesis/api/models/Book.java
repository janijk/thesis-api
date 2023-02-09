package com.thesis.api.models;

import jakarta.persistence.Entity;

import java.sql.Timestamp;

@Entity
public class Book {
    private int id;
    private String title;
    //private User author;
    private String isbn;
    private Timestamp publishDate;
    //private Enum type;  // eBook / normal etc


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }
}
