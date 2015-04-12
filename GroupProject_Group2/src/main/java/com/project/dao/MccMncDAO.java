package com.project.dao;

import java.util.Collection;

import com.project.entities.MccMnc;

public interface MccMncDAO {
	
	void addAllMccMnc(Collection mccMncList);
	
	Collection<MccMnc> getAllMccMnc();

}
