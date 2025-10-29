package store.services;

import store.models.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    public Product getById(Long id);
    public void add(Product product);
    public void update(Long id, Product product);
    public void delete(Long id);
}
