package com.thesis.api.mappers;

import com.thesis.api.models.Book;
import com.thesis.api.models.dtos.BookDTO;
import com.thesis.api.models.dtos.NewBookDTO;
import com.thesis.api.services.book.BookService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
    @Autowired
    private BookService bookService;

    @Mapping(target = "id",ignore = true)
    public abstract Book newBookDTOToBook(NewBookDTO newBookDTO);
    public abstract BookDTO bookToBookDTO(Book book);
    public abstract Collection<BookDTO> bookToBookDTO(Collection<Book> book);

}
