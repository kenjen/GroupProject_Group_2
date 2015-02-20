package com.project.dao;

import java.util.Collection;

import javax.ejb.Local;

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

}
