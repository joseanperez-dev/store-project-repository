package store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.dto.*;
import store.exceptions.CategoryAlreadyExistsException;
import store.exceptions.CategoryNotFoundException;
import store.models.Category;
import store.models.GenericData;
import store.models.Product;
import store.services.CategoryService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = categoryService.getAll();
        List<CategoryResponseDTO> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            String[] dates = formatDate(category);
            CategoryResponseDTO categoryDto = new CategoryResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    dates[0],
                    dates[1]
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
            String[] datesCategory = formatDate(category);
            List<ProductResponseDTO> productDtos = new ArrayList<>();
            for (Product product : category.getProducts()) {
                String[] datesProduct = formatDate(product);
                ProductResponseDTO productDto = new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        category.getName(),
                        product.getPrice(),
                        product.getStock(),
                        datesProduct[0],
                        datesProduct[1]
                );
                productDtos.add(productDto);
            }
            CategoryDetailsResponseDTO categoryDto = new CategoryDetailsResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    datesCategory[0],
                    datesCategory[1],
                    productDtos
            );
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Category category = categoryService.getById(id);
            logger.info("{}", category.getUpdateDate());
            String[] dates = formatDate(category);
            CategoryResponseDTO categoryDto = new CategoryResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    dates[0],
                    dates[1]
            );
            return ResponseEntity.ok(categoryDto);
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryNotFoundException.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getByIdDetails(@PathVariable Long id) {
        try {
            Category category = categoryService.getById(id);
            String[] datesCategory = formatDate(category);
            List<ProductResponseDTO> productDtos = new ArrayList<>();
            for (Product product : category.getProducts()) {
                String[] datesProduct = formatDate(product);
                ProductResponseDTO productDto = new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        category.getName(),
                        product.getPrice(),
                        product.getStock(),
                        datesProduct[0],
                        datesProduct[1]
                );
                productDtos.add(productDto);
            }
            CategoryDetailsResponseDTO categoryDto = new CategoryDetailsResponseDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    datesCategory[0],
                    datesCategory[1],
                    productDtos
            );
            return ResponseEntity.ok(categoryDto);
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryNotFoundException.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getName(), categoryDto.getDescription(), LocalDateTime.now());
        try {
            categoryService.add(category);
            return ResponseEntity.ok("La categoría " + category.getName() + " se ha creado correctamente.");
        }
        catch (CategoryAlreadyExistsException categoryAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(categoryAlreadyExistsException.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryDto) {
        try {
            Category categoryToUpdate = categoryService.getById(id);
            Category category = new Category(
                    categoryDto.getName(),
                    categoryDto.getDescription(),
                    categoryToUpdate.getCreationDate(),
                    LocalDateTime.now()
            );
            categoryService.update(id, category);
            return ResponseEntity.ok(categoryDto);
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryNotFoundException.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok("La categoría se ha eliminado correctamente.");
        }
        catch (CategoryNotFoundException categoryNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryNotFoundException.getMessage());
        }
    }

    public String[] formatDate(GenericData genericData) {
        String[] dates = new String[2];
        LocalDateTime creationDate = genericData.getCreationDate();
        LocalDateTime updateDate = genericData.getUpdateDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if (creationDate == null) {
            dates[0] = "";
        }
        else {
            dates[0] = creationDate.format(formatter);
        }
        if (updateDate == null) {
            dates[1] = "";
        }
        else {
            dates[1] = updateDate.format(formatter);
        }
        return dates;
    }
}
