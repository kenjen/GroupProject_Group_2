package com.project.service;
import java.util.Collection;

import javax.ejb.Remote;
import javax.jws.WebService;

import com.project.entities.BaseData;

@WebService
@Remote
public interface BaseDataService {
	Collection<BaseData> getAllBaseData();
	
	@SuppressWarnings("rawtypes")
	void addAllBaseData(Collection baseDataList);

}