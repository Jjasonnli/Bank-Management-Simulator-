package banking;

import java.util.List;

public class MasterControl {

	private Bank bank;
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage, Bank bank) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
		this.bank = bank;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validateCommand(command)) {
				commandProcessor.processCommand(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}
		return commandStorage.getOutput();
	}

}
