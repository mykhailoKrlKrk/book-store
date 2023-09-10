package mate.academy.book.store.mapper;

import java.util.stream.Collectors;
import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.book.store.dto.book.CreateBookRequestDto;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.model.Category;
import mate.academy.book.store.repository.book.BookRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoriesIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id, BookRepository bookRepository) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id" + id));
    }
}
