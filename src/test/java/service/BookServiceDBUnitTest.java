package service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import configuration.BookConfiguration;
import configuration.DBUnitConfiguration;
import configuration.DatabaseConfiguration;
import configuration.DateConfiguration;
import domain.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles({"mock-date"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DateConfiguration.class,
        DatabaseConfiguration.class,
        BookConfiguration.class,
        DBUnitConfiguration.class
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})

public class BookServiceDBUnitTest {

    @Autowired
    private BookService bookService;

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/addBookData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void bookCreateTest() {

        assertEquals(10, bookService.readAll().size());

        Book book = new Book();
        book.setId(11);
        book.setTitle("Dziady cz.3");
        book.setAuthorName("Adam");
        book.setAuthorSurname("Mickiewicz");
        bookService.create(book);

        assertEquals(11, bookService.readAll().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/readAllData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void readAllTest() {
        assertEquals(10, bookService.readAll().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/readAllData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void readTest() {
        assertEquals(5, bookService.read(5).getId());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/updateData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateTest() {
        Book updatingBook = bookService.read(6);

        updatingBook.setTitle("Janko Muzykantko");
        bookService.update(updatingBook);
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/deleteData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteTest() {
        Book deleteBook = bookService.read(3);

        bookService.delete(deleteBook);
        assertEquals(9, bookService.readAll().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/deleteAllData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteAllTest() {

        bookService.deleteAll();
        assertEquals(0, bookService.readAll().size());
    }

    @Test
    @DatabaseSetup("/addBookData.xml")
    public void findByTitleTest() {

        List<Book> searchedBooks = bookService.readByTitle("miecz");
        assertEquals(1, searchedBooks.size());
    }
}
