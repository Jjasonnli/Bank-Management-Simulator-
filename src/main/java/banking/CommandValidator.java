package banking;

import java.util.Locale;

public class CommandValidator {

	private Bank bank;
	private CreateCommandValidator createCommandValidator;
	private DepositCommandValidator depositCommandValidator;
	private WithdrawCommandValidator withdrawCommandValidator;
	private TransferCommandValidator transferCommandValidator;

	private PassCommandValidator passCommandValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		createCommandValidator = new CreateCommandValidator();
		depositCommandValidator = new DepositCommandValidator();
		withdrawCommandValidator = new WithdrawCommandValidator();
		transferCommandValidator = new TransferCommandValidator();
		passCommandValidator = new PassCommandValidator();
	}

	public boolean validate(String command) {
		// Split the command into parts based on spaces
		String[] parts = command.split(" ");

		// create savings 12345678 0.01(apr)
		// create cd 12345678 0.01(apr) 1000(balance)
		if (parts.length != 2 && parts.length != 3 && parts.length != 4 && parts.length != 5) {
			return false; // The command should have exactly 2, 3, 4 or 5 parts
		}

		String commandType = parts[0].toLowerCase(Locale.ROOT);

		// Ensure that commandType is "create" or "deposit"
		if (!commandType.equals("create") && !commandType.equals("deposit") && !commandType.equals("withdraw")
				&& !commandType.equals("transfer") && !commandType.equals("pass")) {
			return false;
		}

		if (commandType.equals("create")) {
			return createCommandValidator.validate(command, bank);
		} else if (commandType.equals("deposit")) {
			return depositCommandValidator.validate(command, bank);
		} else if (commandType.equals("withdraw")) {
			return withdrawCommandValidator.validate(command, bank);
		} else if (commandType.equals("transfer")) {
			return transferCommandValidator.validate(command, bank);
		} else if (commandType.equals("pass")) {
			return passCommandValidator.validate(command);
		}
		return false;
	}

	public boolean validateCommand(String command) {
		return validate(command);
	}
}
