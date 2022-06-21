package com.shop.report.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RequiredProduct.
 */
@Entity
@Table(name = "required_product")
public class RequiredProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "code")
    private String code;

    @ManyToOne(optional = false)
    @NotNull
    private RequiredProductType requiredProductType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequiredProduct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RequiredProduct name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public RequiredProduct note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCode() {
        return this.code;
    }

    public RequiredProduct code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RequiredProductType getRequiredProductType() {
        return this.requiredProductType;
    }

    public void setRequiredProductType(RequiredProductType requiredProductType) {
        this.requiredProductType = requiredProductType;
    }

    public RequiredProduct requiredProductType(RequiredProductType requiredProductType) {
        this.setRequiredProductType(requiredProductType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequiredProduct)) {
            return false;
        }
        return id != null && id.equals(((RequiredProduct) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequiredProduct{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", note='" + getNote() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
