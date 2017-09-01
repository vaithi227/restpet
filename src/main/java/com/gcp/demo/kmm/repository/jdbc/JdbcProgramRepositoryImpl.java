package com.gcp.demo.kmm.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import com.gcp.demo.kmm.model.Program;
import com.gcp.demo.kmm.repository.ProgramRepository;

@Repository
@Profile("jdbc")
public class JdbcProgramRepositoryImpl implements ProgramRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertProgram;

    @Autowired
    public JdbcProgramRepositoryImpl(DataSource dataSource) {

        this.insertProgram = new SimpleJdbcInsert(dataSource)
            .withTableName("programs")
            .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }
   
    @Override
    public Collection<Program> findByProgramName(String programName) throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        params.put("programName", programName + "%");
        List<Program> programs = this.namedParameterJdbcTemplate.query(
            "SELECT * FROM programs WHERE program_name like :programName",
            params,
            BeanPropertyRowMapper.newInstance(Program.class)
        );
       
        return programs;
    }

   
    @Override
    public Program findById(int id) throws DataAccessException {
        Program program;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            program = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM programs WHERE id= :id",
                params,
                BeanPropertyRowMapper.newInstance(Program.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Program.class, id);
        }
        
        return program;
    }

    @Override
    public void save(Program program) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(program);
        if (program.isNew()) {
            Number newKey = this.insertProgram.executeAndReturnKey(parameterSource);
            program.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                "UPDATE programs SET program_name=:programName WHERE id=:id",
                parameterSource);
        }
    }


	@Override
	public Collection<Program> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(Program program) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

    

}
