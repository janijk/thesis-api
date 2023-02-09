package com.thesis.api.services.book;

import com.thesis.api.exceptions.BookNotFoundException;
import com.thesis.api.models.Book;
import com.thesis.api.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findById(Integer id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
    }

    @Override
    public Collection<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }else logger.warn("Book with ID: " + id + " not found");
    }

    @Override
    public boolean exists(Integer id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Set<Book> findAllByTitle(String title) {
        return bookRepository.findAllByTitle(title);
    }
}
