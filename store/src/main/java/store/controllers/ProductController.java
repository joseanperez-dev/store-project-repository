package store.controllers;

import org.springframework.web.bind.annotation.*;
import store.dto.ProductRequestDTO;
import store.dto.ProductResponseDTO;
import store.models.Product;
import store.services.ProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
                    formattedDate
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
                formattedDate
        );
        return productDto;
    }

    @PostMapping
    public void add(@RequestBody ProductRequestDTO productDto) {
        Product product = new Product(productDto.getName(), productDto.getDescription(), LocalDateTime.now());
        productService.add(product);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDTO productDto) {
        Product product = new Product(productDto.getName(), productDto.getDescription(), LocalDateTime.now());
        productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
