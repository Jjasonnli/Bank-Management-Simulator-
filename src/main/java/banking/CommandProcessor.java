package banking;

import java.util.Locale;

public class CommandProcessor {
	public Bank bank;
	private CreateCommandHandler createCommandHandler;
	private DepositCommandHandler depositCommandHandler;
	private WithdrawCommandHandler withdrawCommandHandler;
	private TransferCommandHandler transferCommandHandler;
	private PassCommandHandler passCommandHandler;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		createCommandHandler = new CreateCommandHandler();
		depositCommandHandler = new DepositCommandHandler();
		withdrawCommandHandler = new WithdrawCommandHandler();
		transferCommandHandler = new TransferCommandHandler();
		passCommandHandler = new PassCommandHandler();

	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");

		String commandType = parts[0].toLowerCase(Locale.ROOT);

		if (commandType.equals("create")) {
			createCommandHandler.handle(command, bank);
		} else if (commandType.equals("deposit")) {
			depositCommandHandler.handle(command, bank);
		} else if (commandType.equals("withdraw")) {
			withdrawCommandHandler.handle(command, bank);
		} else if (commandType.equals("transfer")) {
			transferCommandHandler.handle(command, bank);
		} else if (commandType.equals("pass")) {
			passCommandHandler.handle(command, bank);
		}
	}

}