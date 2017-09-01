package com.gcp.demo.kmm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "projections")
public class Projection extends ProjectionBase {
	

	@Column(name = "program_name")
    @NotEmpty
    private String programName;

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	@Override
	public String toString() {
		return "Projection [programName=" + programName + "]";
	}

    

    
  
}
