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
        return books;
    }

    @Override
    public Book read(int id) {
        return readInternal(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void update(Book newBook) {
        Optional<Book> updateBook = readInternal(newBook.getId());
        if (!updateBook.isPresent()) {
            throw new IllegalArgumentException();
        }
        books.removeIf(book -> book.getId() == newBook.getId());
        books.add(newBook);
    }

    @Override
    public void delete(Book deleteBook) {
        Optional<Book> deleteBook2 = readInternal(deleteBook.getId());
        if (!deleteBook2.isPresent()) {
            throw new IllegalArgumentException();
        }
        books.removeIf(book -> book.getId() == deleteBook.getId());
    }

    private Optional<Book> readInternal(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

}
