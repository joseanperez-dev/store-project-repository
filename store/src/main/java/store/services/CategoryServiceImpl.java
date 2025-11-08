package store.services;

import org.springframework.stereotype.Service;
import store.exceptions.CategoryAlreadyExistsException;
import store.exceptions.CategoryNotFoundException;
import store.models.Category;
import store.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    public CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getById(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new CategoryNotFoundException("No se ha podido encontrar la categoría con Id: " + id);
    }

    @Override
    public Category getByName(String name) {
        Optional<Category> optionalCategory = categoryRepo.findByName(name);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new CategoryNotFoundException("No se ha podido encontrar la categoría con nombre: " + name);
    }

    @Override
    public void add(Category category) {
        if (categoryRepo.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException("La categoría " + category.getName() + " ya existe.");
        }
        categoryRepo.save(category);
    }

    @Override
    public void update(Long id, Category category) {
        Category categoryToUpdate = getById(id);
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        categoryToUpdate.setUpdateDate(category.getUpdateDate());
        categoryRepo.save(categoryToUpdate);
    }

    @Override
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepo.delete(category);
    }
}
