package mate.academy.bookshop;

import java.math.BigDecimal;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookShopApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book1 = new Book();
            book1.setTitle("Harry Potter and Philosopher's Stone");
            book1.setAuthor("J. K. Rowling");
            book1.setIsbn("123456");
            book1.setPrice(BigDecimal.valueOf(100));
            book1.setDescription("Fantasy novel written by British");
            book1.setCoverImage("1");

            Book book2 = new Book();
            book2.setTitle("Harry Potter and the Chamber of Secrets");
            book2.setAuthor("J. K. Rowling");
            book2.setIsbn("1234567");
            book2.setPrice(BigDecimal.valueOf(150));
            book2.setCoverImage("1");

            Book book3 = new Book();
            book3.setTitle("Harry Potter and the Prisoner of Azkaban");
            book3.setAuthor("J. K. Rowling");
            book3.setIsbn("1234568");
            book3.setPrice(BigDecimal.valueOf(125));

            bookService.save(book1);
            bookService.save(book2);
            bookService.save(book3);

            bookService.findAll().forEach(System.out::println);
        };
    }
}
