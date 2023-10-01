package mate.academy.book.store.dto.shoppingcart.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateBookQuantityDto {
    @Positive
    private int quantity;
}
