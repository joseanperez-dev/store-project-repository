package japdp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import japdp.exceptions.CategoryAlreadyExistsException;
import japdp.exceptions.CategoryNotFoundException;
import japdp.models.Category;
import japdp.models.Product;
import japdp.repositories.CategoryRepository;
import japdp.repositories.ProductRepository;
import japdp.services.CategoryServiceImpl;
import japdp.services.ProductServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository catRepo;
    @Mock
    private ProductRepository prodRepo;
    @InjectMocks
    private CategoryServiceImpl catService;
    @InjectMocks
    private ProductServiceImpl prodService;

    @Test
    void shouldReturnCategoryNotFoundException() {
        when(catRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> catService.getById(1L));
    }

    @Test
    void shouldReturnCategoryAlreadyExistsException() {
        Category category = new Category();
        category.setName("Libros");
        category.setDescription("Libros para leer");
        when(catRepo.findByName("Libros")).thenReturn(Optional.of(category));

        Category newCategory = new Category();
        newCategory.setName("Libros");

        assertThrows(CategoryAlreadyExistsException.class, () -> catService.add(newCategory));
    }

    @Test
    void getCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Libros");
        category.setDescription("Libros para leer");
        catService.add(category);
        when(catRepo.findById(1L)).thenReturn(Optional.of(category));
        Category existingCategory = catService.getById(1L);
        assertNotNull(existingCategory);
        assertEquals(1L, existingCategory.getId());
        assertEquals("Libros", existingCategory.getName());
    }

    @Test
    void getAllCategories() {
        Category cat1 = new Category(1L, "Libros", "Libros para leer", null);
        Category cat2 = new Category(2L, "Alimentacion", "Cosas para comer", null);
        catService.add(cat1);
        catService.add(cat2);
        when(catRepo.findAll()).thenReturn(List.of(cat1, cat2));
        List<Category> categories = catService.getAll();
        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals(cat1.getId(), categories.get(0).getId());
        assertEquals(cat2.getId(), categories.get(1).getId());
    }

    @Test
    void getCategoryWithProductsById() {
        Category category = new Category(1L, "Libros", "Libros para leer", null);
        catService.add(category);
        Product prod1 = new Product(1L, "Libro 1", "Es un libro", 1.24D, 20, null, category);
        Product prod2 = new Product(2L, "Libro 2", "Es un libro", 25.4D, 10, null, category);
        prodService.add(prod1);
        prodService.add(prod2);
        category.getProducts().add(prod1);
        category.getProducts().add(prod2);
        when(catRepo.findById(1L)).thenReturn(Optional.of(category));
        Category existingCategory = catService.getById(1L);
        assertNotNull(existingCategory);
        assertEquals(1L, existingCategory.getId());
        assertEquals("Libros", existingCategory.getName());
        List<Product> products = category.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(prod1.getId(), products.get(0).getId());
        assertEquals(prod2.getId(), products.get(1).getId());
    }
}
