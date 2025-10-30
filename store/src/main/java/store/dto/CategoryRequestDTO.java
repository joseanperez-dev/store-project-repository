package store.dto;

public class CategoryRequestDTO extends ResponseDTO {
    public CategoryRequestDTO(Long id, String name, String description, String creationDate) {
        super(id, name, description, creationDate);
    }
}
