package store.controllers;

import org.springframework.web.bind.annotation.*;
import store.dto.ProductRequestDTO;
import store.dto.ProductResponseDTO;
import store.models.Category;
import store.models.GenericData;
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
            String[] dates = formatDate(product);
            ProductResponseDTO productDto = new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getCategory().getName(),
                    product.getPrice(),
                    product.getStock(),
                    dates[0],
                    dates[1]
            );
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        String[] dates = formatDate(product);
        ProductResponseDTO productDto = new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getStock(),
                dates[0],
                dates[1]
        );
        return productDto;
    }

    @PostMapping
    public void add(@RequestBody ProductRequestDTO productDto) throws Exception {
        Category category = categoryService.getById(productDto.getCategoryId());
        if (category == null) {
            throw new Exception("La categoría no existe");
        }
        Product product = new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                LocalDateTime.now(),
                category
        );
        productService.add(product);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDTO productDto) {
        Category category = categoryService.getById(productDto.getCategoryId());
        Product productToUpdate = productService.getById(id);
        Product product = new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                productToUpdate.getCreationDate(),
                LocalDateTime.now(), category
        );
        productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    public String[] formatDate(GenericData genericData) {
        String[] dates = new String[2];
        LocalDateTime creationDate = genericData.getCreationDate();
        LocalDateTime updateDate = genericData.getUpdateDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dates[0] = creationDate.format(formatter);
        if (updateDate == null) {
            dates[1] = "";
        }
        else {
            dates[1] = updateDate.format(formatter);
        }
        return dates;
    }
}
