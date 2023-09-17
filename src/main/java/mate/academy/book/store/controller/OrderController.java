package mate.academy.book.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.order.request.CreateOrderRequestDto;
import mate.academy.book.store.dto.order.request.UpdateOrderStatusRequestDto;
import mate.academy.book.store.dto.order.response.OrderItemsResponseDto;
import mate.academy.book.store.dto.order.response.OrderResponseDto;
import mate.academy.book.store.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints to manage user orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order", description = "Add new user order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @GetMapping
    @Operation(summary = "Get user orders",
            description = "Get all user orders that was created from shopping cart")
    public List<OrderResponseDto> getUserOrders() {
        return orderService.getUserOrders();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added"),
            @ApiResponse(responseCode = "404",
                    description = "Not found - order with this id is not exist!")
    })
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get order items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get"),
            @ApiResponse(responseCode = "404",
                    description = "Not found - order with this id is not exist!")
    })
    public List<OrderItemsResponseDto> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get order item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added"),
            @ApiResponse(responseCode = "404",
                    description = "Not found - order or order item with this id is not exist!")
    })
    public OrderItemsResponseDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        return orderService.getOrderItem(orderId, itemId);
    }
}
