package com.thesis.api.controllers;

import com.thesis.api.exceptions.ResourceNotFoundException;
import com.thesis.api.mappers.BookMapper;
import com.thesis.api.models.Book;
import com.thesis.api.models.dtos.BookDTO;
import com.thesis.api.models.dtos.BookPageDTO;
import com.thesis.api.models.dtos.NewBookDTO;
import com.thesis.api.services.book.BookService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://web.postman.co"
})
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    // GET: api/v1/book?page=value | Return page of books -------------------------------------------------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookPageDTO> findAllBooks(@RequestParam(defaultValue = "0") int page){
        Page<Book> bookPage = bookService.findAllByPage(page);
        return ResponseEntity.ok(bookMapper.bookPageToBookPageDTO(bookPage));
    }

    // POST: api/v1/book | Add a new book ----------------------------------------------------------------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_user')")
    public ResponseEntity<BookDTO> addBook(@RequestBody NewBookDTO newBookDTO){
        try {
            Book book = bookService.add(bookMapper.newBookDTOToBook(newBookDTO));
            String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            URI uri = URI.create(baseUrl + "/" + book.getId());
            return ResponseEntity.created(uri).body(bookMapper.bookToBookDTO(book));
        }catch (ConstraintViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validationViolations(ex));
        }
    }

    // PUT: api/v1/book/{book_id} | Update entire existing book object -----------------------------------------------
    @PutMapping(path = "/{book_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_user') or hasAuthority('ROLE_admin')")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO bookDTO, @PathVariable int book_id){
        if (bookDTO.getId() != book_id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided ID's don't match");
        }
        try {
            bookService.update(bookMapper.bookDTOToBook(bookDTO));
        }catch (ConstraintViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validationViolations(ex));
        }
        return ResponseEntity.noContent().build();
    }

    // DELETE: api/v1/book/{book_id} | Delete a book by its id -------------------------------------------------------
    @DeleteMapping(path = "/{book_id}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<?> deleteBook(@PathVariable int book_id){
        try {
            bookService.deleteById(book_id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /*@ExceptionHandler({JsonParseException.class})
    public void handleException(JsonParseException e) {
        System.out.println(e.getMessage());
    }*/

    // Method for extracting validation error messages from ConstraintViolationException
    private String validationViolations(ConstraintViolationException e){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        violations.forEach(v-> sb.append(v.getMessage()).append(", "));
        sb.delete(sb.length()-2,sb.length());
        return sb.toString();
    }
}