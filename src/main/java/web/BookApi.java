package web;

import domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import javax.ws.rs.Path;

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

    @PutMapping("/books/{id}")
    public void updateBook(@RequestBody Book book){
        bookService.update(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        Book book = new Book();
        book.setId(id);
        bookService.delete(book);
    }
}
