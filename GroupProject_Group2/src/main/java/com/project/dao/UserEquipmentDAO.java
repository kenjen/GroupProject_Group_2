package com.project.dao;

import java.util.Collection;

import com.project.entities.UE;

public interface UserEquipmentDAO {
	Collection<UE> getAllUEs();
	UE getOneUE();
}
