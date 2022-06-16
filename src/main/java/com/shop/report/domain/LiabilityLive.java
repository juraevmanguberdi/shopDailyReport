package com.shop.report.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A LiabilityLive.
 */
@Entity
@Table(name = "liability_live")
public class LiabilityLive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "total_liabilities")
    private Long totalLiabilities;

    @Column(name = "supplier")
    private Long supplier;

    @Column(name = "bank_loan")
    private Long bankLoan;

    @Column(name = "other")
    private Long other;

    @Column(name = "code")
    private String code;

    @Column(name = "notes")
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LiabilityLive id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalLiabilities() {
        return this.totalLiabilities;
    }

    public LiabilityLive totalLiabilities(Long totalLiabilities) {
        this.setTotalLiabilities(totalLiabilities);
        return this;
    }

    public void setTotalLiabilities(Long totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Long getSupplier() {
        return this.supplier;
    }

    public LiabilityLive supplier(Long supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getBankLoan() {
        return this.bankLoan;
    }

    public LiabilityLive bankLoan(Long bankLoan) {
        this.setBankLoan(bankLoan);
        return this;
    }

    public void setBankLoan(Long bankLoan) {
        this.bankLoan = bankLoan;
    }

    public Long getOther() {
        return this.other;
    }

    public LiabilityLive other(Long other) {
        this.setOther(other);
        return this;
    }

    public void setOther(Long other) {
        this.other = other;
    }

    public String getCode() {
        return this.code;
    }

    public LiabilityLive code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return this.notes;
    }

    public LiabilityLive notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LiabilityLive)) {
            return false;
        }
        return id != null && id.equals(((LiabilityLive) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LiabilityLive{" +
            "id=" + getId() +
            ", totalLiabilities=" + getTotalLiabilities() +
            ", supplier=" + getSupplier() +
            ", bankLoan=" + getBankLoan() +
            ", other=" + getOther() +
            ", code='" + getCode() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
