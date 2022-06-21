package com.shop.report.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.RequiredProduct} entity.
 */
public class RequiredProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String note;

    private String code;

    private RequiredProductTypeDTO requiredProductType;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RequiredProductTypeDTO getRequiredProductType() {
        return requiredProductType;
    }

    public void setRequiredProductType(RequiredProductTypeDTO requiredProductType) {
        this.requiredProductType = requiredProductType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequiredProductDTO)) {
            return false;
        }

        RequiredProductDTO requiredProductDTO = (RequiredProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requiredProductDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequiredProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", note='" + getNote() + "'" +
            ", code='" + getCode() + "'" +
            ", requiredProductType=" + getRequiredProductType() +
            "}";
    }
}
