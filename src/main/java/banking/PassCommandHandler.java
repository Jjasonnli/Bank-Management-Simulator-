package banking;

public class PassCommandHandler {

	public void handle(String command, Bank bank) {
		String[] parts = command.split(" ");

		int passMonths;

		try {
			passMonths = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			return;
		}

		bank.passTime(passMonths);
	}

	private void processAccountBehavior(Account account, Bank bank) {
		// Check if the account balance is $0 and close the account
		if (account.getBalance() == 0.0) {
			// Close the account (implement the closeAccount method in the Bank class)
			bank.closeAccount(account.getId());
			return; // Stop processing this account after closing it
		}

		// Check if the account balance is below $100 and deduct $25 (minimum balance
		// fee)
		if (account.getBalance() < 100.0) {
			// Deduct $25 from the account (implement the withdraw method in the Bank class)
			bank.withdraw(account.getId(), 25);
		}

		// Calculate and accrue APR for the account
		double apr = account.calculateAPR(); // returns interest
		double interest = apr;
		// Add the interest to the account (implement the deposit method in the Bank
		// class)
		bank.deposit(account.getId(), interest);
	}
}
