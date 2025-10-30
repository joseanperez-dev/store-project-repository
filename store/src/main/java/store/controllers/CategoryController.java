package store.controllers;

import org.springframework.web.bind.annotation.*;
import store.dto.*;
import store.models.Category;
import store.models.Product;
import store.services.CategoryService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = categoryService.getAll();
        List<CategoryResponseDTO> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            LocalDateTime date = category.getCreationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = date.format(formatter);
            CategoryResponseDTO categoryDto = new CategoryResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    formattedDate
            );
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @GetMapping("/details")
    public List<CategoryDetailsResponseDTO> getAllDetails() {
        List<Category> categories = categoryService.getAll();
        List<CategoryDetailsResponseDTO> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            LocalDateTime date = category.getCreationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = date.format(formatter);
            List<ProductResponseDTO> productDtos = new ArrayList<>();
            for (Product product : category.getProducts()) {
                LocalDateTime productDate = product.getCreationDate();
                String formattedProductDate = productDate.format(formatter);
                ProductResponseDTO productDto = new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        formattedProductDate,
                        category.getName()
                );
                productDtos.add(productDto);
            }
            CategoryDetailsResponseDTO categoryDto = new CategoryDetailsResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    formattedDate,
                    productDtos
            );
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        LocalDateTime date = category.getCreationDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = date.format(formatter);
        CategoryResponseDTO categoryDto = new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                formattedDate
        );
        return categoryDto;
    }

    @GetMapping("/details/{id}")
    public CategoryDetailsResponseDTO getByIdDetails(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        LocalDateTime date = category.getCreationDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = date.format(formatter);
        List<ProductResponseDTO> productDtos = new ArrayList<>();
        for (Product product : category.getProducts()) {
            LocalDateTime productDate = product.getCreationDate();
            String formattedProductDate = productDate.format(formatter);
            ProductResponseDTO productDto = new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    formattedProductDate,
                    category.getName()
            );
            productDtos.add(productDto);
        }
        CategoryDetailsResponseDTO categoryDto = new CategoryDetailsResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                formattedDate,
                productDtos
        );
        return categoryDto;
    }

    @PostMapping
    public void add(@RequestBody CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getName(), categoryDto.getDescription(), LocalDateTime.now());
        categoryService.add(category);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getName(), categoryDto.getDescription(), LocalDateTime.now());
        categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
