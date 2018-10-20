package service;

import domain.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookServiceImplTest {

    private BookService bookService;

    @Before
    public void setup(){
        bookService = new BookServiceImpl();
    }

    @Test
    public void createTest() {

        // given

        Book book = new Book();
        book.setId(1);
        book.setTitle("Pan Tadeusz");
        book.setAuthorId(1);
        book.setYearOfPublishment(1996);
        book.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book.setAvailability(true);

        // when
        bookService.create(book);

        // then
        Assert.assertNotNull(bookService.read(book.getId()));
    }

}