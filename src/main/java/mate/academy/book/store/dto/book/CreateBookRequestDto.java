package mate.academy.book.store.dto.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import mate.academy.book.store.validation.Isbn;

@Data
@Accessors(chain = true)
public class CreateBookRequestDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    @Isbn
    private String isbn;
    @PositiveOrZero
    private BigDecimal price;
    @NotEmpty
    private String description;
    @NotEmpty
    private String coverImage;
    @NotEmpty
    private List<Long> categoriesIds;
}
