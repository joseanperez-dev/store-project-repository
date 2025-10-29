package store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
