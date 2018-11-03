package service;

import domain.Book;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private HashSet<Book> books = new HashSet<>();
    private final DateService dateService;
    private boolean shouldSetCreateTime;
    private boolean shouldSetUpdateTime;
    private boolean shouldSetLastReadingTime;

    public BookServiceImpl(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public void create(Book newBook) {
        Optional<Book> book = readInternal(newBook.getId());
        if (book.isPresent()) {
            throw new IllegalArgumentException();
        }
        if (shouldSetCreateTime) {
            newBook.setCreateTime(dateService.now());
        }
        books.add(newBook);
    }

    @Override
    public Collection<Book> readAll() {
        for (Book book : books) {
            if (shouldSetLastReadingTime) {
                book.setLastReadingTime(dateService.now());
            }
        }
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
        if (shouldSetUpdateTime) {
            newBook.setUpdateTime(dateService.now());
        }
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

    public void setShouldSetCreateTime(boolean shouldSetCreateTime) {
        this.shouldSetCreateTime = shouldSetCreateTime;
    }

    public void setShouldSetUpdateTime(boolean shouldSetUpdateTime) {
        this.shouldSetUpdateTime = shouldSetUpdateTime;
    }

    public void setShouldSetLastReadingTime(boolean shouldSetLastReadingTime) {
        this.shouldSetLastReadingTime = shouldSetLastReadingTime;
    }

    private Optional<Book> readInternal(int id) {
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (shouldSetLastReadingTime && optionalBook.isPresent()) {
            optionalBook.get().setLastReadingTime(dateService.now());
        }

        return optionalBook;
    }
}
