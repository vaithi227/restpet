package com.gcp.demo.kmm.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.gcp.demo.kmm.model.Projection;
import com.gcp.demo.kmm.repository.ProjectionRepository;

@Repository
@Profile("jpa")
public class JpaProjectionRepositoryImpl implements ProjectionRepository {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public Collection<Projection> findByProjectionName(String projectionName) {
        Query query = this.em.createQuery("SELECT * FROM projections projection  WHERE projection.projection_name LIKE :projectionName");
        query.setParameter("projection_name", projectionName + "%");
        return query.getResultList();
    }

    @Override
    public Projection findById(int id) {
        Query query = this.em.createQuery("SELECT projection FROM projections projection  WHERE projection.id =:id");
        query.setParameter("id", id);
        return (Projection) query.getSingleResult();
    }


    @Override
    public void save(Projection projection) {
        if (projection.getId() == null) {
            this.em.persist(projection);
        } else {
            this.em.merge(projection);
        }

    }
    
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Projection> findAll() throws DataAccessException {
		Query query = this.em.createQuery("SELECT projection FROM projections projection");
        return query.getResultList();
	}

	@Override
	public void delete(Projection projection) throws DataAccessException {
		this.em.remove(this.em.contains(projection) ? projection : this.em.merge(projection));
	}

	

}
