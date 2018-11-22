package service;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.Book;
import org.junit.Assert;

import java.util.List;

public class BookStepDefs {

    private BookService bookService = new BookServiceImpl(new DateServiceImpl());
    private List<Book> matchingBooks;

    @Given("^the list of books$")
    public void the_list_of_books(DataTable arg1) throws Exception {
        arg1.getPickleRows().forEach(pickleRow -> {
            Book book = new Book();
            book.setId(Integer.parseInt(pickleRow.getCells().get(0).getValue()));
            book.setTitle(pickleRow.getCells().get(1).getValue());
            bookService.create(book);
        });
    }

    @When("^we find books starting with title \"([^\"]*)\"$")
    public void we_find_books_starting_with_title(String arg1) throws Exception {
        matchingBooks = bookService.readByTitle(arg1);
    }

    @Then("^we have a result list (\\d+)$")
    public void we_have_a_result_list(int arg1) throws Exception {
        Book book = new Book();
        book.setId(arg1);
        Assert.assertTrue(matchingBooks.contains(book));
    }
}