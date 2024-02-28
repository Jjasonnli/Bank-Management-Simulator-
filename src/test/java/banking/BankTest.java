package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	public static final String JASONS_BANK = "Jason Li's banking.Bank";
	public static final String SAVINGS_ID = "87654321";
	public static final String CD_ID = "12345678";
	public static final String CHECKING_ID = "14548692";
	private static final double SAVINGS_APR = 5.00;
	private static final double CD_APR = 10.00;
	private static final double CHECKING_APR = 3.00;

	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank(JASONS_BANK);
	}

	@Test
	void bank_is_created() {
		assertEquals(JASONS_BANK, bank.getName());
	}

	@Test
	void bank_is_empty() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void bank_adds_one_account() {
		bank.addAccount(SAVINGS_ID, new Savings(SAVINGS_ID, SAVINGS_APR, 0.00)); // Provide the correct APR value
		assertTrue(bank.getAccounts().containsKey(SAVINGS_ID));
		assertEquals(SAVINGS_APR, bank.getAccounts().get(SAVINGS_ID).getApr());
		assertEquals(0.00, bank.getAccounts().get(SAVINGS_ID).getBalance());
	}

	@Test
	void bank_adds_two_accounts() {
		bank.addAccount(CD_ID, new Cd(CD_ID, CD_APR, 1000.00));
		assertTrue(bank.getAccounts().containsKey(CD_ID));
		assertEquals(CD_APR, bank.getAccounts().get(CD_ID).getApr());
		assertEquals(1000.00, bank.getAccounts().get(CD_ID).getBalance());

		bank.addAccount(CHECKING_ID, new Checking(CHECKING_ID, CHECKING_APR, 0.00));
		assertTrue(bank.getAccounts().containsKey(CHECKING_ID));
		assertEquals(CHECKING_APR, bank.getAccounts().get(CHECKING_ID).getApr());
		assertEquals(0.00, bank.getAccounts().get(CHECKING_ID).getBalance());
	}

	@Test
	void retrieve_one_account_from_bank() {
		// Add a banking.Checking account to the bank with a known ID
		bank.addAccount(CHECKING_ID, new Checking(CHECKING_ID, CHECKING_APR, 0.00));
		// Retrieve the account by ID
		Account retrievedAccount = bank.retrieveAccountByID(CHECKING_ID);
		// Check if the retrieved account (Checkings) is not null
		assertNotNull(retrievedAccount);
	}

	@Test
	void deposit_money_through_id_at_bank() {
		String accountId = CD_ID;
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Cd(accountId, accountAPR, initialBalance));
		bank.deposit(accountId, 100.00);
		Account retrievedAccount = bank.getAccount(accountId);
		assertEquals(100.00, retrievedAccount.getBalance());
	}

	@Test
	void deposit_money_through_id_at_bank_twice() {
		String accountId = CD_ID;
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Cd(accountId, accountAPR, initialBalance));
		bank.deposit(accountId, 100.00);
		bank.deposit(accountId, 100.00);
		Account retrievedAccount = bank.getAccount(accountId);
		assertEquals(200.00, retrievedAccount.getBalance());
	}

	@Test
	void withdraw_money_through_id_at_bank() {
		String accountId = CD_ID;
		double accountAPR = 10.00;
		double initialBalance = 50.00;
		bank.addAccount(accountId, new Cd(accountId, accountAPR, initialBalance));
		bank.withdraw(accountId, 50.00);
		Account retrievedAccount = bank.getAccount(accountId);
		assertEquals(0.00, retrievedAccount.getBalance());
	}

	@Test
	void withdraw_money_through_id_at_bank_twice() {
		String accountId = CD_ID;
		double accountAPR = 10.00;
		double initialBalance = 50.00;
		bank.addAccount(accountId, new Cd(accountId, accountAPR, initialBalance));
		bank.withdraw(accountId, 20.00);
		bank.withdraw(accountId, 20.00);
		Account retrievedAccount = bank.getAccount(accountId);
		assertEquals(10.00, retrievedAccount.getBalance());
	}
}
