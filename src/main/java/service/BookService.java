package service;

import domain.Book;

import java.util.Collection;

public interface BookService {

    void create(Book book);
    Collection<Book> readAll();
    Book read(int id);
    void update(Book book);
    void delete(Book book);
}