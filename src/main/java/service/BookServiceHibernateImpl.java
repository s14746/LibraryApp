package service;

import domain.Book;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Transactional
public class BookServiceHibernateImpl implements BookService {
    private final SessionFactory sessionFactory;
    private final DateService dateService;

    private HashSet<Book> books = new HashSet<>();
    private boolean shouldSetCreateTime = true;
    private boolean shouldSetUpdateTime = true;
    private boolean shouldSetLastReadingTime = true;

    public BookServiceHibernateImpl(SessionFactory sessionFactory, DateService dateService) {
        this.sessionFactory = sessionFactory;
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

        sessionFactory.getCurrentSession()
                .save(newBook);
    }

    @Override
    public Collection<Book> readAll() {
        List<Book> bookList = sessionFactory
                .getCurrentSession()
                .createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();

        bookList.forEach(book -> {
            if (shouldSetLastReadingTime) {
                book.setLastReadingTime(dateService.now());
            }
        });

        return bookList;
    }

    @Override
    public Book read(int id) {
        return readInternal(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Book> readByTitle(String title) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT b FROM Book b WHERE title LIKE :title", Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    @Override
    public void update(Book newBook) {
        Optional<Book> updateBook = readInternal(newBook.getId());
        if (!updateBook.isPresent()) {
            throw new IllegalArgumentException();
        }

        if (shouldSetUpdateTime) {
            newBook.setUpdateTime(dateService.now());
        }

        sessionFactory.getCurrentSession()
                .merge(newBook);
    }

    @Override
    public void delete(Book deleteBook) {
        Optional<Book> deleteBook2 = readInternal(deleteBook.getId());
        if (!deleteBook2.isPresent()) {
            throw new IllegalArgumentException();
        }

        sessionFactory
                .getCurrentSession()
                .createQuery("DELETE FROM Book where id = :id")
                .setParameter("id", deleteBook.getId())
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Book")
                .executeUpdate();
    }

    @Override
    public void deleteByPublishingHouseAndYearOfPublishment(String publishingHouse, int yearOfPublishment) {

        String regex = "^.*" + publishingHouse + ".*$";
        Pattern pattern = Pattern.compile(regex);
        books.removeIf(book -> pattern.matcher(book.getPublishingHouse()).find() && book.getYearOfPublishment() < yearOfPublishment);

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
        Optional<Book> optionalBook = sessionFactory
                .getCurrentSession()
                .byId(Book.class)
                .loadOptional(id);

        optionalBook.ifPresent(book -> {
            if (shouldSetLastReadingTime) {
                book.setLastReadingTime(dateService.now());
            }
        });

        return optionalBook;
    }
}
