package mate.academy.book.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.CreateBookRequestDto;
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
public class BookControllerTest {
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
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get list of existing in DB books - expected result: return list of books")
    public void getAllBooks_ReturnListOfExistingBooks() throws Exception {
        //When
        MvcResult result = mockMvc.perform(
                        get("/books"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Then
        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find book by existing in DB id - expected result: return book")
    public void findById_ValidId_ReturnBook() throws Exception {
        //Given
        Long expectedId = 1L;
        //When
        MvcResult result = mockMvc.perform(
                        get("/books/{bookId}", expectedId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        BookDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), BookDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find book by not existing in DB id - expected result: "
            + "return exception - NOT FOUND")
    public void findById_InValidId_GetError() throws Exception {
        //Given
        Long bookId = 100L;
        //Then
        mockMvc.perform(get("/books/{bookId}", bookId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update book by id - expected result: return updated book")
    public void updateBook_validId_Success() throws Exception {
        //Given
        Long bookId = 1L;
        CreateBookRequestDto updatedBookRequest = createDefaultBook()
                .setTitle("Updated book");
        String request = objectMapper.writeValueAsString(updatedBookRequest);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/{id}", bookId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        BookDto expected = getResponseDto(createDefaultBook())
                .setId(1L)
                .setTitle("Updated book");
        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        actual.setCategoriesIds(List.of(1L));
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update book by not existing id - expected result: "
            + "return exception - NOT FOUND")
    public void updateById_InValidId_ReturnError() throws Exception {
        //Given
        Long bookId = 101L;
        CreateBookRequestDto updatedBook = createDefaultBook()
                .setAuthor("new Author")
                .setTitle("Updated book");
        //Then
        String request = objectMapper.writeValueAsString(updatedBook);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/{id}", bookId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create book - expected result: create book in DB, return created book")
    public void createBook_ValidRequestDto_Success() throws Exception {
        //Given
        CreateBookRequestDto requestDto = createDefaultBook();
        BookDto expected = getResponseDto(requestDto);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(actual, expected);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create book with invalid isbn - expected result: "
            + "return exception - BAD REQUEST")
    public void createBook_WithInvalidIsbn_GetError() throws Exception {
        //Given
        CreateBookRequestDto requestDto = createDefaultBook();
        requestDto.setIsbn("122-348");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //Then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete book by id - expected result: delete book from DB")
    public void deleteBook_Success() throws Exception {
        mockMvc.perform(
                        delete("/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete book by not existing in DB id - expected result: "
            + "return exception - NOT FOUND")
    public void deleteBook_InvalidId_GetError() throws Exception {
        mockMvc.perform(
                        delete("/books/{id}", 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Search books by params - search by author: "
            + "return list of author's books")
    public void searchByParams_SearchByAuthor_Success() throws Exception {
        //When
        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("authors", "Stephen King"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Then
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals("Stephen King", actual.get(0).getAuthor());
        assertEquals("Stephen King", actual.get(1).getAuthor());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/books/create-books-with-categories.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-all-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Search books by params - search by isbn: "
            + "return book with specified id")
    public void searchByParams_SearchByIsbn_Success() throws Exception {
        //When
        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("isbns", "112-3884-00000-2"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("Stephen King", actual.get(0).getAuthor());
    }

    private static CreateBookRequestDto createDefaultBook() {
        return new CreateBookRequestDto()
                .setDescription("Some details")
                .setTitle("title")
                .setCoverImage("image.jpg")
                .setIsbn("978-3-16-000909-0")
                .setPrice(BigDecimal.TEN)
                .setAuthor("Author")
                .setCategoriesIds(List.of(1L));
    }

    private static BookDto getResponseDto(CreateBookRequestDto requestDto) {
        return new BookDto()
                .setId(4L)
                .setDescription(requestDto.getDescription())
                .setTitle(requestDto.getTitle())
                .setCoverImage(requestDto.getCoverImage())
                .setIsbn(requestDto.getIsbn())
                .setPrice(requestDto.getPrice())
                .setAuthor(requestDto.getAuthor())
                .setCategoriesIds(requestDto.getCategoriesIds());
    }
}
