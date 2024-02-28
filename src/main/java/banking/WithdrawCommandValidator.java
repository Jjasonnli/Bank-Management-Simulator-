package banking;

public class WithdrawCommandValidator {
	public boolean validate(String command, Bank bank) {
		String[] parts = command.split(" ");

		if (parts.length != 3) {
			return false; // Invalid number of parts in the command
		}

		String accountId = parts[1];
		double withdrawAmount;

		try {
			withdrawAmount = Double.parseDouble(parts[2]);
		} catch (NumberFormatException e) {
			return false; // Invalid withdraw amount
		}

		if (!bank.accountExistsByQuickID(accountId)) {
			return false; // Account doesn't exist
		}

		// Retrieve the account and check if there is sufficient balance for withdrawal
		Account account = bank.getAccount(accountId);
		double currentBalance = account.getBalance();

		if (account instanceof Checking) {
			if (withdrawAmount > 400.00) {
				return false; // Checking account withdrawal exceeds limit
			}
		} else if (account instanceof Savings) {
			// Check if more than one withdrawal is attempted in a month
			if (account.getMonths() > 0) {
				return false; // Savings account already had a withdrawal this month
			}
			// Check if withdrawal amount exceeds limit
			if (withdrawAmount > 1000.00) {
				return false; // Savings account withdrawal exceeds limit
			}
		} else if (account instanceof Cd) {
			// Check if 12 months have passed
			if (account.getMonths() >= 12) {
				return true; // CD account has not reached 12 months
			} else
			// Check if the withdrawal amount is less than the total balance in the CD
			if (withdrawAmount <= currentBalance) {
				return false; // Invalid withdrawal amount for CD account
			}
		}

		// Update the month count for Savings account (if applicable)
		if (account instanceof Savings) {
			account.setMonths(1);
		}

		// Valid withdrawal command
		return true;
	}
}
