package store.dto;

public class CategoryResponseDTO {
    public Long id;
    public String name;
    public String description;
    public String creationDate;

    public CategoryResponseDTO(Long id, String name, String description, String creationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
