package mate.academy.book.store.service;

import java.util.List;
import mate.academy.book.store.dto.bookdto.BookDto;
import mate.academy.book.store.dto.bookdto.BookSearchParameters;
import mate.academy.book.store.dto.bookdto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    public List<BookDto> search(BookSearchParameters searchParameters);
}
