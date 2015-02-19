package com.project.service;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.project.dao.BaseDataDAO;
import com.project.entities.BaseData;

@Stateless
@Local
public class BaseDataServiceEJB implements BaseDataService {
	
	@Inject
	private BaseDataDAO baseDataDAO;

	public BaseDataServiceEJB() {
	}

	@Override
	public Collection<BaseData> getAllTestTableData() {
		return baseDataDAO.getAllBaseData();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addAllBaseData(Collection baseDataList) {
		baseDataDAO.addAllBaseData(baseDataList);
	}

	@Override
	public Collection<BaseData> getAllBaseData() {
		return baseDataDAO.getAllBaseData();
	}

}
