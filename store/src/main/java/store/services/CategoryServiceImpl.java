package store.services;

import org.springframework.stereotype.Service;
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
        return null;
    }

    @Override
    public void add(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public void update(Long id, Category category) {
        Category categoryToUpdate = getById(id);
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        categoryRepo.save(categoryToUpdate);
    }

    @Override
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepo.delete(category);
    }
}
