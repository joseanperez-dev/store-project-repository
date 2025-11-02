package store.services;

import org.springframework.stereotype.Service;
import store.models.Product;
import store.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    public ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        return null;
    }

    @Override
    public void add(Product product) {
        productRepo.save(product);
    }

    @Override
    public void update(Long id, Product product) {
        Product productToUpdate = getById(id);
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setUpdateDate(product.getUpdateDate());
        productRepo.save(productToUpdate);
    }

    @Override
    public void delete(Long id) {
        Product product = getById(id);
        productRepo.delete(product);
    }
}
