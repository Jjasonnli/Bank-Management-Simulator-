package banking;

import java.util.Locale;

public class DepositCommandValidator {

	public boolean validate(String command, Bank bank) {
		String[] parts = command.split(" ");

		if (parts.length != 3) {
			return false; // Invalid number of parts in the command
		}

		String commandType = parts[0].toLowerCase(Locale.ROOT);
		String accountId = parts[1];

		if (!commandType.equals("deposit")) {
			return false; // Invalid command type
		}

		double depositAmount;

		try {
			depositAmount = Double.parseDouble(parts[2]);
		} catch (NumberFormatException e) {
			return false; // Invalid deposit amount
		}

		if (!bank.accountExistsByQuickID(accountId)) {
			return false; // Account doesn't exist
		}

		// Check the account type and apply specific deposit limits
		if (bank.getAccount(accountId) instanceof Checking) {
			if (depositAmount > 1000.00) {
				return false; // Checking account deposit exceeds limit
			}
		} else if (bank.getAccount(accountId) instanceof Savings) {
			if (depositAmount > 2500.00) {
				return false; // Savings account deposit exceeds limit
			}
		} else if (bank.getAccount(accountId) instanceof Cd) {
			return false; // Cannot deposit into CD accounts
		}

		return true; // Valid deposit command
	}
}
