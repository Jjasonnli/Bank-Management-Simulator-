package banking;

import java.util.ArrayList;

public abstract class Account {
	ArrayList<String> validCommandsStorage;
	int age = 0;
	private String id;
	private double apr;
	private double balance;

	public Account(String id, double apr, double balance) {
		this.id = id;
		this.apr = apr;
		this.balance = balance;
		this.validCommandsStorage = new ArrayList();

	}

	public int getMonths() {
		return age;
	}

	public void setMonths(int months) {
		this.age = months;
	}

	public void incrementMonthsPassed() {
		this.age += 1;
	}

	public void storeCommand(String command) {
		if (validCommandsStorage.add(command)) {
			System.out.println("Command stored successfully." + command);
		} else {
			System.out.println("Failed to store command." + command);
		}
	}

	public ArrayList<String> getValidCommands() {
		return validCommandsStorage;
	}

	public String getId() {
		return id;
	}

	public double getApr() {
		return apr;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (balance - amount >= 0) {
			balance -= amount;
		} else {
			balance = 0;
		}
	}

	public abstract String getAccountType();

	public double calculateAPR() {
		// divide by 100 to get percentage
		// monthly rate
		double monthlyRate = apr / 1200.00;
		if (this instanceof Cd) {
			// For CD accounts, calculate interest 4 times per month
			for (int i = 0; i < 4; i++) {
				return (balance * monthlyRate);
			}
			return 0;
		} else {
			// For other accounts (Savings, Checking), calculate interest once per month
			return (balance * monthlyRate);
		}
	}

}
