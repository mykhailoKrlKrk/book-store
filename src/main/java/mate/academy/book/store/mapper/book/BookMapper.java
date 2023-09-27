package mate.academy.book.store.mapper.book;

import java.util.stream.Collectors;
import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.CreateBookRequestDto;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoriesIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList()));
    }
}
