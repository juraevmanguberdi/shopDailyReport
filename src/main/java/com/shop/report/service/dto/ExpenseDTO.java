package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.Expense} entity.
 */
public class ExpenseDTO implements Serializable {

    private Long id;

    @NotNull
    private Long amount;

    private LocalDate expenseDate;

    private String notes;

    private String code;

    private ExpenseTypeDTO expenseType;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExpenseTypeDTO getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseTypeDTO expenseType) {
        this.expenseType = expenseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpenseDTO)) {
            return false;
        }

        ExpenseDTO expenseDTO = (ExpenseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, expenseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpenseDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", expenseDate='" + getExpenseDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", code='" + getCode() + "'" +
            ", expenseType=" + getExpenseType() +
            "}";
    }
}
