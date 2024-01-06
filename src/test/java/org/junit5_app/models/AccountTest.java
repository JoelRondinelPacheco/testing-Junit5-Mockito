package org.junit5_app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountName() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));
//        account.setPerson("Joel");
        String expected = "Joel";
        String actual = account.getPerson();
        assertEquals(expected, actual);
        assertTrue(actual.equals("Joel"));
    }

    @Test
    void testAccountBalance() {
        Account account = new Account("Joel", new BigDecimal("1000.12345"));
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
}