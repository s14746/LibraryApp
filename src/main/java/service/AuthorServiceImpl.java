package service;

import domain.Author;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private HashSet<Author> authors = new HashSet<>();

    @Override
    public void create(Author newAuthor) {
        Optional<Author> author = readInternal(newAuthor.getId());
        if (author.isPresent()) {
            throw new IllegalArgumentException();
        }
        authors.add(newAuthor);
    }

    @Override
    public Collection<Author> readAll() {
        return null;
    }

    @Override
    public Author read(int id) {
        return readInternal(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public void delete(Author author) {

    }

    private Optional<Author> readInternal(int id) {
        return authors.stream()
                .filter(author -> author.getId() == id)
                .findFirst();
    }
}
