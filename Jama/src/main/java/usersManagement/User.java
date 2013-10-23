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
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private int id;
	private String password;
	
	private int serialNumber;
	@Enumerated(EnumType.STRING)
	private Role role;


	public User() {
		super();
	}
	
	public boolean hasRole(Role role){
		if(role == this.role){
			return true;
		}
		else{
			return false;
		}
	}


	public int getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(int serialNumber) {
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

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", serialNumber="
				+ serialNumber + ", role=" + role + "]";
	}
	
	
	
	
	
	
	
	
	
	
   
}