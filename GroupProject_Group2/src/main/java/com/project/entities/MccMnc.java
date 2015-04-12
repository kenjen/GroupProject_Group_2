package com.project.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "MccMnc.getAllMccMnc", query = "select m from MccMnc m"), })
@Entity
@Table(name = "mcc_mnc")
public class MccMnc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "mcc")
	private Integer mcc;
	@Column(name = "mnc")
	private Integer mnc;
	@Column(name = "country")
	private String country;
	@Column(name = "operator")
	private String operator;

	public MccMnc() {
	}

	public MccMnc(Integer mcc, Integer mnc, String country, String operator) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.country = country;
		this.operator = operator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mcc == null) ? 0 : mcc.hashCode());
		result = prime * result + ((mnc == null) ? 0 : mnc.hashCode());
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
		MccMnc other = (MccMnc) obj;
		if (mcc == null) {
			if (other.mcc != null)
				return false;
		} else if (!mcc.equals(other.mcc))
			return false;
		if (mnc == null) {
			if (other.mnc != null)
				return false;
		} else if (!mnc.equals(other.mnc))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getMnc() {
		return mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "id: " + getId() + "\nmcc: " + getMcc() + "\nmnc: " + getMnc()
				+ "\ncountry: " + getCountry() + "\noperator: " + getOperator()
				+ "\n";
	}
}
