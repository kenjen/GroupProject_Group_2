package com.project.dao;

import java.util.Collection;

import com.project.entities.BaseData;
import com.project.entities.UE;

public interface BaseDataDAO {

	Collection<BaseData> getAllBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);
	void addUEForeignKey();
	void addFailureClassForeignKey();

}
