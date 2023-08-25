package mate.academy.book.store.repository;

import mate.academy.book.store.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {

    String getKey();
    Specification<Book> getSpecification(String[] params);
}
