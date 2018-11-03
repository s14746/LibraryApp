package service;

import domain.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    private BookService bookService;
    private DateService dateService = mock(DateService.class);

    @Before
    public void setup() {
        bookService = new BookServiceImpl(dateService);
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
        Book book = bookService.read(2);

        // then
        Assert.assertEquals(2, book.getId());
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

    @Test(expected = IllegalArgumentException.class)
    public void deleteTest() {

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
        bookService.delete(book2);

        // then
        Assert.assertNotNull(bookService.read(1));
        Assert.assertNull(bookService.read(2));
    }

    @Test
    public void shouldSetCreateTimeWhenIsOn() {
        // given
        LocalDateTime mockedTime = LocalDateTime.parse("2018-01-01T10:00:01");
        when(dateService.now()).thenReturn(mockedTime);

        bookService.setShouldSetCreateTime(true);

        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);

        bookService.create(book1);

        // when
        Book book = bookService.read(book1.getId());
        Assert.assertEquals(book.getCreateTime(), mockedTime);
    }

    @Test
    public void shouldNotSetCreateTimeWhenIsOff() {
        // given
        bookService.setShouldSetCreateTime(false);

        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);

        bookService.create(book1);

        // when
        Book book = bookService.read(book1.getId());
        Assert.assertNull(book.getCreateTime());
    }

    @Test
    public void shouldSetReadTimeWhenInOn() {
        //given
        LocalDateTime mockedTime = LocalDateTime.parse("2018-12-31T23:50:55");
        when(dateService.now()).thenReturn(mockedTime);

        bookService.setShouldSetLastReadingTime(true);

        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        // when
        Book book = bookService.read(book1.getId());

        // then
        Assert.assertEquals(book.getLastReadingTime(), mockedTime);
    }

    @Test
    public void shouldNotSetReadTimeWhenInOff() {
        //given
        bookService.setShouldSetLastReadingTime(false);

        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Pan Tadeusz");
        book1.setAuthorId(1);
        book1.setYearOfPublishment(1996);
        book1.setPublishingHouse("Beskidzka Oficyna Wydawnicza");
        book1.setAvailability(true);
        bookService.create(book1);

        // when
        Book book = bookService.read(book1.getId());

        // then
        Assert.assertNull(book.getLastReadingTime());
    }

    @Test
    public void shouldSetUpdateTimeWhenInOn() {
        // given
        LocalDateTime mockedTime = LocalDateTime.parse("2018-12-31T23:50:55");
        when(dateService.now()).thenReturn(mockedTime);

        bookService.setShouldSetUpdateTime(true);

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
        Book updateBook = bookService.read(book2.getId());
        Assert.assertEquals(updateBook.getUpdateTime(), mockedTime);
    }

    @Test
    public void shouldNotSetUpdateTimeWhenInOff() {
        // given
        bookService.setShouldSetUpdateTime(false);

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
        Book updateBook = bookService.read(book2.getId());
        Assert.assertNull(updateBook.getUpdateTime());
    }

    @Test
    public void shouldSetReadTimeForAllWhenIsOn() {

        // given
        LocalDateTime mockedTime = LocalDateTime.parse("2018-12-31T23:50:55");
        when(dateService.now()).thenReturn(mockedTime);

        bookService.setShouldSetLastReadingTime(true);

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
        for (Book book : books) {
            Assert.assertEquals(book.getLastReadingTime(), mockedTime);
        }
    }

    @Test
    public void shouldNotSetReadTimeForAllWhenIsOn() {

        // given
        bookService.setShouldSetLastReadingTime(false);

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
        for (Book book : books) {
            Assert.assertNull(book.getLastReadingTime());
        }
    }
}