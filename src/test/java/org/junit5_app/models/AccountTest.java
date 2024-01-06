package org.junit5_app.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit5_app.exceptions.InsufficientMoneyException;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) Una sola instancia para todos los metodos, no es buena practica
class AccountTest {
    Account account;
    @BeforeEach
    void initTestMethod() {
        System.out.println("Iniciando el metodo.");
        this.account = new Account("Joel", new BigDecimal("1000.12345"));
    }
    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test (la clase test)");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test");
    }

    @Test
    @DisplayName("Probando el nombre de la cuenta")
    void testAccountName() {
//        account.setPerson("Joel");
        String expected = "Joel";
        String actual = account.getPerson();
        assertNotNull(actual, () -> "La cuenta no puede ser null");
        assertEquals(expected, actual, () -> "El nuombre de la cuenta no es el esperado");
        assertTrue(actual.equals("Joel"), () -> "El nombre de la cuenta no es igual a la real");
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta corriente, que no sea null, mayor que cero y valor esperado")
    void testAccountBalance() {
        assertNotNull(account.getBalance());
        assertEquals(1000.12345, account.getBalance().doubleValue());
        assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Testeando referencia que sean iguales con el metodo equals")
    void testAccountReference() {
        account = new Account("Joel", new BigDecimal("100.123"));
        Account account2 = new Account("Joel", new BigDecimal("100.123"));

//        assertNotEquals(accountJoel, accountJoel2);
        assertEquals(account, account2);
    }

    @Test
    void testAccountDebit() {
        account.debit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.12345", account.getBalance().toPlainString());
    }

    @Test
    void testAccountCredit() {
        account.credit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.12345", account.getBalance().toPlainString());
    }

    @Test
    void testInsufficientMoneyException() {
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
    //@Disabled //Desactiva el test. No lo hace fallar solo lo ignora.
    void testRelBankAccount() {
        //Fuerza el error
        //fail();
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
                () -> {assertEquals("Central Bank", account.getBank().getName(), "Mensaje de error que siempre se crea, por mas que no salga error" );},
                () -> {assertEquals("1000.8989", account2.getBalance().toPlainString(), () -> "Mensaje de error que solo se crea si el test falla");},
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

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testOnlyWindow() {

    }
    @Test
    @EnabledOnOs(OS.MAC)
    void testOnlyMac() {

    }
    @Test
    @EnabledOnOs(OS.LINUX)
    void testOnlyLinux() {

    }

    @Test
    @DisabledOnOs(OS.LINUX)
    void testDisabledLinux() {
    }
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testDisabledWindows() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testOnlyJDK8() {
    }
    @Test
    @DisabledOnJre(JRE.JAVA_8)
    void testDisabledJDK8() {
    }
    @Test
    @EnabledOnJre(JRE.OTHER)
    void testOnlyJDKOther() {
    }

    @Test
    void testPrintSystemProperties(){
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> {
            System.out.println("K: " + k + ", V: " + v);
        });
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "17.*")
    void testJavaVersion() {

    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testOnly64(){}
}