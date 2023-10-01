package mate.academy.book.store.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;
import mate.academy.book.store.model.Status;

@Data
@Accessors(chain = true)
public class UpdateOrderStatusRequestDto {
    @NotEmpty
    private Status status;
}
