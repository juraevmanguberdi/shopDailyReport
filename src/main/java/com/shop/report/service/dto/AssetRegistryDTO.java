package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.AssetRegistry} entity.
 */
public class AssetRegistryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate today;

    private Long totalAssets;

    private Long currentAssets;

    private Long moneyTotal;

    private Long cashShop;

    private Long cashOwner;

    private Long bankAccount;

    private Long goods;

    private Long debitor;

    private Long longTermAssets;

    private Long transport;

    private Long equipment;

    private Long realEstate;

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

    public Long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Long getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(Long currentAssets) {
        this.currentAssets = currentAssets;
    }

    public Long getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(Long moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public Long getCashShop() {
        return cashShop;
    }

    public void setCashShop(Long cashShop) {
        this.cashShop = cashShop;
    }

    public Long getCashOwner() {
        return cashOwner;
    }

    public void setCashOwner(Long cashOwner) {
        this.cashOwner = cashOwner;
    }

    public Long getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Long bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getGoods() {
        return goods;
    }

    public void setGoods(Long goods) {
        this.goods = goods;
    }

    public Long getDebitor() {
        return debitor;
    }

    public void setDebitor(Long debitor) {
        this.debitor = debitor;
    }

    public Long getLongTermAssets() {
        return longTermAssets;
    }

    public void setLongTermAssets(Long longTermAssets) {
        this.longTermAssets = longTermAssets;
    }

    public Long getTransport() {
        return transport;
    }

    public void setTransport(Long transport) {
        this.transport = transport;
    }

    public Long getEquipment() {
        return equipment;
    }

    public void setEquipment(Long equipment) {
        this.equipment = equipment;
    }

    public Long getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(Long realEstate) {
        this.realEstate = realEstate;
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
        if (!(o instanceof AssetRegistryDTO)) {
            return false;
        }

        AssetRegistryDTO assetRegistryDTO = (AssetRegistryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetRegistryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetRegistryDTO{" +
            "id=" + getId() +
            ", today='" + getToday() + "'" +
            ", totalAssets=" + getTotalAssets() +
            ", currentAssets=" + getCurrentAssets() +
            ", moneyTotal=" + getMoneyTotal() +
            ", cashShop=" + getCashShop() +
            ", cashOwner=" + getCashOwner() +
            ", bankAccount=" + getBankAccount() +
            ", goods=" + getGoods() +
            ", debitor=" + getDebitor() +
            ", longTermAssets=" + getLongTermAssets() +
            ", transport=" + getTransport() +
            ", equipment=" + getEquipment() +
            ", realEstate=" + getRealEstate() +
            ", other=" + getOther() +
            ", code='" + getCode() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
