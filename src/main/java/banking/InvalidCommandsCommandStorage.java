package banking;

import java.util.ArrayList;
import java.util.List;

public class InvalidCommandsCommandStorage {
	private List<String> invalidCommands;

	public InvalidCommandsCommandStorage() {
		this.invalidCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		System.out.println("Invalid commands stored: " + invalidCommands);
		return invalidCommands;
	}
}
