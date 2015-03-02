package com.project.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebService;

import com.project.entities.UE;

@WebService
@Remote
public interface UeService {

	List<Object[]> getCallFailuresDateRange(Date start, Date end, Integer tac);

	List<UE> getAllModels();

}
