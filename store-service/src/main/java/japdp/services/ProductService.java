package japdp.services;

import japdp.models.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    public Product getById(Long id);
    public Product getByName(String name);
    public void add(Product product);
    public void update(Long id, Product product);
    public void delete(Long id);
}
