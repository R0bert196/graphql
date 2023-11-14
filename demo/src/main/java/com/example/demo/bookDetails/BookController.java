package com.example.demo.bookDetails;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Random;


@Controller
public class BookController {

    Random rand = new Random();

    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    @QueryMapping
    public Author authorById(@Argument String id) {
        return Author.getById(id);
    }

    @SchemaMapping
    public Author author(Book book) {
        Author authorId = Author.getById(book.getAuthorId());
        return authorId;
    }

    @SchemaMapping
    public Book book(Author author) {
        return Book.getByAuthorId(author.getId());
    }

    @MutationMapping
    public Book createBook(@Argument String title, @Argument Integer pageCount, @Argument String authorId) {
        Book book = new Book(String.valueOf(rand.nextInt(1000)), title, pageCount, authorId);
        book.setAuthorId(authorId);
        return Book.save(book);
    }
}
