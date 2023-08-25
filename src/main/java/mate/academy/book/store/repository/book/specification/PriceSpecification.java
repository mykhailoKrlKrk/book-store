package mate.academy.book.store.repository.book.specification;

import java.util.Arrays;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;

public class PriceSpecification implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "price";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("price")
                .in(Arrays.stream(params).toArray());
    }
}
