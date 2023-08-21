package mate.academy.book_store.service;

import java.util.List;
import mate.academy.book_store.model.Book;

public interface BookService {
    Book save(Book book);
    List findAll();
}
