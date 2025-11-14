package japdp.dto;

import java.util.List;

public class CategoryDetailsResponseDTO extends ResponseDTO {
    public List<ProductResponseDTO> productDtos;

    public CategoryDetailsResponseDTO(
            Long id, String name, String description, String creationDate,
            String updateDate, List<ProductResponseDTO> productDtos
    ) {
        super(id, name, description, creationDate, updateDate);
        this.productDtos = productDtos;
    }
}
