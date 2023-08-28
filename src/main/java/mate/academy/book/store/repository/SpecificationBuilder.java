package mate.academy.book.store.repository;

import mate.academy.book.store.dto.bookdto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters searchParameters);
}
