package store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import store.dto.*;
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
    public CategoryResponseDTO getById(@PathVariable Long id) {
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
        return categoryDto;
    }

    @GetMapping("/details/{id}")
    public CategoryDetailsResponseDTO getByIdDetails(@PathVariable Long id) {
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
        return categoryDto;
    }

    @PostMapping
    public void add(@RequestBody CategoryRequestDTO categoryDto) {
        Category category = new Category(categoryDto.getName(), categoryDto.getDescription(), LocalDateTime.now());
        categoryService.add(category);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryDto) {
        Category categoryToUpdate = categoryService.getById(id);
        Category category = new Category(
                categoryDto.getName(),
                categoryDto.getDescription(),
                categoryToUpdate.getCreationDate(),
                LocalDateTime.now()
        );
        categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
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
