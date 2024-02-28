package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingTest {

	public static final String SAVINGS_ID = "87654321";
	private static final double SAVINGS_APR = 5.00;

	Savings savings;

	@BeforeEach
	void setUp() {
		savings = new Savings(SAVINGS_ID);
	}

	@Test
	void savings_account_create() {
		assertEquals(SAVINGS_ID, savings.getId());
	}

	@Test
	void savings_account_starting_balance() {
		assertEquals(0, savings.getBalance());
	}

	@Test
	void saving_account_apr() {
		assertEquals(SAVINGS_APR, savings.getApr());
	}

	@Test
	void saving_account_deposit() {
		savings.deposit(1.5);
		assertEquals(1.5, savings.getBalance());
	}

	@Test
	void saving_account_twice_deposit() {
		savings.deposit(1.5);
		savings.deposit(1.5);
		assertEquals(3, savings.getBalance());
	}

	@Test
	void saving_account_withdraw() {
		savings.deposit(100);
		savings.withdraw(50);
		assertEquals(50, savings.getBalance());
	}

	@Test
	void saving_account_twice_withdraw() {
		savings.deposit(100);
		savings.withdraw(50);
		savings.withdraw(100);
		assertEquals(0, savings.getBalance());
	}
}
