package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.LiabilityRegistry} entity.
 */
public class LiabilityRegistryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate today;

    private Long totalLiabilities;

    private Long supplier;

    private Long bankLoan;

    private Long other;

    private String code;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    public Long getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Long totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getBankLoan() {
        return bankLoan;
    }

    public void setBankLoan(Long bankLoan) {
        this.bankLoan = bankLoan;
    }

    public Long getOther() {
        return other;
    }

    public void setOther(Long other) {
        this.other = other;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LiabilityRegistryDTO)) {
            return false;
        }

        LiabilityRegistryDTO liabilityRegistryDTO = (LiabilityRegistryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, liabilityRegistryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LiabilityRegistryDTO{" +
            "id=" + getId() +
            ", today='" + getToday() + "'" +
            ", totalLiabilities=" + getTotalLiabilities() +
            ", supplier=" + getSupplier() +
            ", bankLoan=" + getBankLoan() +
            ", other=" + getOther() +
            ", code='" + getCode() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
