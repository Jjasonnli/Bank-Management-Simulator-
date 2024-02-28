package banking;

public class Checking extends Account {
	public Checking(String id) {
		super(id, 3.00, 0.00);
	}

	public Checking(String id, double apr, double balance) {
		super(id, apr, balance);
	}

	@Override
	public String getAccountType() {
		return "Checking";
	}

}
