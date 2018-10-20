package service;

import domain.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class BookServiceImplTest {

    private BookService bookService;

    @Before
    public void setup() {
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

    @Test
    public void readAllTest() {

        // given
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Potop");
        book2.setAuthorId(2);
        book2.setYearOfPublishment(2012);
        book2.setPublishingHouse("Greg");
        book2.setAvailability(false);
        bookService.create(book2);

        Book book3 = new Book();
        book3.setId(3);
        book3.setTitle("Ogniem i mieczem");
        book3.setAuthorId(2);
        book3.setYearOfPublishment(2009);
        book3.setPublishingHouse("Greg");
        book3.setAvailability(true);
        bookService.create(book3);

        // when
        Collection<Book> books = bookService.readAll();

        // then
        Assert.assertNotNull(books);
        Assert.assertEquals(3, books.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void readNotFoundTest() {

        // given
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Potop");
        book2.setAuthorId(2);
        book2.setYearOfPublishment(2012);
        book2.setPublishingHouse("Greg");
        book2.setAvailability(false);
        bookService.create(book2);

        // when
        bookService.read(3);
    }

    @Test
    public void readFoundTest() {

        //given
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Potop");
        book2.setAuthorId(2);
        book2.setYearOfPublishment(2012);
        book2.setPublishingHouse("Greg");
        book2.setAvailability(false);
        bookService.create(book2);

        // when
        bookService.read(2);

        // then
        Assert.assertEquals(2, 2);
    }

    @Test
    public void updateTest() {

        // given
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        Book book2 = new Book();
        book2.setId(1);
        book2.setTitle("Pan Tadeusz");
        book2.setAuthorId(1);
        book2.setYearOfPublishment(1996);
        book2.setPublishingHouse("Greg");
        book2.setAvailability(true);

        // when
        bookService.update(book2);

        // then
        Book updateBook = bookService.read(book1.getId());
        Assert.assertEquals("Greg", updateBook.getPublishingHouse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantUpdateTest() {

        // given
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Pan Tadeusz");
        book2.setAuthorId(1);
        book2.setYearOfPublishment(1996);
        book2.setPublishingHouse("Greg");
        book2.setAvailability(true);

        // when
        bookService.update(book2);
    }
}