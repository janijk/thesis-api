package com.thesis.api.services.book;

import com.thesis.api.models.Book;
import com.thesis.api.services.CRUDService;

import java.util.Set;

public interface BookService extends CRUDService<Book, Integer> {
    /**
     * Find all books where title like given string.
     * @param title
     * @return Set of book objects
     */
    Set<Book> findAllByTitle(String title);
}
