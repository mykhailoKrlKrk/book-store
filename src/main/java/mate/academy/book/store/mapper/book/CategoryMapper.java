package mate.academy.book.store.mapper.book;

import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.book.category.CategoryDto;
import mate.academy.book.store.dto.book.category.CategoryRequestDto;
import mate.academy.book.store.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryDto);
}
