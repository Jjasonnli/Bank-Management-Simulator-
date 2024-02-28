package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {

	public static final String CHECKING_ID = "14548692";
	private static final double CHECKING_APR = 3.00;

	Checking checking;

	@BeforeEach
	void setUp() {
		checking = new Checking(CHECKING_ID);
	}

	@Test
	void checking_account_create() {
		assertEquals(CHECKING_ID, checking.getId());
	}

	@Test
	void checking_account_starting_balance() {
		assertEquals(0, checking.getBalance());
	}

	@Test
	void checking_account_apr() {
		assertEquals(CHECKING_APR, checking.getApr());
	}

	@Test
	void checking_account_deposit() {
		checking.deposit(1.5);
		assertEquals(1.5, checking.getBalance());
	}

	@Test
	void checking_account_twice_deposit() {
		checking.deposit(1.5);
		checking.deposit(1.5);
		assertEquals(3, checking.getBalance());
	}

	@Test
	void checking_account_withdraw() {
		checking.deposit(100);
		checking.withdraw(50);
		assertEquals(50, checking.getBalance());
	}

	@Test
	void checking_account_twice_withdraw() {
		checking.deposit(100);
		checking.withdraw(50);
		checking.withdraw(50);
		assertEquals(0, checking.getBalance());
	}

}
