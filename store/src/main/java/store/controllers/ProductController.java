package store.controllers;

import org.springframework.web.bind.annotation.*;
import store.dto.ProductRequestDTO;
import store.dto.ProductResponseDTO;
import store.models.Category;
import store.models.Product;
import store.services.CategoryService;
import store.services.ProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        List<Product> products = productService.getAll();
        List<ProductResponseDTO> productDtos = new ArrayList<>();
        for (Product product : products) {
            LocalDateTime date = product.getCreationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = date.format(formatter);
            ProductResponseDTO productDto = new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    formattedDate,
                    product.getCategory().getName()
            );
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        LocalDateTime date = product.getCreationDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = date.format(formatter);
        ProductResponseDTO productDto = new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                formattedDate,
                product.getCategory().getName()
        );
        return productDto;
    }

    @PostMapping
    public void add(@RequestBody ProductRequestDTO productDto) {
        Category category = categoryService.getById(productDto.getCategoryId());
        Product product = new Product(productDto.getName(), productDto.getDescription(), LocalDateTime.now(), category);
        productService.add(product);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDTO productDto) {
        Category category = categoryService.getById(productDto.getCategoryId());
        Product product = new Product(productDto.getName(), productDto.getDescription(), LocalDateTime.now(), category);
        productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
