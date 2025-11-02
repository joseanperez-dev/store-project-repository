package store.dto;

public class ProductRequestDTO extends RequestDTO {
    public Long categoryId;
    public Double price;
    public Integer stock;

    public ProductRequestDTO(String name, String description, Long categoryId, Double price, Integer stock) {
        super(name, description);
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
