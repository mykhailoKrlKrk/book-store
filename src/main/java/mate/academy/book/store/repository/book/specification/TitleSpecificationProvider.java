package mate.academy.book.store.repository.book.specification;

import java.util.Arrays;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;

public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(String[] param) {
        return (root, query, criteriaBuilder) -> root.get("title").in(Arrays.stream(param).toArray());
    }
}
