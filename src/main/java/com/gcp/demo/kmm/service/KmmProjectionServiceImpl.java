package com.gcp.demo.kmm.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcp.demo.kmm.model.Projection;
import com.gcp.demo.kmm.repository.ProjectionRepository;
@Service
public class KmmProjectionServiceImpl implements KmmProjectionService {

    
    private ProjectionRepository projectionRepository;
   

    @Autowired
     public KmmProjectionServiceImpl(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;
    }

	

	@Override
	@Transactional(readOnly = true)
	public Collection<Projection> findAllProjections() throws DataAccessException {
		return projectionRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteProjection(Projection projection) throws DataAccessException {
		projectionRepository.delete(projection);
	}

	

	@Override
	@Transactional(readOnly = true)
	public Projection findProjectionById(int id) throws DataAccessException {
		Projection projection = null;
		try {
			projection = projectionRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return projection;
	}

	

	@Override
	@Transactional
	public void saveProjection(Projection projection) throws DataAccessException {
		projectionRepository.save(projection);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Projection> findProjectionByProjectionName(String projectionName) throws DataAccessException {
		return projectionRepository.findByProjectionName(projectionName);
	}

	
}
