package mate.academy.book.store.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.book.store.dto.BookDto;
import mate.academy.book.store.model.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
