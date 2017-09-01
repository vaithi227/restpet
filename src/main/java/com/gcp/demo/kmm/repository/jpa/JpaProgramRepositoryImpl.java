package com.gcp.demo.kmm.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.gcp.demo.kmm.model.Program;
import com.gcp.demo.kmm.repository.ProgramRepository;

@Repository
@Profile("jpa")
public class JpaProgramRepositoryImpl implements ProgramRepository {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public Collection<Program> findByProgramName(String programName) {
        Query query = this.em.createQuery("SELECT * FROM programs program  WHERE program.program_name LIKE :programName");
        query.setParameter("program_name", programName + "%");
        return query.getResultList();
    }

    @Override
    public Program findById(int id) {
        Query query = this.em.createQuery("SELECT program FROM programs program  WHERE program.id =:id");
        query.setParameter("id", id);
        return (Program) query.getSingleResult();
    }


    @Override
    public void save(Program program) {
        if (program.getId() == null) {
            this.em.persist(program);
        } else {
            this.em.merge(program);
        }

    }
    
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Program> findAll() throws DataAccessException {
		Query query = this.em.createQuery("SELECT program FROM programs program");
        return query.getResultList();
	}

	@Override
	public void delete(Program program) throws DataAccessException {
		this.em.remove(this.em.contains(program) ? program : this.em.merge(program));
	}

	

}
