package mate.academy.book.store.service.book;

import java.util.List;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.BookSearchParameters;
import mate.academy.book.store.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters, Pageable pageable);

    List<BookDto> getBooksByCategoryId(Long id);
}
