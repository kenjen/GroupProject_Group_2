package com.project.service;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.project.dao.ErrorBaseDataDAO;
import com.project.entities.ErrorBaseData;

@Stateless
@Local
public class ErrorBaseDataServiceEJB implements ErrorBaseDataService {
	
	@Inject
	private ErrorBaseDataDAO errorBaseDataDAO;

	public ErrorBaseDataServiceEJB() {
	}

	@Override
	public Collection<ErrorBaseData> getAllTestTableData() {
		return errorBaseDataDAO.getAllErrorBaseData();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addAllErrorBaseData(Collection errorBaseDataList) {
		errorBaseDataDAO.addAllErrorBaseData(errorBaseDataList);
	}

}
