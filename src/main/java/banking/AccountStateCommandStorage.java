package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountStateCommandStorage {

	private List<String> commands;
	private Bank bank;
	private InvalidCommandsCommandStorage invalidCommandsCommandStorage;

	public AccountStateCommandStorage(Bank bank) {
		this.bank = bank;
		this.commands = new ArrayList<>();
		this.invalidCommandsCommandStorage = new InvalidCommandsCommandStorage();
	}

	public List<String> getOutput() {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.FLOOR);
		for (Map.Entry<String, Account> entry : bank.getAccounts().entrySet()) {
			Account account = entry.getValue();
			processAccount(account, df);
		}
		// Add invalid commands
		commands.addAll(getInvalidCommands());
		return commands;
	}

	private void processAccount(Account account, DecimalFormat df) {
		// Truncate balance to two decimal places (rounding down)
		double roundedBalance = Math.floor(account.getBalance() * 100) / 100;

		// Truncate APR to two decimal places (rounding down)
		double roundedApr = Math.floor(account.getApr() * 100) / 100;

		// Create account state
		String accountState = capitalize(account.getAccountType()) + " " + account.getId() + " "
				+ df.format(roundedBalance) + " " + df.format(roundedApr);

		System.out.println("Account state stored: " + accountState);

		// Add account state to commands
		commands.add(accountState);

		// Add both valid and invalid transaction history to commands
		for (String transaction : account.getValidCommands()) {
			commands.add(transaction);
		}
	}

	private String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public List<String> getInvalidCommands() {
		return invalidCommandsCommandStorage.getInvalidCommands();
	}

	public void addInvalidCommand(String command) {
		invalidCommandsCommandStorage.addInvalidCommand(command);
	}
}