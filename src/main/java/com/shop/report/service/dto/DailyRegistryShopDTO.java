package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.DailyRegistryShop} entity.
 */
public class DailyRegistryShopDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate today;

    @NotNull
    private Long sales;

    @NotNull
    private Long goods;

    private Long cashShop;

    private Long click;

    private Long terminal;

    private Long debtReturn;

    private Long debtGiven;

    private Long expense;

    private Long balance;

    private String code;

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

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Long getGoods() {
        return goods;
    }

    public void setGoods(Long goods) {
        this.goods = goods;
    }

    public Long getCashShop() {
        return cashShop;
    }

    public void setCashShop(Long cashShop) {
        this.cashShop = cashShop;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getTerminal() {
        return terminal;
    }

    public void setTerminal(Long terminal) {
        this.terminal = terminal;
    }

    public Long getDebtReturn() {
        return debtReturn;
    }

    public void setDebtReturn(Long debtReturn) {
        this.debtReturn = debtReturn;
    }

    public Long getDebtGiven() {
        return debtGiven;
    }

    public void setDebtGiven(Long debtGiven) {
        this.debtGiven = debtGiven;
    }

    public Long getExpense() {
        return expense;
    }

    public void setExpense(Long expense) {
        this.expense = expense;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyRegistryShopDTO)) {
            return false;
        }

        DailyRegistryShopDTO dailyRegistryShopDTO = (DailyRegistryShopDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dailyRegistryShopDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyRegistryShopDTO{" +
            "id=" + getId() +
            ", today='" + getToday() + "'" +
            ", sales=" + getSales() +
            ", goods=" + getGoods() +
            ", cashShop=" + getCashShop() +
            ", click=" + getClick() +
            ", terminal=" + getTerminal() +
            ", debtReturn=" + getDebtReturn() +
            ", debtGiven=" + getDebtGiven() +
            ", expense=" + getExpense() +
            ", balance=" + getBalance() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
