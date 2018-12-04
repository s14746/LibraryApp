package web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookApi {

    @RequestMapping("/hello")
    public String index() {
        return "This is non rest, just checking if everything works.";
    }
}
