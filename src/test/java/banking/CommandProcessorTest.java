package banking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;
	MasterControl masterControl;
	List<String> input;

	@Test

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		bank = new Bank("Jason banking.Bank");
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank), new Bank("Jason"));
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void create_Checking_Account_Has_Correct_Id_And_Apr() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(0.0, createdAccount.getBalance());
		assertEquals(0.01, createdAccount.getApr());
	}

	@Test
	void create_Saving_Account_Has_Correct_Id_And_Apr() {
		commandProcessor.processCommand("Create savings 12345678 1.0");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(0.0, createdAccount.getBalance());
		assertEquals(1.0, createdAccount.getApr());
	}

	@Test
	void create_CD_Account_Has_Correct_Id_Apr_And_Balance() {
		commandProcessor.processCommand("Create cd 12345678 1.0 1200");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(1200.00, createdAccount.getBalance());
		assertEquals(1.00, createdAccount.getApr());
	}

	@Test
	void deposit_Checking_Account_Has_Correct_Balance() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 100");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(100.00, createdAccount.getBalance());
	}

	@Test
	void deposit_Saving_Account_Has_Correct_Balance() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 120");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(120.00, createdAccount.getBalance());
	}

	@Test
	void deposit_Checking_Account_can_be_1000() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 1000");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(1000, createdAccount.getBalance());
	}

	@Test
	void withdraw_checking_account_has_correct_balance() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 1000");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(1000, createdAccount.getBalance());
		commandProcessor.processCommand("Withdraw 12345678 300");
		assertEquals(700, createdAccount.getBalance());
	}

	@Test
	void withdraw_over_balance_checking_account_has_correct_balance() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(300, createdAccount.getBalance());
		commandProcessor.processCommand("Withdraw 12345678 400");
		assertEquals(0, createdAccount.getBalance());
	}

	@Test
	void withdraw_0_checking_account_has_correct_balance() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(300, createdAccount.getBalance());
		commandProcessor.processCommand("Withdraw 12345678 0");
		assertEquals(300, createdAccount.getBalance());
	}

	@Test
	void withdraw_savings_account_has_correct_balance() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(300, createdAccount.getBalance());
		commandProcessor.processCommand("Withdraw 12345678 100");
		assertEquals(200, createdAccount.getBalance());
	}

	@Test
	void withdraw_over_balance_savings_account_has_correct_balance() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		assertEquals(300, createdAccount.getBalance());
		commandProcessor.processCommand("Withdraw 12345678 400");
		assertEquals(0, createdAccount.getBalance());
	}

	@Test
	void transfer_between_checking_and_checking_has_correct_balance() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create checking 12345679 0.01");
		commandProcessor.processCommand("Deposit 12345679 300");
		Account to_createdAccount = bank.getAccount("12345679");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(100, from_createdAccount.getBalance());
		assertEquals(500, to_createdAccount.getBalance());
	}

	@Test
	void transfer_between_checking_and_savings_has_correct_balance() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create savings 12345679 0.01");
		commandProcessor.processCommand("Deposit 12345679 300");
		Account to_createdAccount = bank.getAccount("12345679");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(100, from_createdAccount.getBalance());
		assertEquals(500, to_createdAccount.getBalance());
	}

	@Test
	void transfer_between_savings_and_savings_has_correct_balance() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create savings 12345679 0.01");
		commandProcessor.processCommand("Deposit 12345679 300");
		Account to_createdAccount = bank.getAccount("12345679");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(100, from_createdAccount.getBalance());
		assertEquals(500, to_createdAccount.getBalance());
	}

	@Test
	void transfer_between_savings_and_checking_has_correct_balance() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create checking 12345679 0.01");
		commandProcessor.processCommand("Deposit 12345679 300");
		Account to_createdAccount = bank.getAccount("12345679");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(100, from_createdAccount.getBalance());
		assertEquals(500, to_createdAccount.getBalance());
	}

	@Test
	void invalid_transfer_between_cd_and_checking() {
		commandProcessor.processCommand("Create cd 12345678 0.01 2500");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create checking 12345679 0.01");
		Account to_createdAccount = bank.getAccount("12345679");
		commandProcessor.processCommand("Deposit 12345679 300");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(2500, from_createdAccount.getBalance());
		assertEquals(300, to_createdAccount.getBalance());
	}

	@Test
	void invalid_transfer_between_cd_and_savings() {
		commandProcessor.processCommand("Create cd 12345678 0.01 2500");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create savings 12345679 0.01");
		Account to_createdAccount = bank.getAccount("12345679");
		commandProcessor.processCommand("Deposit 12345679 300");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(2500, from_createdAccount.getBalance());
		assertEquals(300, to_createdAccount.getBalance());
	}

	@Test
	void invalid_transfer_between_checking_and_cd() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		Account from_createdAccount = bank.getAccount("12345678");
		commandProcessor.processCommand("Deposit 12345678 300");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create cd 12345679 0.01 2500");
		Account to_createdAccount = bank.getAccount("12345679");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 200");
		assertEquals(300, from_createdAccount.getBalance());
		assertEquals(2500, to_createdAccount.getBalance());
	}

	@Test
	void over_balance_transfer_between_accounts() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Deposit 12345678 300");
		Account from_createdAccount = bank.getAccount("12345678");
		assertNotNull(from_createdAccount);
		commandProcessor.processCommand("Create checking 12345679 0.01");
		commandProcessor.processCommand("Deposit 12345679 300");
		Account to_createdAccount = bank.getAccount("12345679");
		assertNotNull(to_createdAccount);
		commandProcessor.processCommand("Transfer 12345678 12345679 400");
		assertEquals(0, from_createdAccount.getBalance());
		assertEquals(600, to_createdAccount.getBalance());
	}

	@Test
	void valid_pass_time_delete_account_command() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		commandProcessor.processCommand("Pass 1");
		assertEquals(0, createdAccount.getBalance());
		Account closedAccount = bank.getAccount("12345678");
		assertNull(closedAccount);
	}

	@Test
	void valid_pass_time_interest_calculation_command() {
		commandProcessor.processCommand("Create savings 12345678 1.0");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		commandProcessor.processCommand("Deposit 12345678 100");
		assertEquals(100, createdAccount.getBalance());
		commandProcessor.processCommand("Pass 12");
		// assuming apr is 1%, 1% of 100 is 1, so technically after 12 months
		// there would be an extra dollar in the account
		assertEquals(101.0045960887182, createdAccount.getBalance());
	}

	@Test
	void valid_pass_time_minimum_balance_command() {
		commandProcessor.processCommand("Create savings 12345678 1.0");
		Account createdAccount = bank.getAccount("12345678");
		assertNotNull(createdAccount);
		commandProcessor.processCommand("Deposit 12345678 75");
		commandProcessor.processCommand("Pass 1");
		assertEquals(50.041666666666664, createdAccount.getBalance());
	}

	@Test
	void valid_create_case_insensitive_command() {
		commandProcessor.processCommand("creAte cHecKing 98765432 0.01");
		Account createdAccount = bank.getAccount("98765432");
		assertNotNull(createdAccount);
	}
}
