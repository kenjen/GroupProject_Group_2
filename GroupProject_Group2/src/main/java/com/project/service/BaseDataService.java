package com.project.service;
import java.util.Collection;

import javax.ejb.Remote;
import javax.jws.WebService;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;

@WebService
@Remote
public interface BaseDataService {
	Collection<BaseData> getAllBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);
	Collection<FailureClass> addFailureClassKeys();
	Collection<UE> addUEKeys();

}