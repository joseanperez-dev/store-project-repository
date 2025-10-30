package store.dto;

public class CategoryResponseDTO extends ResponseDTO {
    public CategoryResponseDTO(Long id, String name, String description, String creationDate) {
        super(id, name, description, creationDate);
    }
}
