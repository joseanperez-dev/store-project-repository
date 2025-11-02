package store.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Product extends GenericData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category")
    Category category;

    public Product() {
    }

    public Product(Long id, String name, String description, LocalDateTime creationDate, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.category = category;
    }

    public Product(String name, String description, LocalDateTime creationDate, Category category) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.category = category;
    }

    public Product(String name, String description, LocalDateTime creationDate, LocalDateTime updateDate, Category category) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
