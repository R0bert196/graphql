package com.example.demo.bookDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Book {

    private String id;
    private String name;
    private int pageCount;
    private String authorId;

    public Book(String id, String name, int pageCount, String authorId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
    }

    private static List<Book> books = new ArrayList<>();

    static {
        createInitialBooks();
    }

    private static void createInitialBooks() {
        books.add(new Book("book-1", "Harry Potter and the Philosopher's Stone", 223, "author-1"));
        books.add(new Book("book-2", "Moby Dick", 635, "author-2"));
        books.add(new Book("book-3", "Interview with the vampire", 371, "author-3"));
    }

    public static Book getById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    public static Book getByAuthorId(String id) {
        return books.stream().filter(book -> book.getAuthorId().equals(id)).findFirst().orElse(null);
    }

    public static Book save(Book book) {
        books.add(book);
        return book;
    }

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String id) {
        authorId  = id;
    }
}