package com.gcp.demo.kmm.repository.springdatajpa;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import com.gcp.demo.kmm.repository.ProgramRepository;

import com.gcp.demo.kmm.model.Program;

@Profile("spring-data-jpa")
public interface SpringDataProgramRepository extends ProgramRepository, Repository<Program, Integer> {

    @Override
    @Query("SELECT DISTINCT program FROM Program program  WHERE program.programName LIKE :programName%")
    public Collection<Program> findByProgramName(@Param("programName") String programName);

    @Override
    @Query("SELECT program FROM Program program  WHERE program.id =:id")
    public Program findById(@Param("id") int id);
}
