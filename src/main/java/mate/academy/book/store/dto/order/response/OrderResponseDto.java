package mate.academy.book.store.dto.order.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import mate.academy.book.store.model.Status;

@Data
@Accessors(chain = true)
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemsResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;
}
