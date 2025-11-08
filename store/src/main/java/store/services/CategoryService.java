package store.services;

import store.models.Category;
import store.models.Product;

import java.util.List;

public interface CategoryService {
    public List<Category> getAll();
    public Category getById(Long id);
    public Category getByName(String name);
    public void add(Category category);
    public void update(Long id, Category category);
    public void delete(Long id);
}
