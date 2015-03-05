package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;

public interface BaseDataDAO {

	Collection<BaseData> getAllBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);
	Collection<UE> addUEForeignKey();
	Collection<FailureClass> addFailureClassForeignKey();
	Collection<FailureClass> getFailureClasses();
	Collection<UE> getUEs();
	List<Object[]> getImsiByDateRange(Date startDate, Date endDate);
	List<Object[]> getCountImsiBetweenDates(Date startDate, Date endDate);
	List<Object[]> getCountSingleImsiBetweenDates(Date startDate, Date endDate, long imsi);
	List<Object[]> getCountTop10ImsiBetweenDates(Date startDate, Date endDate);
	List<Object[]> getCountTop10ComboBetweenDates(Date startDate, Date endDate);
}
