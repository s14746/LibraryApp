package service;

import domain.Author;
import org.junit.Assert;
import org.junit.Test;

public class AuthorServiceImplTest {

    private AuthorService authorService = new AuthorServiceImpl();

    @Test
    public void createTest() {
        // when
        Author author = new Author();
        author.setId(1);
        author.setName("Adam");
        author.setSurname("Mickiewicz");

        // when
        authorService.create(author);

        // then
        Assert.assertNotNull(authorService.read(author.getId()));
    }
}