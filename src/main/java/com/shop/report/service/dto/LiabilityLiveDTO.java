package com.shop.report.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.shop.report.domain.LiabilityLive} entity.
 */
public class LiabilityLiveDTO implements Serializable {

    private Long id;

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
        if (!(o instanceof LiabilityLiveDTO)) {
            return false;
        }

        LiabilityLiveDTO liabilityLiveDTO = (LiabilityLiveDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, liabilityLiveDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LiabilityLiveDTO{" +
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
