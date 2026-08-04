package co.istad.productapisimpledemo;


import co.istad.productapisimpledemo.dto.CategoryRequest;
import co.istad.productapisimpledemo.dto.CategoryResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.mapper.CategoryMapper;import co.istad.productapisimpledemo.repository.CategoryRepository;
import co.istad.productapisimpledemo.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryService;
    private CategoryMapper categoryMapper;
    @BeforeEach
    void setUp() {
        categoryMapper = Mappers.getMapper(CategoryMapper.class);
        categoryService = new CategoryServiceImpl(
                categoryMapper,
                categoryRepository
        );
    }
    @Test
    void shouldCreateCategorySuccessfully() {

        CategoryRequest request = new CategoryRequest(
                "Laptop", "just a standard category" , null , null
        );

        Category saved = new Category();
        saved.setId(1);
        saved.setName("Laptop");
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(saved);
        CategoryResponse response = categoryService.createCategory(request);
        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Laptop", response.name());
        verify(categoryRepository, times(1))
                .save(any(Category.class));
    }
    @Test
    void shouldFindCategoryById() {

        Category category = new Category();
        category.setId(5);
        category.setName("Phone");


        when(categoryRepository.findById(5))
                .thenReturn(Optional.of(category));

        CategoryResponse response =
                categoryService.findById(5);
        assertEquals(5, response.id());
        assertEquals("Phone", response.name());
        verify(categoryRepository).findById(5);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {

        when(categoryRepository.findById(100))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> categoryService.findById(100)
        );

        verify(categoryRepository)
                .findById(100);
    }

    @Test
    void shouldDeleteCategory() {

        when(categoryRepository.existsById(1))
                .thenReturn(true);

        doNothing()
                .when(categoryRepository)
                .deleteById(1);

        categoryService.deleteCategory(1);

        verify(categoryRepository).existsById(1);
        verify(categoryRepository).deleteById(1);
    }

    @Test
    void shouldReturnAllCategories() {

        Category phone = new Category();
        phone.setId(1);
        phone.setName("Phone");

        Category laptop = new Category();
        laptop.setId(2);
        laptop.setName("Laptop");

        List<Category> categories = List.of(phone, laptop);

        when(categoryRepository.findAll())
                .thenReturn(categories);

        List<CategoryResponse> result =
                categoryService.findAll();

        assertEquals(2, result.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldFindCategoryByName() {
        Category category = new Category();
        category.setId(1);
        category.setName("Laptop");
        when(categoryRepository.findByNameContainingIgnoreCase("lap"))
                .thenReturn(List.of(category));
        List<CategoryResponse> response =
                categoryService.findByName("lap");
        assertEquals(1, response.size());
        assertEquals("Laptop",
                response.get(0).name());
    }

}
