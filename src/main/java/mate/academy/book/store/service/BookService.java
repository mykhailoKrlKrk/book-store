package mate.academy.book.store.service;

import java.util.List;
import mate.academy.book.store.dto.BookDto;
import mate.academy.book.store.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

}
