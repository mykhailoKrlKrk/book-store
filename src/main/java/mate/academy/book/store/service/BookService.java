package mate.academy.book.store.service;

import java.util.List;
import mate.academy.book.store.dto.BookDto;
import mate.academy.book.store.dto.BookSearchParameters;
import mate.academy.book.store.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    public List<BookDto> search(BookSearchParameters searchParameters);
}
