package banking;

import java.util.List;

public class CommandStorage {
	private Bank bank;
	private AccountStateCommandStorage accountStateCommandStorage;

	public CommandStorage(Bank bank) {
		this.bank = bank;
		this.accountStateCommandStorage = new AccountStateCommandStorage(bank);
	}

	public void addInvalidCommand(String command) {
		accountStateCommandStorage.addInvalidCommand(command);
	}

	public List<String> getOutput() {
		return accountStateCommandStorage.getOutput();
	}

}
