package is.hi.travel_planer.model;

public class User {
	private String name;
	private String email;
	private String ssn;
	private String phone;

	public User(String name, String email, String ssn, String phone) {
		this.name = name;
		this.email = email;
		this.ssn = ssn;
		this.phone = phone;
	}

	public String getName() { return this.name; }
	public String getEmail() { return this.email; }
	public String getPhone() { return this.phone; }
	public String getSSN() { return this.ssn; }
}
