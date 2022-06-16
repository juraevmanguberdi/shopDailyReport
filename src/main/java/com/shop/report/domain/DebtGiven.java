package com.shop.report.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DebtGiven.
 */
@Entity
@Table(name = "debt_given")
public class DebtGiven implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "debt_amount", nullable = false)
    private Long debtAmount;

    @NotNull
    @Column(name = "debt_date", nullable = false)
    private LocalDate debtDate;

    @NotNull
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "code")
    private String code;

    @ManyToOne(optional = false)
    @NotNull
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DebtGiven id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebtAmount() {
        return this.debtAmount;
    }

    public DebtGiven debtAmount(Long debtAmount) {
        this.setDebtAmount(debtAmount);
        return this;
    }

    public void setDebtAmount(Long debtAmount) {
        this.debtAmount = debtAmount;
    }

    public LocalDate getDebtDate() {
        return this.debtDate;
    }

    public DebtGiven debtDate(LocalDate debtDate) {
        this.setDebtDate(debtDate);
        return this;
    }

    public void setDebtDate(LocalDate debtDate) {
        this.debtDate = debtDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public DebtGiven returnDate(LocalDate returnDate) {
        this.setReturnDate(returnDate);
        return this;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getCode() {
        return this.code;
    }

    public DebtGiven code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public DebtGiven client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DebtGiven)) {
            return false;
        }
        return id != null && id.equals(((DebtGiven) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DebtGiven{" +
            "id=" + getId() +
            ", debtAmount=" + getDebtAmount() +
            ", debtDate='" + getDebtDate() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
