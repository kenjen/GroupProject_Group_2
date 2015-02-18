package com.project.entities;

import java.io.Serializable;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

@NamedQueries({ @NamedQuery(name = "ErrorBaseData.getAllErrorBaseData", query = "select e from ErrorBaseData e"), })
@Entity
@Table(name = "error_base_data")
public class ErrorBaseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String date;
	private String eventId;
	private String failureClass;
	// UE Type
	private String tac;
	// Market
	private String mnc;
	// Operator
	private String mcc;
	private String cellId;
	private String duration;
	private String causeCode;
	private String neVersion;
	private String imsi;
	private String hier3Id;
	private String hier32Id;
	private String hier321Id;


	public ErrorBaseData(Row row) {
		Iterator<Cell> i = row.cellIterator();
		Cell cell = i.next();
		this.date = cell.toString();
		cell = i.next();
		this.eventId = cell.toString();
		cell = i.next();
		this.failureClass = cell.toString();
		cell = i.next();
		this.tac = cell.toString();
		cell = i.next();
		this.mnc = cell.toString();
		cell = i.next();
		this.mcc = cell.toString();
		cell = i.next();
		this.cellId = cell.toString();
		cell = i.next();
		this.duration = cell.toString();
		cell = i.next();
		this.causeCode = cell.toString();
		cell = i.next();
		this.neVersion = cell.toString();
		cell = i.next();
		this.imsi = cell.toString();
		cell = i.next();
		this.hier3Id = cell.toString();
		cell = i.next();
		this.hier32Id = cell.toString();
		cell = i.next();
		this.hier321Id = cell.toString();
	}

	public ErrorBaseData() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "event_id")
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	@Column(name = "failure_class")
	public String getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(String failureClass) {
		this.failureClass = failureClass;
	}

	@Column(name = "tac")
	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	@Column(name = "mnc")
	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	@Column(name = "mcc")
	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	@Column(name = "cell_id")
	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	@Column(name = "duration")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "cause_code")
	public String getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}

	@Column(name = "ne_version")
	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	@Column(name = "imsi")
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@Column(name = "hier3_id")
	public String getHier3Id() {
		return hier3Id;
	}

	public void setHier3Id(String hier3Id) {
		this.hier3Id = hier3Id;
	}

	@Column(name = "hier32_id")
	public String getHier32Id() {
		return hier32Id;
	}

	public void setHier32Id(String hier32Id) {
		this.hier32Id = hier32Id;
	}

	@Column(name = "hier321_id")
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