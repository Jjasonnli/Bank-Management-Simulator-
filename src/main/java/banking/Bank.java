package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
	private String name;
	private Map<String, Account> accounts;

	public Bank(String name) {
		this.name = name;
		accounts = new HashMap<>();

	}

	public String getName() {
		return name;
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String accountId, Account account) {
		accounts.put(accountId, account);
	}

	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	public Account retrieveAccountByID(String ID) {
		return accounts.get(ID);
	}

	public void deposit(String accountId, double amount) {
		// Get the account associated with the provided accountId
		Account account = accounts.get(accountId);

		// Check if the account actually exists
		if (account != null) {
			// Deposit the amount into the account
			account.deposit(amount);
		}
	}

	public void withdraw(String accountId, double amount) {
		Account account = accounts.get(accountId);
		if (account != null) {
			account.withdraw(amount);
		}
	}

	public boolean accountExistsByQuickID(String accountId) {
		return accounts.containsKey(accountId);
	}

	public void closeAccount(String id) {
		accounts.remove(id);

	}

	public void passTime(int months) {
		List<Account> allAccounts = new ArrayList<>(accounts.values());
		for (int i = 0; i < months; i++) {
			// Iterate through each account in the bank
			for (Account account : allAccounts) {
				// Increment the age for Savings accounts
				if (account instanceof Savings) {
					account.setMonths(0);
				}
				// Increment the age for CD accounts
				else if (account instanceof Cd) {
					account.incrementMonthsPassed();
				}
				// Process the behaviors for each month
				processAccountBehavior(account, this); // Pass the bank as a parameter
				// commandStorage.addAccountState(account);
			}
		}
	}

	private void processAccountBehavior(Account account, Bank bank) {
		// Check if the account balance is $0 and close the account
		if (account.getBalance() == 0.0) {
			bank.closeAccount(account.getId());
			return;
		}
		// Check if the account balance is below $100 and deduct $25 (minimum balance
		// fee)
		if (account.getBalance() < 100.0) {
			// Deduct $25 from the account (implement the withdraw method in the Bank class)
			bank.withdraw(account.getId(), 25);
		}
		// Calculate and accrue APR for the account
		double apr = account.calculateAPR(); // returns interest
		double interest = apr;
		// Add the interest to the account (implement the deposit method in the Bank
		// class)
		bank.deposit(account.getId(), interest);
	}

}