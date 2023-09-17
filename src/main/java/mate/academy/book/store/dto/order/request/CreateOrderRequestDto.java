package mate.academy.book.store.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderRequestDto {
    @NotEmpty
    private String shippingAddress;
}
