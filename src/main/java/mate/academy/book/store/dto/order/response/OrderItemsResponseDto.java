package mate.academy.book.store.dto.order.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemsResponseDto {
    private Long id;
    private Long bookId;
    private Integer quantity;
}
