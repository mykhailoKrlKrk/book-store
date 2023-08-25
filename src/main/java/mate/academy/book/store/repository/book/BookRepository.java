package mate.academy.book.store.repository.book;

import mate.academy.book.store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book> {
}
