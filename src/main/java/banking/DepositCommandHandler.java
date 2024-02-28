package banking;

public class DepositCommandHandler {
	public void handle(String command, Bank bank) {
		String[] parts = command.split(" ");
		String accountId = parts[1];
		double depositAmount = Double.parseDouble(parts[2]);

		// Check if an account with the specified ID exists
		if (bank.accountExistsByQuickID(accountId)) {
			// Deposit the money into the account
			bank.deposit(accountId, depositAmount);

			// Get the reference to the account
			Account account = bank.getAccount(accountId);

			// Add the command to the account's command storage
			account.storeCommand(command);

			System.out.println("Deposited $" + depositAmount + " into account " + accountId);
		} else {
			System.out.println("banking.Account " + accountId + " does not exist.");
		}
	}
}
