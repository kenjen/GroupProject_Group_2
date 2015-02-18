package com.project.service;
import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.ErrorBaseData;

@Local
public interface ErrorBaseDataService {
	Collection<ErrorBaseData> getAllTestTableData();
	
	@SuppressWarnings("rawtypes")
	void addAllErrorBaseData(Collection errorBaseDataList);

}