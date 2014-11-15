package beans;

public class Users {

	private String username;
	private String password;
	private String name;
	private String lastname;
	private String email;
	private String titles;
	
	public Users(String username, String password, String name,
			String lastname, String titles, String email) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.titles = titles;
	}

	public Users(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	

	public Users(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password
				+ ", name=" + name + ", lastname=" + lastname + ", email="
				+ email + "]";
	}
	
	
}
