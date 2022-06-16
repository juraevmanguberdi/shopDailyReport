package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.OwnerExpense} entity.
 */
public class OwnerExpenseDTO implements Serializable {

    private Long id;

    @NotNull
    private Long amount;

    private String code;

    private LocalDate expenseDate;

    private String notes;

    private OwnerExpenseTypeDTO ownerExpenseType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OwnerExpenseTypeDTO getOwnerExpenseType() {
        return ownerExpenseType;
    }

    public void setOwnerExpenseType(OwnerExpenseTypeDTO ownerExpenseType) {
        this.ownerExpenseType = ownerExpenseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OwnerExpenseDTO)) {
            return false;
        }

        OwnerExpenseDTO ownerExpenseDTO = (OwnerExpenseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ownerExpenseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OwnerExpenseDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", code='" + getCode() + "'" +
            ", expenseDate='" + getExpenseDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", ownerExpenseType=" + getOwnerExpenseType() +
            "}";
    }
}
