package mate.academy.book.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.dto.shoppingcart.request.UpdateBookQuantityDto;
import mate.academy.book.store.dto.shoppingcart.response.ShoppingCartResponseDto;
import mate.academy.book.store.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    @Operation(summary = "Add book", description = "Add book to user shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added to shopping cart"),
            @ApiResponse(responseCode = "409",
                    description = "Duplicate - The book is already exist in the shopping cart!")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponseDto addBookToShoppingCart(@RequestBody
                                                         @Valid CreateBookItemDto request) {
        return shoppingCartService.addBookToUserCart(request);
    }

    @GetMapping
    public ShoppingCartResponseDto getUserCart() {
        return shoppingCartService.getUserCart();
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update book quantity",
            description = "Update book quantity in user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added"),
            @ApiResponse(responseCode = "404",
                    description = "Book with this id is not exist in shopping cart!")
    })
    public ShoppingCartResponseDto updateShoppingCart(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateBookQuantityDto requestDto) {
        return shoppingCartService.updateBookQuantity(cartItemId, requestDto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book", description = "Delete book by from user's shopping cart")
    public void deleteBookFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.removeBookFromCart(cartItemId);
    }
}
