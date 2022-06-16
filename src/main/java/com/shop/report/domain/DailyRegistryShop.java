package com.shop.report.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DailyRegistryShop.
 */
@Entity
@Table(name = "daily_registry_shop")
public class DailyRegistryShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "today", nullable = false, unique = true)
    private LocalDate today;

    @NotNull
    @Column(name = "sales", nullable = false)
    private Long sales;

    @NotNull
    @Column(name = "goods", nullable = false)
    private Long goods;

    @Column(name = "cash_shop")
    private Long cashShop;

    @Column(name = "click")
    private Long click;

    @Column(name = "terminal")
    private Long terminal;

    @Column(name = "debt_return")
    private Long debtReturn;

    @Column(name = "debt_given")
    private Long debtGiven;

    @Column(name = "expense")
    private Long expense;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "code")
    private String code;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DailyRegistryShop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getToday() {
        return this.today;
    }

    public DailyRegistryShop today(LocalDate today) {
        this.setToday(today);
        return this;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    public Long getSales() {
        return this.sales;
    }

    public DailyRegistryShop sales(Long sales) {
        this.setSales(sales);
        return this;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Long getGoods() {
        return this.goods;
    }

    public DailyRegistryShop goods(Long goods) {
        this.setGoods(goods);
        return this;
    }

    public void setGoods(Long goods) {
        this.goods = goods;
    }

    public Long getCashShop() {
        return this.cashShop;
    }

    public DailyRegistryShop cashShop(Long cashShop) {
        this.setCashShop(cashShop);
        return this;
    }

    public void setCashShop(Long cashShop) {
        this.cashShop = cashShop;
    }

    public Long getClick() {
        return this.click;
    }

    public DailyRegistryShop click(Long click) {
        this.setClick(click);
        return this;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getTerminal() {
        return this.terminal;
    }

    public DailyRegistryShop terminal(Long terminal) {
        this.setTerminal(terminal);
        return this;
    }

    public void setTerminal(Long terminal) {
        this.terminal = terminal;
    }

    public Long getDebtReturn() {
        return this.debtReturn;
    }

    public DailyRegistryShop debtReturn(Long debtReturn) {
        this.setDebtReturn(debtReturn);
        return this;
    }

    public void setDebtReturn(Long debtReturn) {
        this.debtReturn = debtReturn;
    }

    public Long getDebtGiven() {
        return this.debtGiven;
    }

    public DailyRegistryShop debtGiven(Long debtGiven) {
        this.setDebtGiven(debtGiven);
        return this;
    }

    public void setDebtGiven(Long debtGiven) {
        this.debtGiven = debtGiven;
    }

    public Long getExpense() {
        return this.expense;
    }

    public DailyRegistryShop expense(Long expense) {
        this.setExpense(expense);
        return this;
    }

    public void setExpense(Long expense) {
        this.expense = expense;
    }

    public Long getBalance() {
        return this.balance;
    }

    public DailyRegistryShop balance(Long balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getCode() {
        return this.code;
    }

    public DailyRegistryShop code(String code) {
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
        if (!(o instanceof DailyRegistryShop)) {
            return false;
        }
        return id != null && id.equals(((DailyRegistryShop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyRegistryShop{" +
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
