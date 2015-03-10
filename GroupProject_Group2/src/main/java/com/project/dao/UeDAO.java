package com.project.dao;

import java.util.Date;
import java.util.List;

import com.project.entities.UE;

public interface UeDAO {

	List<Object[]> getCallFailuresDateRange(Date start, Date end, Integer tac);

	List<UE> getAllModels();

}
