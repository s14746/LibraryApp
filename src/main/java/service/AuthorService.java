package service;

import domain.Author;

import java.util.Collection;

public interface AuthorService {

    void create(Author author);
    Collection<Author> readAll();
    Author read(int id);
    void update(Author author);
    void delete(Author author);

}
