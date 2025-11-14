package japdp.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends GenericData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    public Category() {
        super();
    }

    public Category(Long id, String name, String description, LocalDateTime creationDate) {
        super(name, description, creationDate);
        this.id = id;
    }

    public Category(String name, String description, LocalDateTime creationDate) {
        super(name, description, creationDate);
    }

    public Category(String name, String description, LocalDateTime creationDate, LocalDateTime updateDate) {
        super(name, description, creationDate, updateDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
