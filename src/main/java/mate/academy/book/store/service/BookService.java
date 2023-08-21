package mate.academy.book.store.service;

import java.util.List;
import mate.academy.book.store.model.Book;

public interface BookService {
    Book save(Book book);

    List findAll();
}
