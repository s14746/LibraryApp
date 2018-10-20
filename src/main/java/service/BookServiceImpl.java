package service;

import domain.Book;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private HashSet<Book> books = new HashSet<>();

    @Override
    public void create(Book newBook) {
        Optional<Book> book = readInternal(newBook.getId());
        if (book.isPresent()) {
            throw new IllegalArgumentException();
        }
        books.add(newBook);
    }

    @Override
    public Collection<Book> readAll() {
        return null;
    }

    @Override
    public Book read(int id) {
        return readInternal(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void update(Book newBook) {
        
    }

    @Override
    public void delete(Book deleteBook) {
        
    }

    private Optional<Book> readInternal(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

}
