package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input;
	Bank bank;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank("Jasons banking.Bank");
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank), new Bank("Jason"));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 1.0", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	void invalid_to_create_accounts_with_same_ID() {
		input.add("Create checking 12345678 2.0");
		input.add("Create checking 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 2.00", actual.get(0));
		assertEquals("Create checking 12345678 1.0", actual.get(1));

	}

	@Test
	void sample() {
		input.add("Create savings 12345678 0.06");
		input.add("Deposit 12345678 500");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 500.00 0.06", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));

	}

	@Test
	void sample2() {
		input.add("Create savings 12345678 0.06");
		input.add("Deposit 12345678 500");
		input.add("Create savings 12345679 0.06");
		input.add("Deposit 12345679 500");
		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 500.00 0.06", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Savings 12345679 500.00 0.06", actual.get(2));
		assertEquals("Deposit 12345679 500", actual.get(3));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000"); // invalid
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	void sample3() {
		input.add("Create savings 12345678 1.0");
		input.add("Deposit 12345678 1000");
		input.add("Pass 12");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 1010.04 1.00", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));

	}

	@Test
	void sample4() {
		input.add("Create cd 12345678 0.6 1000");
		input.add("Pass 1");
		List<String> actual = masterControl.start(input);
		assertEquals("Cd 12345678 1000.50 0.60", actual.get(0));

	}

	@Test
	void sample_saving_withdraw() {
		input.add("Create savings 12345678 0.00");
		input.add("Deposit 12345678 1000");
		input.add("Withdraw 12345678 100");
		input.add("Withdraw 12345678 200");
		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 900.00 0.00", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
		assertEquals("Withdraw 12345678 200", actual.get(3));
	}

	@Test
	void sample_saving_withdraw2() {
		input.add("Create savings 12345678 0.00");
		input.add("Deposit 12345678 1000");
		input.add("Withdraw 12345678 100");
		input.add("Withdraw 12345678 200");
		input.add("Pass 1");
		input.add("Withdraw 12345678 500");
		List<String> actual = masterControl.start(input);
		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 400.00 0.00", actual.get(0));
		assertEquals("Deposit 12345678 1000", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
		assertEquals("Withdraw 12345678 500", actual.get(3));
		assertEquals("Withdraw 12345678 200", actual.get(4));
	}

	@Test
	void sample_cd_withdraw() {
		input.add("Create cd 12345678 0.00 2500");
		input.add("Withdraw 12345678 100");
		input.add("Withdraw 123345678 2500");
		input.add("Pass 6");
		input.add("Withdraw 12345678 200");
		input.add("Pass 6");
		input.add("Withdraw 12345678 2500");
		List<String> actual = masterControl.start(input);
		assertEquals(5, actual.size());
		assertEquals("Cd 12345678 0.00 0.00", actual.get(0));
		assertEquals("Withdraw 12345678 2500", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
		assertEquals("Withdraw 123345678 2500", actual.get(3));
		assertEquals("Withdraw 12345678 200", actual.get(4));

	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

}
