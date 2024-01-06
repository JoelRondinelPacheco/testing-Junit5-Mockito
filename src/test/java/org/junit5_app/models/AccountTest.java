package org.junit5_app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit5_app.exceptions.InsufficientMoneyException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountName() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));
//        account.setPerson("Joel");
        String expected = "Joel";
        String actual = account.getPerson();
        assertNotNull(actual);
        assertEquals(expected, actual);
        assertTrue(actual.equals("Joel"));
    }

    @Test
    void testAccountBalance() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));
        assertNotNull(account.getBalance());
        assertEquals(1000.12345, account.getBalance().doubleValue());
        assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testAccountReference() {
        Account accountJoel = new Account("Joel", new BigDecimal("100.123"));
        Account accountJoel2 = new Account("Joel", new BigDecimal("100.123"));

//        assertNotEquals(accountJoel, accountJoel2);
        assertEquals(accountJoel, accountJoel2);
    }

    @Test
    void testAccountDebit() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));
        account.debit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.12345", account.getBalance().toPlainString());
    }

    @Test
    void testAccountCredit() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));
        account.credit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.12345", account.getBalance().toPlainString());
    }

    @Test
    void testInsufficientMoneyException() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));

        Exception exception = assertThrows(InsufficientMoneyException.class, () -> {
            account.debit(new BigDecimal("1500"));
        });

        String expected = "Insufficient money";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }
}