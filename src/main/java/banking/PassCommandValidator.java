package banking;

public class PassCommandValidator {

	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (parts.length != 2) {
			return false;
		}

		int passMonths; // should be whole numbers

		try {
			passMonths = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			return false; // Invalid pass months
		}

		// Check if passMonths is within the valid range (1 to 61)
		return passMonths >= 1 && passMonths <= 61;
	}
}
