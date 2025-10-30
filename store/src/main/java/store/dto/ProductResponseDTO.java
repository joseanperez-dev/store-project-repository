package store.dto;

public class ProductResponseDTO extends ResponseDTO {
    public String categoryName;

    public ProductResponseDTO(Long id, String name, String description, String creationDate, String categoryName) {
        super(id, name, description, creationDate);
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
