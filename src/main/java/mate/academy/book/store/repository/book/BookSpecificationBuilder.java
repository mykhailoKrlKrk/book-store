package mate.academy.book.store.repository.book;

import lombok.AllArgsConstructor;
import mate.academy.book.store.dto.BookSearchParameters;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.SpecificationBuilder;
import mate.academy.book.store.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.descriptions() != null && searchParameters.descriptions().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("description")
                    .getSpecification(searchParameters.descriptions()));
        }
        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbns()));
        }
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.authors()));
        }
        return spec;
    }
}
