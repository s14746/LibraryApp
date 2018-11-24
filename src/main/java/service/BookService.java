package service;

import domain.Book;

import java.util.Collection;
import java.util.List;

public interface BookService {

    void create(Book book);
    Collection<Book> readAll();
    Book read(int id);
    List<Book> readByTitle(String title);
    void update(Book book);
    void delete(Book book);
    void deleteByPublishingHouseAndYearOfPublishment(String publishingHouse, int yearOfPublishment);
    void setShouldSetCreateTime(boolean shouldSetCreateTime);
    void setShouldSetUpdateTime(boolean shouldSetUpdateTime);
    void setShouldSetLastReadingTime(boolean shouldSetLastReadingTime);
}
