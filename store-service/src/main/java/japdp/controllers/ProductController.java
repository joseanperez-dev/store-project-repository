package japdp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import japdp.dto.ProductRequestDTO;
import japdp.dto.ProductResponseDTO;
import japdp.exceptions.CategoryNotFoundException;
import japdp.exceptions.ProductAlreadyExistsException;
import japdp.exceptions.ProductNotFoundException;
import japdp.models.Category;
import japdp.models.GenericData;
import japdp.models.Product;
import japdp.services.CategoryService;
import japdp.services.ProductService;

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
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
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
            return ResponseEntity.ok(productDto);
        }
        catch (ProductNotFoundException productNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productNotFoundException.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody ProductRequestDTO productDto) throws Exception {
        try {
            Category category = categoryService.getById(productDto.getCategoryId());
            Product product = new Product(
                    productDto.getName(),
                    productDto.getDescription(),
                    productDto.getPrice(),
                    productDto.getStock(),
                    LocalDateTime.now(),
                    category
            );
            try {
                productService.add(product);
                return ResponseEntity.ok("El producto " + product.getName() + " se ha creado correctamente.");
            }
            catch (ProductAlreadyExistsException productAlreadyExistsException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productAlreadyExistsException.getMessage());
            }
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryNotFoundException.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ProductRequestDTO productDto) {
        Category category = categoryService.getById(productDto.getCategoryId());
        try {
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
            return ResponseEntity.ok(productDto);
        }
        catch (ProductNotFoundException productNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productNotFoundException.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok("El producto se ha eliminado correctamente.");
        }
        catch (ProductNotFoundException productNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productNotFoundException.getMessage());
        }
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
