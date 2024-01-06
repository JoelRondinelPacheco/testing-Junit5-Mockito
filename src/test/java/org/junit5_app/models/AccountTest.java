package org.junit5_app.models;

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
        assertNotNull(actual, () -> "La cuenta no puede ser null");
        assertEquals(expected, actual, () -> "El nuombre de la cuenta no es el esperado");
        assertTrue(actual.equals("Joel"), () -> "El nombre de la cuenta no es igual a la real");
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

    @Test
    void testTransferMoneyBetweenAccounts() {
        Account account = new Account("Joel", new BigDecimal("2500"));
        Account account2 = new Account("Daniel", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.setName("Central Bank");
        bank.transfer(account2, account, new BigDecimal("500"));
        assertEquals("1000.8989", account2.getBalance().toPlainString());
        assertEquals("3000", account.getBalance().toPlainString());
    }
    @Test
    void testRelBankAccount() {
        Account account = new Account("Joel", new BigDecimal("2500"));
        Account account2 = new Account("Daniel", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.addAccount(account);
        bank.addAccount(account2);
        bank.setName("Central Bank");
        bank.transfer(account2, account, new BigDecimal("500"));
        //SIN ASSERTALL
        /*
        assertEquals("1000.8989", account2.getBalance().toPlainString());
        assertEquals("3000", account.getBalance().toPlainString());

        assertEquals(2, bank.getAccounts().size());
*/


        // Con assertAll
        assertAll(
                () -> {assertEquals("Central Bank", account.getBank().getName());},
                () -> {assertEquals("1000.8989", account2.getBalance().toPlainString());},
                () -> {assertEquals("3000", account.getBalance().toPlainString());},
                () -> {assertEquals(2, bank.getAccounts().size());},
                () -> {assertEquals("Joel", bank.getAccounts().stream()
                        .filter(c -> c.getPerson().equals("Joel"))
                        .findFirst()
                        .get().getPerson());},
                () -> {assertTrue(bank.getAccounts().stream()
                        .filter(c -> c.getPerson().equals("Joel"))
                        .findFirst()
                        .isPresent());},
                () -> {assertTrue(bank.getAccounts().stream()
                            .anyMatch(c -> c.getPerson().equals("Daniel")));
                }
        );


    }
}