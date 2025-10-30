package store.dto;

public class ProductRequestDTO extends RequestDTO {
    public Long categoryId;

    public ProductRequestDTO(String name, String description, Long categoryId) {
        super(name, description);
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
