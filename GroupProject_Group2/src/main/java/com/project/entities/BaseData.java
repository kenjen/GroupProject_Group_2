package com.project.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries({
		@NamedQuery(name = "BaseData.getAllBaseData", query = "select b from BaseData b"),
		
		@NamedQuery(name = "BaseData.getCountAllFailuresBetweenDates", query = "select count(b.imsi) as countImsi, sum(b.duration) from BaseData b where b.date Between :startDate AND :endDate"),

		@NamedQuery(name = "BaseData.getImsiBetweenDates", query = "select b.date, b.imsi from BaseData b where b.date Between :startDate AND :endDate"),

		@NamedQuery(name = "BaseData.getCountImsiBetweenDates", query = "select count(b.imsi) as countImsi, sum(b.duration), b.imsi from BaseData b where b.date Between :startDate AND :endDate group by b.imsi ORDER BY countImsi DESC"),

		@NamedQuery(name = "BaseData.getCountSingleImsiBetweenDates", query = "select count(b.imsi), b.imsi from BaseData b where  b.imsi = :imsi AND b.date Between :startDate AND :endDate"),

		@NamedQuery(name = "BaseData.getCountTop10ImsiBetweenDates", query = "select count(b.imsi) as countImsi, b.imsi from BaseData b where b.date Between :startDate AND :endDate GROUP BY b.imsi ORDER BY countImsi DESC"),

		@NamedQuery(name = "BaseData.getCountTop10ComboBetweenDates", query = "select count(m) as countModel, m.country, m.operator, b.cellId from MccMnc m, BaseData b where b.date Between :startDate AND :endDate AND m.id = b.mccMncFK.id GROUP BY m.country, m.operator, b.cellId ORDER BY countModel DESC"),

		@NamedQuery(name = "BaseData.getfindUniqueCauseByIMSI", query = "SELECT e.id, e.causeCode, count(e.id) as countCombo from EventCause e, BaseData b "
				+ " where b.imsi = :imsi and e.id = b.eventCauseFK.id group by e.causeCode ORDER BY countCombo DESC"),
				
		@NamedQuery(name = "BaseData.getUniqueImsi", query = "SELECT b.imsi from BaseData b group by b.imsi")

})
@Entity
@Table(name = "base_data")
public class BaseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "date")
	private Date date;

	@Transient
	private Integer eventId;

	@Transient
	private Integer failureClass;

	@Transient
	private Integer tac;

	@Transient
	private Integer mnc;

	@Transient
	private Integer mcc;

	@Column(name = "cell_id")
	private Integer cellId;
	@Column(name = "duration")
	private Integer duration;

	@Transient
	private Integer causeCode;

	@Column(name = "ne_version")
	private String neVersion;
	@Column(name = "imsi")
	private Long imsi;
	@Column(name = "hier3_id")
	private String hier3Id;
	@Column(name = "hier32_id")
	private String hier32Id;
	@Column(name = "hier321_id")
	private String hier321Id;

	@JoinColumn(name = "failureclass", referencedColumnName = "id", nullable = true)
	@ManyToOne
	private FailureClass failureClassFK;

	@JoinColumn(name = "event_cause", referencedColumnName = "id", nullable = true)
	@ManyToOne
	private EventCause eventCauseFK;

	@JoinColumn(name = "ue", referencedColumnName = "id", nullable = true)
	@ManyToOne
	private UE ueFK;
	
	@JoinColumn(name = "mcc_mnc", referencedColumnName = "id", nullable = true)
	@ManyToOne
	private MccMnc mccMncFK;

	public BaseData(Date date, Integer eventId, Integer failureClass,
			Integer tac, Integer mnc, Integer mcc, Integer cellId,
			Integer duration, Integer causeCode, String neVersion, Long imsi,
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

	public BaseData() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellId == null) ? 0 : cellId.hashCode());
		result = prime * result + ((imsi == null) ? 0 : imsi.hashCode());
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
		BaseData other = (BaseData) obj;
		if (cellId == null) {
			if (other.cellId != null)
				return false;
		} else if (!cellId.equals(other.cellId))
			return false;
		if (imsi == null) {
			if (other.imsi != null)
				return false;
		} else if (!imsi.equals(other.imsi))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(Integer failureClass) {
		this.failureClass = failureClass;
	}

	public Integer getTac() {
		return tac;
	}

	public void setTac(Integer tac) {
		this.tac = tac;
	}

	public Integer getMnc() {
		return mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	public Long getImsi() {
		return imsi;
	}

	public void setImsi(Long imsi) {
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

	public FailureClass getFaliureClassFK() {
		return failureClassFK;
	}

	public void setFaliureClassFK(FailureClass faliureClassFK) {
		this.failureClassFK = faliureClassFK;
	}

	public EventCause getEventCauseFK() {
		return eventCauseFK;
	}

	public void setEventCauseFK(EventCause eventCauseFK) {
		this.eventCauseFK = eventCauseFK;
	}

	public UE getUeFK() {
		return ueFK;
	}

	public void setUeFK(UE ueFK) {
		this.ueFK = ueFK;
	}

	public MccMnc getMccMncFK() {
		return mccMncFK;
	}

	public void setMccMncFK(MccMnc mccMncFK) {
		this.mccMncFK = mccMncFK;
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
