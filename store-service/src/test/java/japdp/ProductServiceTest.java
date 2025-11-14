package japdp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import japdp.exceptions.ProductAlreadyExistsException;
import japdp.exceptions.ProductNotFoundException;
import japdp.models.Product;
import japdp.repositories.ProductRepository;
import japdp.services.ProductServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository prodRepo;
    @InjectMocks
    private ProductServiceImpl prodService;

    @Test
    void shouldReturnProductNotFoundException() {
        when(prodRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> prodService.getById(1L));
    }

    @Test
    void shouldReturnProductAlreadyExistsException() {
        Product product = new Product();
        product.setName("Libro");
        product.setDescription("Es un libro");
        product.setPrice(1.24D);
        product.setStock(20);
        when(prodRepo.findByName("Libro")).thenReturn(Optional.of(product));

        Product newProduct = new Product();
        newProduct.setName("Libro");

        assertThrows(ProductAlreadyExistsException.class, () -> prodService.add(newProduct));
    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Libro");
        product.setDescription("Es un libro");
        product.setPrice(1.24D);
        product.setStock(20);
        prodService.add(product);
        when(prodRepo.findById(1L)).thenReturn(Optional.of(product));
        Product existingProduct = prodService.getById(1L);
        assertNotNull(existingProduct);
        assertEquals(1L, existingProduct.getId());
        assertEquals("Libro", existingProduct.getName());
    }

    @Test
    void getAllProducts() {
        Product prod1 = new Product(1L, "Libro 1", "Es un libro", 1.24D, 20, null, null);
        Product prod2 = new Product(2L, "Libro 2", "Es un libro", 25.4D, 10, null, null);
        prodService.add(prod1);
        prodService.add(prod2);
        when(prodRepo.findAll()).thenReturn(List.of(prod1, prod2));
        List<Product> products = prodService.getAll();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(prod1.getId(), products.get(0).getId());
        assertEquals(prod2.getId(), products.get(1).getId());
    }
}
