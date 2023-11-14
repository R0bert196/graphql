package com.example.demo.bookDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Author {

    private String id;
    private String firstName;
    private String lastName;

    private List<Book> books;

    public Author(String id, String firstName, String lastName, List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    private static List<Author> authors = Arrays.asList(
            new Author("author-1", "Joanne", "Rowling", new ArrayList<>()),
            new Author("author-2", "Herman", "Melville", new ArrayList<>()),
            new Author("author-3", "Anne", "Rice", new ArrayList<>())
    );

    public static Author getById(String id) {
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst().orElse(null);
    }

    public String getId() {
        return id;
    }
}