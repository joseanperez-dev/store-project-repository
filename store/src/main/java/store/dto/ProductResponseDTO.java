package store.dto;

public class ProductResponseDTO extends ResponseDTO {
    public ProductResponseDTO(Long id, String name, String description, String creationDate) {
        super(id, name, description, creationDate);
    }
}
