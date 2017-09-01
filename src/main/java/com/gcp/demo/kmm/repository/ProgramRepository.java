package com.gcp.demo.kmm.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.gcp.demo.kmm.model.BaseEntity;
import com.gcp.demo.kmm.model.Program;

public interface ProgramRepository {

    /**
     * Retrieve <code>Program</code>s from the data store by last name, returning all programs whose last name <i>starts</i>
     * with the given name.
     *
     * @param lastName Value to search for
     * @return a <code>Collection</code> of matching <code>Program</code>s (or an empty <code>Collection</code> if none
     * found)
     */
    Collection<Program> findByProgramName(String programName) throws DataAccessException;

    /**
     * Retrieve an <code>Program</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Program</code> if found
     * @throws org.springframework.dao.DataRetrievalFailureException if not found
     */
    Program findById(int id) throws DataAccessException;


    /**
     * Save an <code>Program</code> to the data store, either inserting or updating it.
     *
     * @param program the <code>Program</code> to save
     * @see BaseEntity#isNew
     */
    void save(Program program) throws DataAccessException;
    
    /**
     * Retrieve <code>Program</code>s from the data store, returning all programs 
     *
     * @return a <code>Collection</code> of <code>Program</code>s (or an empty <code>Collection</code> if none
     * found)
     */
	Collection<Program> findAll() throws DataAccessException;
	
    /**
     * Delete an <code>Program</code> to the data store by <code>Program</code>.
     *
     * @param program the <code>Program</code> to delete
     * 
     */
	void delete(Program program) throws DataAccessException;

	


}
