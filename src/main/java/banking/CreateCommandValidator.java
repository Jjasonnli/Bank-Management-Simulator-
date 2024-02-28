package banking;

import java.util.Locale;

public class CreateCommandValidator {

	public boolean validate(String command, Bank bank) {
		String[] parts = command.split(" ");
		String accountType = parts[1].toLowerCase(Locale.ROOT);
		String accountId = parts[2];

		if (bank.accountExistsByQuickID(accountId)) {
			return false; // banking.Account already exists
		}

		if (accountType.equals("savings")) {
			if (parts.length != 4) {
				return false; // saving starts with 0 balance part[5] is balance
			}
		}

		if (accountType.equals("checking")) {
			if (parts.length != 4) {
				return false; // checking start with 0 balance part[5] is balance
			}
		}

		if (accountType.equals("cd")) {
			if (parts.length != 5) {
				return false; // Invalid number of parts for CD account
			}

			double apr = Double.parseDouble(parts[3]);
			double balance = Double.parseDouble(parts[4]);

			if (apr < 0.0 || apr > 10.0 || balance < 1000.0 || balance > 10000.0) {
				return false; // Invalid APR or CD balance
			}
		} else {
			if (!isDouble(parts[3]) || Double.parseDouble(parts[3]) < 0.0 || Double.parseDouble(parts[3]) > 10.0) {
				return false; // Invalid initial balance or APR
			}
		}

		return true; // If all checks pass, it's a valid command
	}

	private boolean isDouble(String part) {
		try {
			Double.parseDouble(part);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
