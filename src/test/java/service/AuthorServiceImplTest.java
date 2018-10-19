package service;

import domain.Author;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class AuthorServiceImplTest {

    private AuthorService authorService;

    @Before
    public void setup() {
        authorService = new AuthorServiceImpl();
    }


    @Test
    public void createTest() {

        // given
        Author author = new Author();
        author.setId(1);
        author.setName("Adam");
        author.setSurname("Mickiewicz");

        // when
        authorService.create(author);

        // then
        Assert.assertNotNull(authorService.read(author.getId()));
    }

    @Test
    public void readAllTest() {

        // given
        Author author1 = new Author();
        author1.setId(1);
        author1.setName("Adam");
        author1.setSurname("Mickiewicz");
        authorService.create(author1);

        Author author2 = new Author();
        author2.setId(2);
        author2.setName("Henryk");
        author2.setSurname("Sienkiewicz");
        authorService.create(author2);

        Author author3 = new Author();
        author3.setId(3);
        author3.setName("Jan");
        author3.setSurname("Kochanowski");
        authorService.create(author3);

        // when
        Collection<Author> authors = authorService.readAll();

        // then
        Assert.assertNotNull(authors);
        Assert.assertEquals(3, authors.size());
    }
}