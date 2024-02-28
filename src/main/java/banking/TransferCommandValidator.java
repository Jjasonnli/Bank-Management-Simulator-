package banking;

public class TransferCommandValidator {

	public boolean validate(String command, Bank bank) {
		String[] parts = command.split(" ");

		String from_accountId = parts[1];
		String to_accountId = parts[2];
		double transferAmount;

		try {
			transferAmount = Double.parseDouble(parts[3]);
		} catch (NumberFormatException e) {
			return false; // Invalid transfer amount
		}

		if (!bank.accountExistsByQuickID(from_accountId) || !bank.accountExistsByQuickID(to_accountId)) {
			return false; // From account or To Account doesn't exist
		}

		Account from_account = bank.getAccount(from_accountId);
		Account to_account = bank.getAccount(to_accountId);

		if (from_accountId.equals(to_accountId)) {
			return false; // Can't transfer from and to the same account
		}

		if (from_account instanceof Checking || from_account instanceof Savings) {
			return true;
		} else if (to_account instanceof Checking || to_account instanceof Savings) {
			return true;
		} else if (from_account instanceof Cd || to_account instanceof Cd) {
			return false; // Can't transfer to or from CD accounts
		}
		return true; // valid transfer command
	}
}