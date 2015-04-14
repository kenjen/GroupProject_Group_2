package com.project.service;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebService;

import com.project.entities.BaseData;

@WebService
@Remote
public interface BaseDataService {
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);
	Collection<BaseData> getAllBaseData();
	List<Object[]> getImsiByDateRange(Date startDate, Date endDate);
	List<Object[]> getCountImsiBetweenDates(Date startDate, Date endDate);
	List<Object[]> getCountSingleImsiBetweenDates(Date startDate, Date endDate, long imsi);
	List<Object[]> getCountTop10ImsiBetweenDates(Date startDate, Date endDate);
	List<Object[]> getCountTop10ComboBetweenDates(Date startDate, Date endDate);
	List<Object[]> getfindUniqueCauseByIMSI(long imsi);
	List<Object> getUniqueIMSI();
	List<Object[]> countAllFailuresByDate(Date startDate, Date endDate);
	List<Object[]> countCellFailuresByModelEventCause(String description, String marketingName);
}
