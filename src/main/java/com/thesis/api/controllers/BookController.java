package com.thesis.api.controllers;

import com.thesis.api.models.Book;
import com.thesis.api.services.book.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
})
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET: api/v1/book | Return all books ---------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<?> findAllBooks(){

        Collection<Book> books = bookService.findAll();

        return ResponseEntity.ok(books);
    }

    // POST: api/v1/book | Add a new book ----------------------------------------------------------------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBook(@RequestBody Book book){

        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        URI uri = URI.create(baseUrl + "/api/v1/book/" + 1/*ID HERE*/);

        return ResponseEntity.created(uri).body(uri);
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
        /*try {

        }
        catch (MyResourceNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, “Foo Not Found”, exc);
        }
        */
        System.out.println("DONE DELETE "+book_id);

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
}