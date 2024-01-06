package org.junit5_app.models;

import java.math.BigDecimal;

public class Account {
    private String person;
    private BigDecimal balance;

    public Account(String person, BigDecimal balance) {
        this.person = person;
        this.balance = balance;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Account)) {
            return false;
        }

        Account c = (Account) obj;

        if (this.person == null || this.balance == null) {
            return false;
        }
        return this.person.equals(c.getPerson()) && this.balance.equals(c.getBalance());
    }
}
