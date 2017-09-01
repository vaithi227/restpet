package com.gcp.demo.kmm.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotEmpty;


@MappedSuperclass
public class ProjectionBase extends BaseEntity {

	@Column(name = "projection_name")
    @NotEmpty
    private String projectionName;

    

	public String getProjectionName() {
		return projectionName;
	}

	public void setProjectionName(String projectionName) {
		this.projectionName = projectionName;
	}

	

}
