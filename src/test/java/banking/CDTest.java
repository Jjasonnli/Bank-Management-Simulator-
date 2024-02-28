package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {

	public static final String CD_ID = "12345678";
	private static final double CD_APR = 10.00;
	Cd cd;

	@BeforeEach
	void setUp() {
		cd = new Cd(CD_ID);
	}

	@Test
	void cd_account_create() {
		assertEquals(CD_ID, cd.getId());
	}

	@Test
	void cd_account_starting_balance() {
		assertEquals(1000, cd.getBalance());
	}

	@Test
	void cd_account_apr() {
		assertEquals(CD_APR, cd.getApr());
	}

	@Test
	void cd_account_deposit() {
		cd.deposit(1.5);
		assertEquals(1001.5, cd.getBalance());
	}

	@Test
	void cd_account_twice_deposit() {
		cd.deposit(1.5);
		cd.deposit(1.5);
		assertEquals(1003, cd.getBalance());
	}

	@Test
	void cd_account_withdraw() {
		cd.withdraw(50);
		assertEquals(950, cd.getBalance());
	}

	@Test
	void cd_account_twice_withdraw() {
		cd.withdraw(50);
		cd.withdraw(2000);
		assertEquals(0, cd.getBalance());
	}
}
