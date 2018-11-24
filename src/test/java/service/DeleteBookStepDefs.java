package service;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.Book;
import org.junit.Assert;

public class DeleteBookStepDefs {

    private BookService bookService = new BookServiceImpl(new DateServiceImpl());

    @Given("^list of books$")
    public void list_of_books(DataTable arg1) throws Exception {
        arg1.getPickleRows().forEach(pickleRow -> {
            Book book = new Book();
            book.setId(Integer.parseInt(pickleRow.getCells().get(0).getValue()));
            book.setPublishingHouse(pickleRow.getCells().get(1).getValue());
            book.setYearOfPublishment(Integer.parseInt(pickleRow.getCells().get(2).getValue()));
            bookService.create(book);
        });
    }

    @When("^we delete books published by house \"([^\"]*)\" before (\\d+)$")
    public void we_delete_books_published_by_house_before(String arg1, int arg2) throws Exception {
        bookService.deleteByPublishingHouseAndYearOfPublishment(arg1, arg2);
    }

    @Then("^book with id (\\d+) should be deleted$")
    public void book_with_id_should_be_deleted(int arg1) throws Exception {
        try {
            bookService.read(arg1);
            Assert.fail("Exception should be thrown");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Then("^book with id (\\d+) should not be deleted$")
    public void book_with_id_should_not_be_deleted(int arg1) throws Exception {
        Assert.assertNotNull(bookService.read(arg1));
    }
}