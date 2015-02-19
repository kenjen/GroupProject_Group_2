package com.project.service;
import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.BaseData;

@Local
public interface BaseDataService {
	Collection<BaseData> getAllBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);

	Collection<BaseData> getAllTestTableData();

}