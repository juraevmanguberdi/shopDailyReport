package com.shop.report.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "sur_name", nullable = false)
    private String surName;

    @Column(name = "debt_amount")
    private Long debtAmount;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "code")
    private String code;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Client name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return this.surName;
    }

    public Client surName(String surName) {
        this.setSurName(surName);
        return this;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Long getDebtAmount() {
        return this.debtAmount;
    }

    public Client debtAmount(Long debtAmount) {
        this.setDebtAmount(debtAmount);
        return this;
    }

    public void setDebtAmount(Long debtAmount) {
        this.debtAmount = debtAmount;
    }

    public String getAddress() {
        return this.address;
    }

    public Client address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public Client phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return this.notes;
    }

    public Client notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Client createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCode() {
        return this.code;
    }

    public Client code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surName='" + getSurName() + "'" +
            ", debtAmount=" + getDebtAmount() +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", notes='" + getNotes() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
