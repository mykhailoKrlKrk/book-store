package mate.academy.book.store.dto.book.category;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryRequestDto {
    private String name;
    private String description;
}
