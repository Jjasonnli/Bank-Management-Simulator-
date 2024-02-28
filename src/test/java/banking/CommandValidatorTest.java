package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	// VALID COMMANDS
	// create checkings, savings, cd
	// deposit checkings, savings, cd

	@BeforeEach
	void setUp() {
		bank = new Bank("Jason banking.Bank");
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void valid_create_checkings_account_command() {
		boolean actual = commandValidator.validate("Create checking 12345678 0.01");
		assertTrue(actual);
	}

	@Test
	void valid_create_savings_account_command() {
		boolean actual = commandValidator.validate("Create savings 12345678 0.01");
		assertTrue(actual);
	}

	@Test
	void valid_create_cd_account_command() {
		boolean actual = commandValidator.validate("Create cd 12345678 5.0 5000");
		assertTrue(actual);
	}

	@Test
	void duplicate_account_id_is_invalid() {
		bank.addAccount("12345678", new Checking("12345678", 0.01, 0.00));
		boolean actual = commandValidator.validate("Create checking 12345678 0.01");
		assertFalse(actual);
	}

	@Test
	void invalid_balance_create_cd_account() {
		boolean actual = commandValidator.validate("Create cd 12345678 5.0 250");
		assertFalse(actual);
	}

	@Test
	void invalid_over_balance_create_cd_account() {
		boolean actual = commandValidator.validate("Create cd 12345678 2.0 10001");
		assertFalse(actual);
	}

	@Test
	void invalid_balance_create_checkings_account() {
		boolean actual = commandValidator.validate("Create checking 12345678 10.0 120");
		assertFalse(actual);
	}

	@Test
	void invalid_apr_create_checkings_account() {
		boolean actual = commandValidator.validate("Create checking 12345678 12.0");
		assertFalse(actual);
	}

	@Test
	void invalid_negative_apr_create_checkings_account() {
		boolean actual = commandValidator.validate("Create checking 12345678 -1.0");
		assertFalse(actual);
	}

	@Test
	void valid_0_apr_create_checkings_account() {
		boolean actual = commandValidator.validate("Create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	void valid_deposit_savings_account() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 100");
		assertTrue(actual);
		// Retrieve the account and verify the balance
		// Account retrievedAccount = bank.getAccount(accountId);
		// assertEquals(100.00, retrievedAccount.getBalance());
	}

	@Test
	void valid_deposit_checkings_account() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 100");
		assertTrue(actual);
		// Retrieve the account and verify the balance
		// Account retrievedAccount = bank.getAccount(accountId);
		// assertEquals(100.00, retrievedAccount.getBalance());
	}

	@Test
	void valid_deposit_cd_account() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 100");
		assertTrue(actual);
		// Retrieve the account and verify the balance
		// Account retrievedAccount = bank.getAccount(accountId);
		// assertEquals(100.00, retrievedAccount.getBalance());
	}

	@Test
	void invalid_id_deposit_any_account() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 87654321 100"); // invalid id
		assertFalse(actual);
		// Retrieve the account and verify the balance
		// Account retrievedAccount = bank.getAccount(accountId);
		// assertEquals(0.00, retrievedAccount.getBalance());
	}

	@Test
	void valid_double_deposit_any_account() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 100");
		boolean actual2 = commandValidator.validate("Deposit 12345678 100");
		assertTrue(actual);
		assertTrue(actual2);
		// Retrieve the account and verify the balance
		// Account retrievedAccount = bank.getAccount(accountId);
		// assertEquals(200.00, retrievedAccount.getBalance());
	}

	@Test
	void invalid_typo_create_checkings_account_command() {
		boolean actual = commandValidator.validate("creat checking 12345678 0.01");
		assertFalse(actual);
	}

	@Test
	void invalid_typo_deposit_checkings_account_command() {
		boolean actual = commandValidator.validate("deposi checking 12345678 100");
		assertFalse(actual);
	}

	@Test
	void invalid_over_1000_deposit_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void valid_1000_deposit_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void invalid_over_2500_deposit_savings_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 2501");
		assertFalse(actual);
	}

	@Test
	void valid_2500_deposit_savings_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 2500");
		assertTrue(actual);
	}

	@Test
	void invalid_deposit_any_amount_cd_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 2500.00;
		bank.addAccount(accountId, new Cd(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 2500");
		boolean actual2 = commandValidator.validate("Deposit 12345678 0");
		boolean actual3 = commandValidator.validate("Deposit 12345678 1");
		assertFalse(actual);
		assertFalse(actual2);
		assertFalse(actual3);
	}

	@Test
	void valid_withdraw_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 900");
		assertTrue(actual);
		boolean actual2 = commandValidator.validate("Withdraw 12345678 400");
		assertTrue(actual2);
	}

	@Test
	void invalid_over_400_withdraw_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 900");
		assertTrue(actual);
		boolean actual2 = commandValidator.validate("Withdraw 12345678 401");
		assertFalse(actual2);
	}

	@Test
	void valid_withdraw_saving_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 2500");
		assertTrue(actual);
		boolean actual2 = commandValidator.validate("Withdraw 12345678 1000");
		assertTrue(actual2);
	}

	@Test
	void invalid_over_1000_withdraw_saving_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 2500");
		assertTrue(actual);
		boolean actual2 = commandValidator.validate("Withdraw 12345678 1001");
		assertFalse(actual2);
	}

	@Test
	void valid_withdraw_over_balance_saving_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 200");
		assertTrue(actual);
		boolean actual2 = commandValidator.validate("Withdraw 12345678 201");
		assertTrue(actual2);
	}

	@Test
	void valid_withdraw_over_balance_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 0.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));
		boolean actual = commandValidator.validate("Deposit 12345678 200");
		assertTrue(actual);
		boolean actual2 = commandValidator.validate("Withdraw 12345678 201");
		assertTrue(actual2);
	}

	@Test
	void valid_transfer_checking_to_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 200.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));

		String accountId_2 = "87654321";
		double accountAPR_2 = 10.00;
		double initialBalance_2 = 120;
		bank.addAccount(accountId_2, new Checking(accountId_2, accountAPR_2, initialBalance_2));

		boolean actual = commandValidator.validate("Transfer 12345678 87654321 200");
		assertTrue(actual);
	}

	@Test
	void valid_transfer_savings_to_savings_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 200.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));

		String accountId_2 = "87654321";
		double accountAPR_2 = 10.00;
		double initialBalance_2 = 120;
		bank.addAccount(accountId_2, new Savings(accountId_2, accountAPR_2, initialBalance_2));

		boolean actual = commandValidator.validate("Transfer 12345678 87654321 200");
		assertTrue(actual);
	}

	@Test
	void valid_transfer_savings_to_checking_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 200.00;
		bank.addAccount(accountId, new Savings(accountId, accountAPR, initialBalance));

		String accountId_2 = "87654321";
		double accountAPR_2 = 10.00;
		double initialBalance_2 = 120;
		bank.addAccount(accountId_2, new Checking(accountId_2, accountAPR_2, initialBalance_2));

		boolean actual = commandValidator.validate("Transfer 12345678 87654321 200");
		assertTrue(actual);
	}

	@Test
	void valid_transfer_checking_to_savings_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 200.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));

		String accountId_2 = "87654321";
		double accountAPR_2 = 10.00;
		double initialBalance_2 = 120;
		bank.addAccount(accountId_2, new Savings(accountId_2, accountAPR_2, initialBalance_2));

		boolean actual = commandValidator.validate("Transfer 12345678 87654321 200");
		assertTrue(actual);
	}

	@Test
	void invalid_transfer_from_same_account_command() {
		String accountId = "12345678";
		double accountAPR = 10.00;
		double initialBalance = 200.00;
		bank.addAccount(accountId, new Checking(accountId, accountAPR, initialBalance));

		boolean actual = commandValidator.validate("Transfer 12345678 12345678 200");
		assertFalse(actual);
	}

	@Test
	void valid_pass_time_command() {
		boolean actual = commandValidator.validate("Pass 1");
		assertTrue(actual);
	}

	@Test
	void invalid_pass_time_command() {
		boolean actual = commandValidator.validate("Pass 0");
		assertFalse(actual);
	}

	@Test
	void invalid_double_pass_time_command() {
		boolean actual = commandValidator.validate("Pass 1.1");
		assertFalse(actual);
	}

	@Test
	void invalid_negative_pass_time_command() {
		boolean actual = commandValidator.validate("Pass -1");
		assertFalse(actual);
	}

	@Test
	void invalid_word_pass_time_command() {
		boolean actual = commandValidator.validate("Pass adsf");
		assertFalse(actual);
	}

}
