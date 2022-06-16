package com.shop.report.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.PaymentMethod} entity.
 */
public class PaymentMethodDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private String note;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethodDTO)) {
            return false;
        }

        PaymentMethodDTO paymentMethodDTO = (PaymentMethodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentMethodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
