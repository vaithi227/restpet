package com.gcp.demo.kmm.repository.springdatajpa;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import com.gcp.demo.kmm.repository.ProjectionRepository;

import com.gcp.demo.kmm.model.Projection;

@Profile("spring-data-jpa")
public interface SpringDataProjectionRepository extends ProjectionRepository, Repository<Projection, Integer> {

    @Override
    @Query("SELECT DISTINCT projection FROM Projection projection  WHERE projection.projectionName LIKE :projectionName%")
    public Collection<Projection> findByProjectionName(@Param("projectionName") String projectionName);

    @Override
    @Query("SELECT projection FROM Projection projection  WHERE projection.id =:id")
    public Projection findById(@Param("id") int id);
}
