package banking;

public class Savings extends Account {
	public Savings(String id) {
		super(id, 5.00, 0.00);
	}

	public Savings(String id, double apr, double balance) {
		super(id, apr, balance);
	}

	@Override
	public String getAccountType() {
		return "Savings";
	}
}
