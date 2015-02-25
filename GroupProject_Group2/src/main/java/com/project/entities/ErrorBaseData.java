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
	
	private Integer id;
	private Date date;
	private Integer eventId;
	private Integer failureClass;
	// UE Type
	private Integer tac;
	// Market
	private Integer mnc;
	// Operator
	private Integer mcc;
	private Integer cellId;
	private Integer duration;
	private Integer causeCode;
	private String neVersion;
	private Long imsi;
	private String hier3Id;
	private String hier32Id;
	private String hier321Id;


	public ErrorBaseData(BaseData baseDataRecord) {
		super();
		this.date = baseDataRecord.getDate();
		this.eventId = baseDataRecord.getEventId();
		this.failureClass = baseDataRecord.getFailureClass();
		this.tac = baseDataRecord.getTac();
		this.mnc = baseDataRecord.getMnc();
		this.mcc = baseDataRecord.getMcc();
		this.cellId = baseDataRecord.getCellId();
		this.duration = baseDataRecord.getDuration();
		this.causeCode = baseDataRecord.getCauseCode();
		this.neVersion = baseDataRecord.getNeVersion();
		this.imsi = baseDataRecord.getImsi();
		this.hier3Id = baseDataRecord.getHier3Id();
		this.hier32Id = baseDataRecord.getHier32Id();
		this.hier321Id = baseDataRecord.getHier321Id();
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
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "event_id")
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@Column(name = "failure_class")
	public Integer getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(Integer failureClass) {
		this.failureClass = failureClass;
	}

	@Column(name = "tac")
	public Integer getTac() {
		return tac;
	}

	public void setTac(Integer tac) {
		this.tac = tac;
	}

	@Column(name = "mnc")
	public Integer getMnc() {
		return mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	@Column(name = "mcc")
	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	@Column(name = "cell_id")
	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	@Column(name = "duration")
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name = "cause_code")
	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
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
	public Long getImsi() {
		return imsi;
	}

	public void setImsi(Long imsi) {
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