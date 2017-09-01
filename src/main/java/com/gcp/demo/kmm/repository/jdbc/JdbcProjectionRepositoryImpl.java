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

import com.gcp.demo.kmm.model.Projection;
import com.gcp.demo.kmm.repository.ProjectionRepository;

@Repository
@Profile("jdbc")
public class JdbcProjectionRepositoryImpl implements ProjectionRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert insertProjection;

	@Autowired
	public JdbcProjectionRepositoryImpl(DataSource dataSource) {

		this.insertProjection = new SimpleJdbcInsert(dataSource).withTableName("projections")
				.usingGeneratedKeyColumns("id");

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}

	@Override
	public Collection<Projection> findByProjectionName(String projectionName) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("projectionName", projectionName + "%");
		List<Projection> projections = this.namedParameterJdbcTemplate.query(
				"SELECT * FROM projections WHERE projection_name like :projectionName", params,
				BeanPropertyRowMapper.newInstance(Projection.class));

		return projections;
	}

	@Override
	public Projection findById(int id) throws DataAccessException {
		Projection projection;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			projection = this.namedParameterJdbcTemplate.queryForObject("SELECT * FROM projections WHERE id= :id",
					params, BeanPropertyRowMapper.newInstance(Projection.class));
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Projection.class, id);
		}

		return projection;
	}

	@Override
	public void save(Projection projection) throws DataAccessException {
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(projection);
		if (projection.isNew()) {
			Number newKey = this.insertProjection.executeAndReturnKey(parameterSource);
			projection.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate
					.update("UPDATE projections SET projection_name=:projectionName WHERE id=:id", parameterSource);
		}
	}

	@Override
	public Collection<Projection> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Projection projection) throws DataAccessException {
		// TODO Auto-generated method stub

	}

}
