package com.thesis.api.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.thesis.api.exceptions.ResourceNotFoundException;
import com.thesis.api.mappers.BookMapper;
import com.thesis.api.models.Book;
import com.thesis.api.models.dtos.BookDTO;
import com.thesis.api.models.dtos.NewBookDTO;
import com.thesis.api.services.book.BookService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
})
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    // GET: api/v1/book | Return all books ---------------------------------------------------------------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<BookDTO>> findAllBooks(){
        return ResponseEntity.ok(bookMapper.bookToBookDTO(bookService.findAll()));
    }

    // POST: api/v1/book | Add a new book ----------------------------------------------------------------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBook(@RequestBody NewBookDTO newBookDTO){

        try {
            Book book = bookService.add(bookMapper.newBookDTOToBook(newBookDTO));
            String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            URI uri = URI.create(baseUrl + "/" + book.getId());
            return ResponseEntity.created(uri).body(book);
        }catch (ConstraintViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validationViolations(ex));
        }
    }

    // PUT: api/v1/book/{book_id} | Update entire existing book object -----------------------------------------------
    @PutMapping(path = "/{book_id}")
    public ResponseEntity<?> updateBook(@PathVariable int book_id){

        return ResponseEntity.noContent().build();
    }

    // PATCH: api/v1/book/{book_id} | Update existing book partially -------------------------------------------------
    @PatchMapping
    public ResponseEntity<?> updateBookPartial(@PathVariable int book_id){

        return ResponseEntity.noContent().build();
    }

    // DELETE: api/v1/book/{book_id} | Delete a book by its id -------------------------------------------------------
    @DeleteMapping(path = "/{book_id}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<?> deleteBook(@AuthenticationPrincipal Jwt jwt, @PathVariable int book_id){

        try {
            System.out.println("DONE DELETE " + book_id);
        }catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found ", ex);
        }

        return ResponseEntity.noContent().build();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(path = "/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal Jwt jwt, Principal user)    {

        System.out.println("****** TEST endpoint ******");

        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/test2")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<?> test2(@AuthenticationPrincipal Jwt jwt, Principal user)    {

        System.out.println("***** TEST 2 endpoint ******");

        return ResponseEntity.ok(user);
    }

    // Method for extracting validation error messages from ConstraintViolationException
    private String validationViolations(ConstraintViolationException e){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        violations.forEach(v-> sb.append(v.getMessage() + ", "));
        sb.delete(sb.length()-2,sb.length());
        return sb.toString();
    }
}