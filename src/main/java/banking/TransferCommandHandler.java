package banking;

public class TransferCommandHandler {

	public void handle(String command, Bank bank) {
		String[] parts = command.split(" ");

		String from_accountId = parts[1];
		String to_accountId = parts[2];

		double transferAmount = Double.parseDouble(parts[3]);
		Account from_account = bank.getAccount(from_accountId);
		if (from_account == null) {
			return;
		}
		double money_in_from_account = from_account.getBalance();
		Account to_account = bank.getAccount(to_accountId);
		if (to_account == null) {
			return;
		}

		if (!(from_account instanceof Cd) && !(to_account instanceof Cd)) {
			bank.withdraw(from_accountId, transferAmount);
			// withdraw from from_id, and deposit to to_id

			// if wanted transfer amount is greater than money in the account
			// transfer all the available money in the "from account"
			// if not, do the regular transfer/deposit
			if (transferAmount > money_in_from_account) {
				bank.deposit(to_accountId, money_in_from_account);
				from_account.storeCommand(command);
				to_account.storeCommand(command);
			} else {
				bank.deposit(to_accountId, transferAmount);
				from_account.storeCommand(command);
				to_account.storeCommand(command);
			}
		}
	}
}
