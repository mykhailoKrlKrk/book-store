package mate.academy.book.store.repository;

import java.util.List;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books with category = 'horror', "
            + "expected result: list with two books")
    @Sql(scripts = {
            "classpath:database/books/repository/before/"
                    + "before_findAllByCategoryId_horrorCategoryBooks_ReturnListWithTwoBooks.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:database/books/repository/"
                + "after/after_findAllByCategoryId_horrorCategoryBooks_ReturnListWithTwoBooks.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_horrorCategoryBooks_ReturnListWithTwoBooks() {
        List<Book> actual = bookRepository.findAllByCategoryId(1L);
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Find all books with category = 'adventure', "
            + "expected result: list with one book")
    @Sql(scripts = {
            "classpath:database/books/repository/before/"
                    + "before_findAllByCategoryId_adventureCategoryBooks_ReturnListWithOneBook.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:database/books/repository/after/"
                + "after_findAllByCategoryId_adventureCategoryBooks_ReturnListWithOneBook.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_adventureCategoryBooks_ReturnListWithOneBook() {
        List<Book> actual = bookRepository.findAllByCategoryId(2L);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("Pet Sematary", actual.get(0).getTitle());
    }

    @Test
    @DisplayName("Find books with no existing category, "
            + "expected result: empty list")
    @Sql(scripts = {
            "classpath:database/books/repository/before/"
                    + "before_findAllByCategoryId_bookWithNoExistingCategory_ReturnEmptyList.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:database/books/repository/after/"
                + "after_findAllByCategoryId_bookWithNoExistingCategory_ReturnEmptyList.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_bookWithNoExistingCategory_ReturnEmptyList() {
        List<Book> actual = bookRepository.findAllByCategoryId(90L);
        Assertions.assertEquals(0, actual.size());
    }
}
