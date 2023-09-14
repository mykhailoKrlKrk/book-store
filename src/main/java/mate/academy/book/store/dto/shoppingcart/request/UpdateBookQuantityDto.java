package mate.academy.book.store.dto.shoppingcart.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateBookQuantityDto {
    @Positive
    private int quantity;
}
