package mate.academy.book.store.service.book;

import java.util.List;
import mate.academy.book.store.dto.book.category.CategoryDto;
import mate.academy.book.store.dto.book.category.CategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto requestDto);

    CategoryDto update(Long id, CategoryRequestDto requestDto);

    void deleteById(Long id);

}
