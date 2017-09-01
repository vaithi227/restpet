package com.gcp.demo.kmm.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.gcp.demo.kmm.model.BaseEntity;
import com.gcp.demo.kmm.model.Projection;

public interface ProjectionRepository {

    /**
     * Retrieve <code>Projection</code>s from the data store by last name, returning all projections whose last name <i>starts</i>
     * with the given name.
     *
     * @param lastName Value to search for
     * @return a <code>Collection</code> of matching <code>Projection</code>s (or an empty <code>Collection</code> if none
     * found)
     */
    Collection<Projection> findByProjectionName(String projectionName) throws DataAccessException;

    /**
     * Retrieve an <code>Projection</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Projection</code> if found
     * @throws org.springframework.dao.DataRetrievalFailureException if not found
     */
    Projection findById(int id) throws DataAccessException;


    /**
     * Save an <code>Projection</code> to the data store, either inserting or updating it.
     *
     * @param projection the <code>Projection</code> to save
     * @see BaseEntity#isNew
     */
    void save(Projection projection) throws DataAccessException;
    
    /**
     * Retrieve <code>Projection</code>s from the data store, returning all projections 
     *
     * @return a <code>Collection</code> of <code>Projection</code>s (or an empty <code>Collection</code> if none
     * found)
     */
	Collection<Projection> findAll() throws DataAccessException;
	
    /**
     * Delete an <code>Projection</code> to the data store by <code>Projection</code>.
     *
     * @param projection the <code>Projection</code> to delete
     * 
     */
	void delete(Projection projection) throws DataAccessException;

	


}
