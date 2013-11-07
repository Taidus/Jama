package usersManagement;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.resource.spi.IllegalStateException;

import businessLayer.Department;
import util.Encryptor;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@Table(
        uniqueConstraints=
            @UniqueConstraint(columnNames={"serialNumber"})
    )
@NamedQueries({ @NamedQuery(name = "User.findBySerialNumber", query = "SELECT u FROM User U where u.serialNumber= :number ") })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private byte[] password;
	private String email;
	private String name;
	private String surname;
	
	//molti o uno solo???????
	@ManyToMany
	private List<Department> belongingDepths;

	private String serialNumber;
	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
		super();
		belongingDepths = new ArrayList<>();
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

	public void setPassword(String password) throws GeneralSecurityException {
		this.password = Encryptor.encrypt(password);
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

	public List<Department> getBelongingDepths() {
		return belongingDepths;
	}

	public void setBelongingDepths(List<Department> belongingDepths) {
		this.belongingDepths = belongingDepths;
	}
	
	public void addDepartment(Department d){
		belongingDepths.add(d);
	}
	
	public List<String> getBelongingDepthsCodes(){
		List<String> result = new ArrayList<>();
		for(Department d : belongingDepths){
			result.add(d.getCode());
		}
		return result;
	}

	public boolean login(String password) throws IllegalStateException {

		try {

			byte[] encrypted = Encryptor.encrypt(password);
			// XXX nn so perchè equals su array di byte nn funziona!
			return new String(encrypted).equals(new String(this.password));

		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("E' avvenuto un errore durante il controllo della password causato dal Cypher");
		}

	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", serialNumber="
				+ serialNumber + ", role=" + role + "]";
	}

}
