package mate.academy.book.store.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.BookSearchParameters;
import mate.academy.book.store.dto.book.CreateBookRequestDto;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.mapper.book.BookMapper;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.repository.book.BookRepository;
import mate.academy.book.store.repository.book.BookSpecificationBuilder;
import mate.academy.book.store.repository.book.CategoryRepository;
import mate.academy.book.store.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        List<Long> categoriesIds = requestDto.getCategoriesIds();
        categoryRepository.findAllById(categoriesIds).forEach(book::addCategory);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book bookById = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id:" + id));
        return bookMapper.toDto(bookById);
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        Specification<Book> bookSpecification = specificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookDto> getBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
