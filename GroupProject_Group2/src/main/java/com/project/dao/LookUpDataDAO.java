package com.project.dao;

import java.util.Collection;

import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;

public interface LookUpDataDAO {

	void addAllEventCause(Collection<EventCause> eventCauseList);

	void addAllFailureClass(Collection<FailureClass> failureCauseList);

	void addAllUe(Collection<UE> ueList);

	void addAllMccMnc(Collection<MccMnc> mccMncList);

}
