package store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.models.Category;
import store.models.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
