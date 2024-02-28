package banking;

import java.util.Locale;

public class CreateCommandHandler {

	public void handle(String command, Bank bank) {
		String[] parts = command.split(" ");
		parts[0] = parts[0].toLowerCase(Locale.ROOT);

		if (validateCreateCommand(parts)) {
			String accountType = parts[1].toLowerCase(Locale.ROOT);
			String accountId = parts[2];
			double apr = Double.parseDouble(parts[3]);

			if (accountType.equals("checking") || accountType.equals("savings")) {
				createCheckingOrSavingAccount(accountId, apr, bank, accountType);
			} else if (accountType.equals("cd") && parts.length == 5) {
				double balance = Double.parseDouble(parts[4]);
				createCdAccount(accountId, apr, balance, bank);
			}
		}
	}

	private boolean validateCreateCommand(String[] parts) {
		return (parts.length == 4 && (parts[1].equalsIgnoreCase("checking") || parts[1].equalsIgnoreCase("savings")))
				|| (parts.length == 5 && parts[1].equalsIgnoreCase("cd"));
	}

	private void createCheckingOrSavingAccount(String accountId, double apr, Bank bank, String accountType) {
		if (!bank.accountExistsByQuickID(accountId)) {
			if (isValidApr(apr)) {
				if (accountType.equals("checking")) {
					Checking checking = new Checking(accountId, apr, 0.0);
					bank.addAccount(accountId, checking);
					// accountStateCommandStorage.addAccountState(checking); // Add account state
					// for checking account
					System.out.println("Checking account created with ID " + accountId);
				} else if (accountType.equals("savings")) {
					Savings saving = new Savings(accountId, apr, 0.0);
					bank.addAccount(accountId, saving);
					// accountStateCommandStorage.addAccountState(saving); // Add account state for
					// savings account
					System.out.println("Savings account created with ID " + accountId);
				}
			}
		}
	}

	private void createCdAccount(String accountId, double apr, double balance, Bank bank) {
		if (!bank.accountExistsByQuickID(accountId)) {
			if (isValidApr(apr) && isValidCdBalance(balance)) {
				Cd cd = new Cd(accountId, apr, balance);
				bank.addAccount(accountId, cd);
				// accountStateCommandStorage.addAccountState(cd); // Add account state for CD
				// account
				System.out.println("CD account created with ID " + accountId);
			}
		}
	}

	private boolean isValidApr(double apr) {
		return apr >= 0.0 && apr <= 10.0;
	}

	private boolean isValidCdBalance(double balance) {
		return balance >= 1000.0 && balance <= 10000.0;
	}
}
