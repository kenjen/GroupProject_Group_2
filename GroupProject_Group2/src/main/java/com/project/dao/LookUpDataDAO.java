package com.project.dao;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public interface LookUpDataDAO {

	void addAllEventCause(Collection eventCauseList);
	
	void addAllFailureClass(Collection failureCauseList);
	
	void addAllUe(Collection ueList);
	
	void addAllMccMnc(Collection mccMncList);

}
