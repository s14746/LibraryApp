package web;

import domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.BookService;

@RestController
public class BookApi {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public void createBook(@RequestBody Book book) {
        bookService.create(book);
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable("id") int id) {
        return bookService.read(id);
    }
}
