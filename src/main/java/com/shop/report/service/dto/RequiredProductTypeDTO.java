package com.shop.report.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.RequiredProductType} entity.
 */
public class RequiredProductTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private String notes;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequiredProductTypeDTO)) {
            return false;
        }

        RequiredProductTypeDTO requiredProductTypeDTO = (RequiredProductTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requiredProductTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequiredProductTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
