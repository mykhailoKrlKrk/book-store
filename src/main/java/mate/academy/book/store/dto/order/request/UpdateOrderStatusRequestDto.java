package mate.academy.book.store.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import mate.academy.book.store.model.Status;

@Data
public class UpdateOrderStatusRequestDto {
    @NotEmpty
    private Status status;
}
