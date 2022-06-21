package com.shop.report.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BorrowedDebt.
 */
@Entity
@Table(name = "borrowed_debt")
public class BorrowedDebt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "notes")
    private String notes;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    private BorrowedDebtType borrowedDebtType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BorrowedDebt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return this.amount;
    }

    public BorrowedDebt amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return this.notes;
    }

    public BorrowedDebt notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCode() {
        return this.code;
    }

    public BorrowedDebt code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public BorrowedDebt date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BorrowedDebtType getBorrowedDebtType() {
        return this.borrowedDebtType;
    }

    public void setBorrowedDebtType(BorrowedDebtType borrowedDebtType) {
        this.borrowedDebtType = borrowedDebtType;
    }

    public BorrowedDebt borrowedDebtType(BorrowedDebtType borrowedDebtType) {
        this.setBorrowedDebtType(borrowedDebtType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BorrowedDebt)) {
            return false;
        }
        return id != null && id.equals(((BorrowedDebt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorrowedDebt{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", notes='" + getNotes() + "'" +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
