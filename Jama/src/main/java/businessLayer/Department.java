package businessLayer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints=
            @UniqueConstraint(columnNames={"code"})
    )
@NamedQueries({ @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d") ,
@NamedQuery(name="Department.findByCode", query="SELECT d FROM Department d where d.code = :code")})
public class Department {

	@Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;

	private String rateDirectory;
	private String code; // XXX pattern?
	private String name;

	public String getRateDirectory() {
		return rateDirectory;
	}

	public void setRateDirectory(String rateDirectory) {
		this.rateDirectory = rateDirectory;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", code=" + code + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getCompleteName(){
		return this.code;
	}

}
