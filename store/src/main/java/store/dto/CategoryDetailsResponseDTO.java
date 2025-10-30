package store.dto;

import java.util.List;

public class CategoryDetailsResponseDTO extends ResponseDTO {
    public List<ProductResponseDTO> productDtos;

    public CategoryDetailsResponseDTO(Long id, String name, String description, String creationDate, List<ProductResponseDTO> productDtos) {
        super(id, name, description, creationDate);
        this.productDtos = productDtos;
    }
}
