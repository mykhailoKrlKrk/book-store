package mate.academy.book.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.book.store.dto.user.request.UserLoginRequestDto;
import mate.academy.book.store.dto.user.request.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.response.UserResponseDto;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIntegrationTest {
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
    @DisplayName("Login like user - expected result: return token")
    @Sql(scripts = {
            "classpath:database/AuthenticationControllerIntegrationTest/after/"
                    + "after_register_ValidRequest_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void register_ValidRequest_Success() throws Exception {
        //Given
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto()
                .setEmail("john.doe@example.com")
                .setFirstName("John")
                .setLastName("Doe")
                .setShippingAddress("123 Main St, City, Country")
                .setPassword("securePassword123")
                .setRepeatPassword("securePassword123");
        String request = objectMapper.writeValueAsString(requestDto);
        //When
        UserResponseDto expected = new UserResponseDto()
                .setId(4L)
                .setEmail("john.doe@example.com")
                .setFirstName("John")
                .setLastName("Doe")
                .setShippingAddress("123 Main St, City, Country");
        MvcResult result = mockMvc.perform(
                        post("/auth/register")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        UserResponseDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), UserResponseDto.class);
        //Then
        assertNotNull(result);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Login like user with invalid email - "
            + "expected result: return error - BAD REQUEST")
    public void register_InValidMail_GetError() throws Exception {
        //Given
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto()
                .setEmail("john...@example.com")
                .setFirstName("John")
                .setLastName("Doe")
                .setShippingAddress("123 Main St, City, Country")
                .setPassword("securePassword123")
                .setRepeatPassword("securePassword123");
        String request = objectMapper.writeValueAsString(requestDto);
        //Then
        mockMvc.perform(
                        post("/auth/register")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/AuthenticationControllerIntegrationTest/before/"
                    + "before_login_ValidCredentials_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/AuthenticationControllerIntegrationTest/after/"
                    + "after_login_ValidCredentials_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Login like user - expected result: successfully login")
    public void login_ValidCredentials_Success() throws Exception {
        //Given
        UserLoginRequestDto requestDto = new UserLoginRequestDto()
                .setEmail("john.doe@example.com")
                .setPassword("securePassword123");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //Then
        mockMvc.perform(
                        post("/auth/login")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser
    @DisplayName("Login like user with invalid password - "
            + "expected result: return error - BAD REQUEST")
    public void login_InCorrectPassword_GetError() throws Exception {
        //Given
        UserLoginRequestDto requestDto = new UserLoginRequestDto()
                .setEmail("john.doe@example.com")
                .setPassword("securePassword");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //Then
        mockMvc.perform(
                        post("/auth/login")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
