package com.thesis.api.controllers;

import com.thesis.api.models.Book;
import com.thesis.api.services.book.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET: api/v1/book | return all books
    @GetMapping
    public ResponseEntity<?> findAllBooks(){

        Collection<Book> books = bookService.findAll();

        return ResponseEntity.ok(books);
    }
}
