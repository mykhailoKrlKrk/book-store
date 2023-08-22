package mate.academy.book.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.BookDto;
import mate.academy.book.store.dto.CreateBookRequestDto;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.mapper.BookMapper;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book bookById = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id:" + id));
        return bookMapper.toDto(bookById);
    }
}
