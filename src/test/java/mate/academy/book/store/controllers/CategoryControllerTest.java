package mate.academy.book.store.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mate.academy.book.store.dto.book.BookDto;
import mate.academy.book.store.dto.book.category.CategoryDto;
import mate.academy.book.store.dto.book.category.CategoryRequestDto;
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
public class CategoryControllerTest {
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/category/add-data/method-create-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/method-create-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create category - expected result: "
            + "create category in DB, return created category")
    public void createCategory_ValidRequestDto_Success() throws Exception {
        //Given
        CategoryRequestDto requestDto = new CategoryRequestDto()
                .setName("comedy")
                .setDescription("very funny book");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        CategoryDto expected = new CategoryDto()
                .setId(3L)
                .setName("comedy")
                .setDescription("very funny book");
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/category/add-data/getAll-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/getAll-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get list of existing in DB categories - expected result: "
            + "return list of categories")
    public void getAllCategories_ReturnListOfExistingCategories() throws Exception {
        //When
        MvcResult result = mockMvc.perform(
                        get("/categories"))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Then
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/category/add-data/findById-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/findById-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find category by existing in DB id - expected result: return category")
    public void findById_ValidId_ReturnCategory() throws Exception {
        //Given
        Long expectedId = 1L;
        //When
        MvcResult result = mockMvc.perform(
                        get("/categories/{categoryId}", expectedId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), CategoryDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/category/add-data/findById-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/findById-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find category by not existing in DB id - expected result: "
            + "return exception - NOT FOUND")
    public void findById_InValidId_GetError() throws Exception {
        //Given
        Long categoryId = 100L;
        //Then
        mockMvc.perform(get("/category/{categoryId}", categoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/category/add-data/update-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/update-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update category by id - expected result: return updated category")
    public void updateCategory_validId_Success() throws Exception {
        //Given
        Long bookId = 1L;
        CategoryRequestDto updateCategoryRequest = new CategoryRequestDto()
                .setName("Updated category");
        CategoryDto expected = new CategoryDto()
                .setId(1L)
                .setName("Updated category");
        String request = objectMapper.writeValueAsString(updateCategoryRequest);
        //When
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.put("/categories/{id}", bookId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        //Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/category/add-data/update-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/update-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update category by not existing id - expected result: "
            + "return exception - NOT FOUND")
    public void updateById_InValidId_ReturnError() throws Exception {
        //Given
        Long categoryId = 101L;
        CategoryRequestDto updatedBook = new CategoryRequestDto()
                .setName("new Category")
                .setDescription("Updated description");
        //Then
        String request = objectMapper.writeValueAsString(updatedBook);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/categories/{id}", categoryId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/category/add-data/delete-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/delete-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete category by id - expected result: delete category from DB")
    public void deleteCategory_Success() throws Exception {
        mockMvc.perform(
                        delete("/categories/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/category/add-data/delete-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/delete-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete category by not existing in DB id - expected result: "
            + "return exception - NOT FOUND")
    public void deleteCategory_InvalidId_GetError() throws Exception {
        mockMvc.perform(
                        delete("/categories/{id}", 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/category/add-data/getBooksByCategoryId-method-add.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/category/delete-from-db/getBooksByCategoryId-method-delete.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get list of books by category id - expected result: "
            + "return list of books")
    public void getBooksByCategoryId_Success() throws Exception {
        Long categoryId = 1L;
        //When
        MvcResult result = mockMvc.perform(
                        get("/categories/{categoryId}/books", categoryId))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Then
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }
}
