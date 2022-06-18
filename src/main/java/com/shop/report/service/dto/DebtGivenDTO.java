package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.DebtGiven} entity.
 */
public class DebtGivenDTO implements Serializable {

    private Long id;

    @NotNull
    private Long debtAmount;

    @NotNull
    private LocalDate debtDate;

    @NotNull
    private LocalDate returnDate;

    private String code;

    private String notes;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(Long debtAmount) {
        this.debtAmount = debtAmount;
    }

    public LocalDate getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(LocalDate debtDate) {
        this.debtDate = debtDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DebtGivenDTO)) {
            return false;
        }

        DebtGivenDTO debtGivenDTO = (DebtGivenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, debtGivenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DebtGivenDTO{" +
            "id=" + getId() +
            ", debtAmount=" + getDebtAmount() +
            ", debtDate='" + getDebtDate() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", code='" + getCode() + "'" +
            ", notes='" + getNotes() + "'" +
            ", client=" + getClient() +
            "}";
    }
}
