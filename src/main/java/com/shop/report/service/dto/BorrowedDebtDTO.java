package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.BorrowedDebt} entity.
 */
public class BorrowedDebtDTO implements Serializable {

    private Long id;

    @NotNull
    private Long amount;

    private String notes;

    private String code;

    private LocalDate date;

    private BorrowedDebtTypeDTO borrowedDebtType;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BorrowedDebtTypeDTO getBorrowedDebtType() {
        return borrowedDebtType;
    }

    public void setBorrowedDebtType(BorrowedDebtTypeDTO borrowedDebtType) {
        this.borrowedDebtType = borrowedDebtType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BorrowedDebtDTO)) {
            return false;
        }

        BorrowedDebtDTO borrowedDebtDTO = (BorrowedDebtDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, borrowedDebtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorrowedDebtDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", notes='" + getNotes() + "'" +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", borrowedDebtType=" + getBorrowedDebtType() +
            "}";
    }
}
