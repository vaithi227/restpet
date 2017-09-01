package com.gcp.demo.kmm.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.gcp.demo.kmm.model.Projection;

public interface KmmProjectionService {

	
	Projection findProjectionById(int id) throws DataAccessException;
	Collection<Projection> findAllProjections() throws DataAccessException;
	void saveProjection(Projection projection) throws DataAccessException;
	void deleteProjection(Projection projection) throws DataAccessException;
	Collection<Projection> findProjectionByProjectionName(String projectionName) throws DataAccessException;


}
