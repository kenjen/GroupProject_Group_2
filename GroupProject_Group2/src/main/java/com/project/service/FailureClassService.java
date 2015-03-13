package com.project.service;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.FailureClass;

@Local
public interface FailureClassService {
	Collection<FailureClass> getAllFailureClasses();
}
