package mate.academy.book_store;

import java.math.BigDecimal;
import mate.academy.book_store.model.Book;
import mate.academy.book_store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("Steven King");
                book.setDescription("special horror");
                book.setTitle("Strange man");
                book.setCoverImage("image");
                book.setPrice(BigDecimal.TEN);

                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }

}
