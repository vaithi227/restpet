package com.gcp.demo.kmm.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcp.demo.kmm.model.Program;
import com.gcp.demo.kmm.repository.ProgramRepository;
@Service
public class KmmProgramServiceImpl implements KmmProgramService {

    
    private ProgramRepository programRepository;
   

    @Autowired
     public KmmProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

	

	@Override
	@Transactional(readOnly = true)
	public Collection<Program> findAllPrograms() throws DataAccessException {
		return programRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteProgram(Program program) throws DataAccessException {
		programRepository.delete(program);
	}

	

	@Override
	@Transactional(readOnly = true)
	public Program findProgramById(int id) throws DataAccessException {
		Program program = null;
		try {
			program = programRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return program;
	}

	

	@Override
	@Transactional
	public void saveProgram(Program program) throws DataAccessException {
		programRepository.save(program);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Program> findProgramByProgramName(String programName) throws DataAccessException {
		return programRepository.findByProgramName(programName);
	}

	
}
