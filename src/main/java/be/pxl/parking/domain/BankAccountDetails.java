package be.pxl.parking.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class BankAccountDetails {

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Bank bank;

    public BankAccountDetails(Bank bank, String accountNumber) {
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    public BankAccountDetails() {
        // JPA only
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Bank getBank() {
        return bank;
    }
}