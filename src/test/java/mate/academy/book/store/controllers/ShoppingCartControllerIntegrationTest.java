package mate.academy.book.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.dto.shoppingcart.request.UpdateBookQuantityDto;
import mate.academy.book.store.dto.shoppingcart.response.CartItemResponseDto;
import mate.academy.book.store.dto.shoppingcart.response.ShoppingCartResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerIntegrationTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_getUserCart_ReturnUserCart.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_getUserCart_ReturnUserCart.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get user cart - expected result: "
            + "return user cart with all items")
    public void getUserCart_ReturnUserCart() throws Exception {
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/cart")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ShoppingCartResponseDto expected = getCartResponse();
        ShoppingCartResponseDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), ShoppingCartResponseDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_addBookToShoppingCart_ValidData_AddNewBookToCart.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_addBookToShoppingCart_ValidData_AddNewBookToCart.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Add book to user cart - expected result: "
            + "return user cart with new book")
    public void addBookToShoppingCart_ValidData_AddNewBookToCart() throws Exception {
        //Given
        CreateBookItemDto requestDto = new CreateBookItemDto()
                .setBookId(2L)
                .setQuantity(12);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/cart")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        ShoppingCartResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), ShoppingCartResponseDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(2, actual.getCartItems().size());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_addBookToShoppingCart_AddExistingBook_IncreaseBookQuantity.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_addBookToShoppingCart_AddExistingBook_IncreaseBookQuantity.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Add existing book to user cart - expected result: "
            + "increase a quantity of book in the cart")
    public void addBookToShoppingCart_AddExistingBook_IncreaseBookQuantity() throws Exception {
        //Given
        CreateBookItemDto requestDto = new CreateBookItemDto()
                .setBookId(1L)
                .setQuantity(15);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/cart")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        ShoppingCartResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), ShoppingCartResponseDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(1, actual.getCartItems().size());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_addBookToShoppingCart_AddExistingBook_IncreaseBookQuantity.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_addBookToShoppingCart_AddExistingBook_IncreaseBookQuantity.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Add book with invalid id to user cart - expected result: "
            + "get error - NOT FOUND")
    public void addBookToShoppingCart_InvalidBookId_GetError() throws Exception {
        //Given
        CreateBookItemDto requestDto = new CreateBookItemDto()
                .setBookId(100L)
                .setQuantity(15);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //When
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/cart")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_updateShoppingCart_ValidData_ReturnUpdatedCart.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_updateShoppingCart_ValidData_ReturnUpdatedCart.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update shopping cart - expected result: "
            + "update cart item quantity by id")
    public void updateShoppingCart_ValidData_ReturnUpdatedCart() throws Exception {
        //Given
        Long cartItemId = 1L;
        UpdateBookQuantityDto quantityDto = new UpdateBookQuantityDto()
                .setQuantity(10);
        String jsonRequest = objectMapper.writeValueAsString(quantityDto);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.put("/cart/cart-items/{cartItemId}", cartItemId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        ShoppingCartResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), ShoppingCartResponseDto.class);
        ShoppingCartResponseDto expected = getCartResponse();
        //Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_updateShoppingCart_InvalidCartItemId_GetError.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_updateShoppingCart_InvalidCartItemId_GetError.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update shopping cart by invalid cart item id - expected result: "
            + "get error - NOT FOUND")
    public void updateShoppingCart_InvalidCartItemId_GetError() throws Exception {
        //Given
        Long cartItemId = 100L;
        UpdateBookQuantityDto quantityDto = new UpdateBookQuantityDto()
                .setQuantity(10);
        String jsonRequest = objectMapper.writeValueAsString(quantityDto);
        //Then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/cart/cart-items/{cartItemId}", cartItemId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_updateShoppingCart_NegativeQuantity_GetError.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_updateShoppingCart_NegativeQuantity_GetError.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update shopping cart using negative quantity - expected result: "
            + "get error - BAD RESULT")
    public void updateShoppingCart_NegativeQuantity_GetError() throws Exception {
        //Given
        Long cartItemId = 1L;
        UpdateBookQuantityDto quantityDto = new UpdateBookQuantityDto()
                .setQuantity(-5);
        //Then
        String jsonRequest = objectMapper.writeValueAsString(quantityDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/cart/cart-items/{cartItemId}", cartItemId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_deleteBookFromCart_ValidId_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_deleteBookFromCart_ValidId_Success.sql.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete book from cart - expected result: "
            + "cart item is successfully deleted from cart")
    public void deleteBookFromCart_ValidId_Success() throws Exception {
        //Given
        Long cartItemId = 1L;
        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/cart/cart-items/{cartItemId}", cartItemId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/before/"
                    + "before_deleteBookFromCart_InValidId_GetError.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartControllerIntegrationTest/after/"
                    + "after_deleteBookFromCart_InValidId_GetError.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete book from cart by invalid id - expected result: "
            + "get error - NOT FOUND")
    public void deleteBookFromCart_InValidId_GetError() throws Exception {
        //Given
        Long cartItemId = 100L;
        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/cart/cart-items/{cartItemId}", cartItemId))
                .andExpect(status().isNotFound());
    }

    private ShoppingCartResponseDto getCartResponse() {
        return new ShoppingCartResponseDto()
                .setId(1L)
                .setUserId(1L)
                .setCartItems(getCartItems());
    }

    private Set<CartItemResponseDto> getCartItems() {
        CartItemResponseDto bookOne = new CartItemResponseDto();
        bookOne.setId(1L);
        bookOne.setQuantity(10);
        bookOne.setBookId(1L);
        bookOne.setBookTitle("The Dark Half");

        CartItemResponseDto bookTwo = new CartItemResponseDto();
        bookTwo.setId(2L);
        bookTwo.setQuantity(20);
        bookTwo.setBookId(2L);
        bookTwo.setBookTitle("Pet Sematary");

        Set<CartItemResponseDto> items = new HashSet<>();
        items.add(bookOne);
        items.add(bookTwo);
        return items;
    }
}
