package com.project.dao;

import java.util.Collection;

import com.project.entities.ErrorBaseData;

public interface ErrorBaseDataDAO {

	Collection<ErrorBaseData> getAllErrorBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllErrorBaseData(Collection baseDataList);

}
