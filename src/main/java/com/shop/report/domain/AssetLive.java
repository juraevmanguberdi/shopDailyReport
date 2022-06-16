package com.shop.report.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AssetLive.
 */
@Entity
@Table(name = "asset_live")
public class AssetLive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "total_assets")
    private Long totalAssets;

    @Column(name = "current_assets")
    private Long currentAssets;

    @Column(name = "money_total")
    private Long moneyTotal;

    @Column(name = "cash_shop")
    private Long cashShop;

    @Column(name = "cash_owner")
    private Long cashOwner;

    @Column(name = "bank_account")
    private Long bankAccount;

    @Column(name = "goods")
    private Long goods;

    @Column(name = "debitor")
    private Long debitor;

    @Column(name = "long_term_assets")
    private Long longTermAssets;

    @Column(name = "transport")
    private Long transport;

    @Column(name = "equipment")
    private Long equipment;

    @Column(name = "real_estate")
    private Long realEstate;

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

    public AssetLive id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalAssets() {
        return this.totalAssets;
    }

    public AssetLive totalAssets(Long totalAssets) {
        this.setTotalAssets(totalAssets);
        return this;
    }

    public void setTotalAssets(Long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Long getCurrentAssets() {
        return this.currentAssets;
    }

    public AssetLive currentAssets(Long currentAssets) {
        this.setCurrentAssets(currentAssets);
        return this;
    }

    public void setCurrentAssets(Long currentAssets) {
        this.currentAssets = currentAssets;
    }

    public Long getMoneyTotal() {
        return this.moneyTotal;
    }

    public AssetLive moneyTotal(Long moneyTotal) {
        this.setMoneyTotal(moneyTotal);
        return this;
    }

    public void setMoneyTotal(Long moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public Long getCashShop() {
        return this.cashShop;
    }

    public AssetLive cashShop(Long cashShop) {
        this.setCashShop(cashShop);
        return this;
    }

    public void setCashShop(Long cashShop) {
        this.cashShop = cashShop;
    }

    public Long getCashOwner() {
        return this.cashOwner;
    }

    public AssetLive cashOwner(Long cashOwner) {
        this.setCashOwner(cashOwner);
        return this;
    }

    public void setCashOwner(Long cashOwner) {
        this.cashOwner = cashOwner;
    }

    public Long getBankAccount() {
        return this.bankAccount;
    }

    public AssetLive bankAccount(Long bankAccount) {
        this.setBankAccount(bankAccount);
        return this;
    }

    public void setBankAccount(Long bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getGoods() {
        return this.goods;
    }

    public AssetLive goods(Long goods) {
        this.setGoods(goods);
        return this;
    }

    public void setGoods(Long goods) {
        this.goods = goods;
    }

    public Long getDebitor() {
        return this.debitor;
    }

    public AssetLive debitor(Long debitor) {
        this.setDebitor(debitor);
        return this;
    }

    public void setDebitor(Long debitor) {
        this.debitor = debitor;
    }

    public Long getLongTermAssets() {
        return this.longTermAssets;
    }

    public AssetLive longTermAssets(Long longTermAssets) {
        this.setLongTermAssets(longTermAssets);
        return this;
    }

    public void setLongTermAssets(Long longTermAssets) {
        this.longTermAssets = longTermAssets;
    }

    public Long getTransport() {
        return this.transport;
    }

    public AssetLive transport(Long transport) {
        this.setTransport(transport);
        return this;
    }

    public void setTransport(Long transport) {
        this.transport = transport;
    }

    public Long getEquipment() {
        return this.equipment;
    }

    public AssetLive equipment(Long equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public void setEquipment(Long equipment) {
        this.equipment = equipment;
    }

    public Long getRealEstate() {
        return this.realEstate;
    }

    public AssetLive realEstate(Long realEstate) {
        this.setRealEstate(realEstate);
        return this;
    }

    public void setRealEstate(Long realEstate) {
        this.realEstate = realEstate;
    }

    public Long getOther() {
        return this.other;
    }

    public AssetLive other(Long other) {
        this.setOther(other);
        return this;
    }

    public void setOther(Long other) {
        this.other = other;
    }

    public String getCode() {
        return this.code;
    }

    public AssetLive code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return this.notes;
    }

    public AssetLive notes(String notes) {
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
        if (!(o instanceof AssetLive)) {
            return false;
        }
        return id != null && id.equals(((AssetLive) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetLive{" +
            "id=" + getId() +
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
