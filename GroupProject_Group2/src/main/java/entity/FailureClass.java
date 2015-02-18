package entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
		@NamedQuery(name = "FailureClass.findAll", query = "select o from FailureClass o"),
		@NamedQuery(name = "FailureClass.findByID", query = "select o from FailureClass o where o.id=:id"), })
@Entity
public class FailureClass {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int failureClass;
	private String description;

	public FailureClass() {

	}

	public FailureClass(int failureClass, String desc) {
		super();
		this.failureClass = failureClass;
		this.description = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(int failureClass) {
		this.failureClass = failureClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
