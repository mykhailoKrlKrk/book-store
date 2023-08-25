package mate.academy.book.store.repository.book;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.SpecificationProvider;
import mate.academy.book.store.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private List<SpecificationProvider<Book>> bookSpecificationProviders;
    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException
                                ("Can't find correct specification for key "+ key));
    }
}
