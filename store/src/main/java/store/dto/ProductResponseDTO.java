package store.dto;

public class ProductResponseDTO extends ResponseDTO {
    public String categoryName;

    public ProductResponseDTO(Long id, String name, String description, String categoryName, String creationDate, String updateName) {
        super(id, name, description, creationDate, updateName);
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
