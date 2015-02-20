package com.project.service;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.project.dao.BaseDataDAO;
import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;

@Stateless
@Remote(BaseDataService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BaseDataServiceEJB implements BaseDataService {
	
	//@Inject
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
	public Collection<FailureClass> addFailureClassKeys() {
		return baseDataDAO.addFailureClassForeignKey();
	}

	@Override
	public Collection<UE> addUEKeys() {
		return baseDataDAO.addUEForeignKey();
	}

	@Override
	public Collection<FailureClass> getFailureClasses() {
		return baseDataDAO.getFailureClasses();
	}

	@Override
	public Collection<UE> getUEs() {
		return baseDataDAO.getUEs();
	}
	
	

}
