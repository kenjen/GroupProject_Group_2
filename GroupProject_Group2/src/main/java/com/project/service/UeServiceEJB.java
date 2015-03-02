package com.project.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.project.dao.UeDAO;
import com.project.entities.UE;

@Stateless
@Remote(UeService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UeServiceEJB implements UeService{
	
	@EJB
	private UeDAO ueDAO;

	@Override
	public List<Object[]> getCallFailuresDateRange(Date start, Date end, Integer tac) {
		return ueDAO.getCallFailuresDateRange(start, end, tac);
	}

	@Override
	public List<UE> getAllModels() {
		return ueDAO.getAllModels();
	}
	
	
}