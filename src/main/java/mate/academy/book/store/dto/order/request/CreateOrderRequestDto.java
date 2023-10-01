package mate.academy.book.store.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateOrderRequestDto {
    @NotEmpty
    private String shippingAddress;
}
