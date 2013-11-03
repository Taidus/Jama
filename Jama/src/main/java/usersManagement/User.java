package usersManagement;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "User.findBySerialNumber", query = "SELECT u FROM User U where u.serialNumber= :number ") })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String password;
	private String email;
	private String name;
	private String surname;

	private String serialNumber;
	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
		super();
	}

	public boolean hasRole(Role role) {
		return (role == this.role) ? true : false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getId() {
		return id;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean login(String password) {
		// TODO: mettere encryption
		return this.password.equals(password);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", serialNumber="
				+ serialNumber + ", role=" + role + "]";
	}

}
