package com.shop.report.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DebtReturn.
 */
@Entity
@Table(name = "debt_return")
public class DebtReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "return_amount", nullable = false)
    private Long returnAmount;

    @NotNull
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "code")
    private String code;

    @ManyToOne(optional = false)
    @NotNull
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    private PaymentMethod paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DebtReturn id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReturnAmount() {
        return this.returnAmount;
    }

    public DebtReturn returnAmount(Long returnAmount) {
        this.setReturnAmount(returnAmount);
        return this;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public DebtReturn returnDate(LocalDate returnDate) {
        this.setReturnDate(returnDate);
        return this;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getCode() {
        return this.code;
    }

    public DebtReturn code(String code) {
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

    public DebtReturn client(Client client) {
        this.setClient(client);
        return this;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public DebtReturn paymentMethod(PaymentMethod paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DebtReturn)) {
            return false;
        }
        return id != null && id.equals(((DebtReturn) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DebtReturn{" +
            "id=" + getId() +
            ", returnAmount=" + getReturnAmount() +
            ", returnDate='" + getReturnDate() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
