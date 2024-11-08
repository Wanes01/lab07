package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are
    // executed.
    private static final double AMOUNT = 100;
    private static final double EXPECTED_BALANCE = 94.9;
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", 1);
        bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the
     * balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(0, bankAccount.getBalance());
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);
        assertEquals(AMOUNT, bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactionsCount());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(EXPECTED_BALANCE, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -AMOUNT);
            fail("Test failed: negative amount withdrawn");
        } catch (IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);
        assertEquals(AMOUNT, bankAccount.getBalance());
        try {
            bankAccount.withdraw(mRossi.getUserID(), 2 * AMOUNT);
            fail(
                    "Test failed: more money was withdrawn than was available in the user's account");
        } catch (IllegalArgumentException e) {
            assertEquals(AMOUNT, bankAccount.getBalance());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }
}
