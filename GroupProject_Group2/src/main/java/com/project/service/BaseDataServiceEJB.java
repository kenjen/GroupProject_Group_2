package com.project.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.project.dao.BaseDataDAO;
import com.project.entities.BaseData;

@Stateless
@Remote(BaseDataService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BaseDataServiceEJB implements BaseDataService {
	
	@EJB
	private BaseDataDAO baseDataDAO;

	public BaseDataServiceEJB() {
	}

	@Override
	public Collection<BaseData> getAllBaseData() {
		return baseDataDAO.getAllBaseData();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addAllBaseData(Collection baseDataList) {
		baseDataDAO.addAllBaseData(baseDataList);
	}

	@Override
	public List<Object[]> getImsiByDateRange(Date startDate, Date endDate) {
		return baseDataDAO.getImsiByDateRange(startDate, endDate);
	}

	@Override
	public List<Object[]> getCountImsiBetweenDates(Date startDate, Date endDate) {
		return baseDataDAO.getCountImsiBetweenDates(startDate, endDate);
	}

	@Override
	public List<Object[]> getCountSingleImsiBetweenDates(Date startDate,
			Date endDate, long imsi) {
		return baseDataDAO.getCountSingleImsiBetweenDates(startDate, endDate,
				imsi);
	}

	@Override
	public List<Object[]> getCountTop10ImsiBetweenDates(Date startDate,
			Date endDate) {
		return baseDataDAO.getCountTop10ImsiBetweenDates(startDate, endDate);
	}

	@Override
	public List<Object[]> getCountTop10ComboBetweenDates(Date startDate,
			Date endDate) {
		return baseDataDAO.getCountTop10ComboBetweenDates(startDate, endDate);
	}

	@Override
	public List<Object[]> getfindUniqueCauseByIMSI(long imsi) {
		return baseDataDAO.getfindUniqueCauseByIMSI(imsi);
	}
	
	@Override
	public List<Object> getUniqueIMSI() {
		return baseDataDAO.getUniqueIMSI();
	}

	@Override
	public List<Object[]> getAllFailuresByDate(Date startDate, Date endDate) {
		return baseDataDAO.getAllFailuresByDate(startDate, endDate);
	}
	
	@Override
	public List<Object[]> countCellFailuresByModelEventCause(String description,
			String marketingName) {
		return baseDataDAO.countCellFailuresByModelEventCause(description, marketingName);
	}
}
