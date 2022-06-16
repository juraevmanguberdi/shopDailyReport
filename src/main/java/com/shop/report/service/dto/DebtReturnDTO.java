package com.shop.report.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.shop.report.domain.DebtReturn} entity.
 */
public class DebtReturnDTO implements Serializable {

    private Long id;

    @NotNull
    private Long returnAmount;

    @NotNull
    private LocalDate returnDate;

    private String code;

    private ClientDTO client;

    private PaymentMethodDTO paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
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

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public PaymentMethodDTO getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DebtReturnDTO)) {
            return false;
        }

        DebtReturnDTO debtReturnDTO = (DebtReturnDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, debtReturnDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DebtReturnDTO{" +
            "id=" + getId() +
            ", returnAmount=" + getReturnAmount() +
            ", returnDate='" + getReturnDate() + "'" +
            ", code='" + getCode() + "'" +
            ", client=" + getClient() +
            ", paymentMethod=" + getPaymentMethod() +
            "}";
    }
}
