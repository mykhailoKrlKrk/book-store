package mate.academy.book.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import mate.academy.book.store.dto.order.request.CreateOrderRequestDto;
import mate.academy.book.store.dto.order.request.UpdateOrderStatusRequestDto;
import mate.academy.book.store.dto.order.response.OrderItemsResponseDto;
import mate.academy.book.store.dto.order.response.OrderResponseDto;
import mate.academy.book.store.model.Status;
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
public class OrderControllerIntegrationTest {
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
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_createOrder_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_createOrder_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create order - expected result: "
            + "create order in the DB")
    public void createOrder_Success() throws Exception {
        //Given
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto()
                .setShippingAddress("new address");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //When

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        OrderResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), OrderResponseDto.class);
        OrderResponseDto expected = new OrderResponseDto()
                .setId(3L)
                .setUserId(1L)
                .setStatus(Status.PENDING)
                .setTotal(new BigDecimal("220.00"))
                .setOrderDate(actual.getOrderDate())
                .setOrderItems(Set.of(getOrderItem().setId(3L)));
        //Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_getUserOrders_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_getUserOrders_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get user orders - expected result: "
            + "return list of all user orders")
    public void getUserOrders_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/orders")
                )
                .andExpect(status().isOk())
                .andReturn();
        List<OrderResponseDto> actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), new TypeReference<>() {
                });
        assertEquals(2, actual.size());
    }

    @Test
    @WithMockUser(username = "bob@mail.com", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_updateOrderStatus_ValidId_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_updateOrderStatus_ValidId_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update order status - expected result: "
            + "return order with changed status")
    public void updateOrderStatus_ValidId_Success() throws Exception {
        Long orderId = 1L;
        UpdateOrderStatusRequestDto requestDto = new UpdateOrderStatusRequestDto()
                .setStatus(Status.DELIVERED);
        OrderResponseDto expected = new OrderResponseDto()
                .setId(1L)
                .setUserId(1L)
                .setStatus(Status.DELIVERED)
                .setOrderDate(LocalDateTime.parse("2023-10-01T11:35:50",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .setTotal(new BigDecimal("220.00"))
                .setOrderItems(Set.of(getOrderItem().setId(2L)));

        String request = objectMapper.writeValueAsString(requestDto);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.patch("/orders/{id}", orderId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        OrderResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), OrderResponseDto.class);

        assertNotNull(actual);
        assertEquals(Status.DELIVERED, actual.getStatus());
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "bob@mail.com", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_updateOrderStatus_ValidId_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_updateOrderStatus_ValidId_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update order status using invalid id - expected result: "
            + "return error - NOT FOUND")
    public void updateOrderStatus_InValidIdGetError() throws Exception {
        Long orderId = 101L;
        UpdateOrderStatusRequestDto requestDto = new UpdateOrderStatusRequestDto()
                .setStatus(Status.DELIVERED);
        String request = objectMapper.writeValueAsString(requestDto);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.patch("/orders/{id}", orderId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_getOrderItems_ValidId_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_getOrderItems_ValidId_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get order items - expected result: "
            + "return list of all items in order")
    public void getOrderItems_ValidId_Success() throws Exception {
        //Given
        Long orderId = 1L;
        //When
        MvcResult result = mockMvc.perform(
                        get("/orders/{orderId}/items", orderId))
                .andExpect(status().isOk())
                .andReturn();
        List<OrderItemsResponseDto> actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Then
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_getOrderItems_InValidId_GetError.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_getOrderItems_InValidId_GetError.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get order items using invalid id - expected result: "
            + "return error - NOT FOUND")
    public void getOrderItems_InValidId_GetError() throws Exception {
        //Given
        Long orderId = 100L;
        //Then
        mockMvc.perform(
                        get("/orders/{orderId}/items", orderId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_getOrderItem_ValidData_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_getOrderItem_ValidData_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get order item using  - expected result: "
            + "return item by id")
    public void getOrderItem_ValidData_Success() throws Exception {
        //Given
        Long orderId = 1L;
        Long itemId = 1L;
        OrderItemsResponseDto expected = new OrderItemsResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setQuantity(1);
        //When
        MvcResult result = mockMvc.perform(
                        get("/orders/{orderId}/items/{itemId}", orderId, itemId))
                .andExpect(status().isOk())
                .andReturn();
        OrderItemsResponseDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), OrderItemsResponseDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "bob@mail.com")
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/before/"
                    + "before_getOrderItem_InValidData_GetError.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderControllerIntegrationTest/after/"
                    + "after_getOrderItem_InValidData_GetError.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get order item using invalid id - expected result: "
            + "return error - NOT FOUND")
    public void getOrderItem_InValidData_GetError() throws Exception {
        //Given
        Long orderId = 1L;
        Long itemId = 101L;
        //Then
        mockMvc.perform(
                        get("/orders/{orderId}/items/{itemId}", orderId, itemId))
                .andExpect(status().isNotFound());
    }

    private OrderItemsResponseDto getOrderItem() {
        return new OrderItemsResponseDto()
                .setBookId(1L)
                .setQuantity(10);
    }
}
