package banking;

public class WithdrawCommandHandler {

	public void handle(String command, Bank bank) {
		String[] parts = command.split(" ");
		String accountId = parts[1];
		double withdrawAmount = Double.parseDouble(parts[2]);

		if (bank.accountExistsByQuickID(accountId)) {
			Account account = bank.getAccount(accountId);
			double currentBalance = account.getBalance();

			if (account instanceof Checking) {
				if (withdrawAmount > 400.00) {
					bank.withdraw(accountId, 400); // limit can't go over
				}
				if (withdrawAmount > currentBalance) {
					bank.withdraw(accountId, currentBalance);
				} else {
					bank.withdraw(accountId, withdrawAmount);
					System.out.println("Withdraw $" + withdrawAmount + " from account " + accountId);

					// Add the command to the account's command storage
					account.storeCommand(command);
				}
			} else if (account instanceof Savings) {
				if (withdrawAmount > 1000.00) {
					bank.withdraw(accountId, 1000); // limit can't go over
				}
				if (withdrawAmount > currentBalance) {
					bank.withdraw(accountId, currentBalance);
				} else {
					bank.withdraw(accountId, withdrawAmount);
					System.out.println("Withdraw $" + withdrawAmount + " from account " + accountId);

					// Add the command to the account's command storage
					account.storeCommand(command);
				}
			} else if (account instanceof Cd) {
				account.withdraw(currentBalance);
				System.out.println("Withdraw $" + withdrawAmount + " from account " + accountId);
				account.storeCommand(command);
			}
		} else {
			System.out.println("banking.Account " + accountId + " does not exist.");
		}
	}
}
