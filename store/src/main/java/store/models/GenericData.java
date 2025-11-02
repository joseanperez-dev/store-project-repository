package store.models;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/*
    IMPORTANTE
    Esta clase "padre" debe tener la anotación @MappedSuperclass para que los valores
    almacenados en las propiedades de las clases "hijas" se registren en la base de datos.
*/
@MappedSuperclass
public class GenericData {
    String name;
    String description;

    LocalDateTime creationDate;
    LocalDateTime updateDate;

    public GenericData() {
    }

    public GenericData(String name, String description, LocalDateTime creationDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public GenericData(String name, String description, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
