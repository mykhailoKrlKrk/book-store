package mate.academy.book.store.mapper;

import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.CreateBookRequestDto;
import mate.academy.book.store.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
