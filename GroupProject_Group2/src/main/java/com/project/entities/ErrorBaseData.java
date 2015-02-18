package com.project.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "ErrorBaseData.getAllErrorBaseData", query = "select e from ErrorBaseData e"), })
@Entity
@Table(name = "error_base_data")
public class ErrorBaseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "date")
	private String date;
	@Column(name = "event_id")
	private String eventId;
	@Column(name = "failure_class")
	private String failureClass;
	@Column(name = "tac")
	// UE Type
	private String tac;
	@Column(name = "mnc")
	// Market
	private String mnc;
	@Column(name = "mcc")
	// Operator
	private String mcc;
	@Column(name = "cell_id")
	private String cellId;
	@Column(name = "duration")
	private String duration;
	@Column(name = "cause_code")
	private String causeCode;
	@Column(name = "ne_version")
	private String neVersion;
	@Column(name = "imsi")
	private String imsi;
	@Column(name = "hier3_id")
	private String hier3Id;
	@Column(name = "hier32_id")
	private String hier32Id;
	@Column(name = "hier321_id")
	private String hier321Id;

	public ErrorBaseData(String date, String eventId, String failureClass,
			String tac, String mnc, String mcc, String cellId,
			String duration, String causeCode, String neVersion, String imsi,
			String hier3Id, String hier32Id, String hier321Id) {
		this.date = date;
		this.eventId = eventId;
		this.failureClass = failureClass;
		this.tac = tac;
		this.mnc = mnc;
		this.mcc = mcc;
		this.cellId = cellId;
		this.duration = duration;
		this.causeCode = causeCode;
		this.neVersion = neVersion;
		this.imsi = imsi;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
	}

	public ErrorBaseData() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(String failureClass) {
		this.failureClass = failureClass;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}

	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getHier3Id() {
		return hier3Id;
	}

	public void setHier3Id(String hier3Id) {
		this.hier3Id = hier3Id;
	}

	public String getHier32Id() {
		return hier32Id;
	}

	public void setHier32Id(String hier32Id) {
		this.hier32Id = hier32Id;
	}

	public String getHier321Id() {
		return hier321Id;
	}

	public void setHier321Id(String hier321Id) {
		this.hier321Id = hier321Id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "id: " + getId() + "\ndate: " + getDate() + "\neventId: "
				+ getEventId() + "\nfailureClass: " + getFailureClass()
				+ "\ntac: " + getTac() + "\nmnc: " + getMnc() + "\nmcc: "
				+ getMcc() + "\ncellId: " + getCellId() + "\nduration: "
				+ getDuration() + "\ncauseCode: " + getCauseCode()
				+ "\nneVersion: " + getNeVersion() + "\nimsi: " + getImsi()
				+ "\nhier3Id: " + getHier3Id() + "\nhier32Id: " + getHier32Id()
				+ "\nhier321Id: " + getHier321Id() + "\n";
	}
}