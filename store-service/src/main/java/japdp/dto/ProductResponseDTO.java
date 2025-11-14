package japdp.dto;

public class ProductResponseDTO extends ResponseDTO {
    public String categoryName;
    public Double price;
    public Integer stock;

    public ProductResponseDTO(Long id, String name, String description, String categoryName, Double price, Integer stock, String creationDate, String updateName) {
        super(id, name, description, creationDate, updateName);
        this.categoryName = categoryName;
        this.price = price;
        this.stock = stock;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
