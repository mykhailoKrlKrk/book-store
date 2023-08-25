package mate.academy.book.store.repository;

import java.util.List;
import mate.academy.book.store.model.Book;

public interface BookRepository {
    Book save(Book book);

    List findAll();
}
