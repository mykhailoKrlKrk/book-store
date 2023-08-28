package mate.academy.book.store.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;
import mate.academy.book.store.validation.Isbn;

@Data
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
}
