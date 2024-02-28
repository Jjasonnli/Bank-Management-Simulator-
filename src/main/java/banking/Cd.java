package banking;

import java.time.LocalDate;

public class Cd extends Account {
	private LocalDate creationDate;

	public Cd(String id) {
		super(id, 10.00, 1000.00);
		this.creationDate = LocalDate.now();
	}

	public Cd(String id, double apr, double balance) {
		super(id, apr, balance);
		this.creationDate = LocalDate.now();
	}

	@Override
	public String getAccountType() {
		return "Cd";
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}
}
