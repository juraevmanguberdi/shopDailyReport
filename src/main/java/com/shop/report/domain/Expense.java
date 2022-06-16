package com.shop.report.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Expense.
 */
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "code")
    private String code;

    @ManyToOne(optional = false)
    @NotNull
    private ExpenseType expenseType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Expense id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Expense amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getExpenseDate() {
        return this.expenseDate;
    }

    public Expense expenseDate(LocalDate expenseDate) {
        this.setExpenseDate(expenseDate);
        return this;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Expense notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCode() {
        return this.code;
    }

    public Expense code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExpenseType getExpenseType() {
        return this.expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Expense expenseType(ExpenseType expenseType) {
        this.setExpenseType(expenseType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expense)) {
            return false;
        }
        return id != null && id.equals(((Expense) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Expense{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", expenseDate='" + getExpenseDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
