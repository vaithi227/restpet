package com.gcp.demo.kmm.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotEmpty;


@MappedSuperclass
public class ProgramBase extends BaseEntity {

	@Column(name = "program_name")
    @NotEmpty
    private String programName;

    @Column(name = "buyer")
    @NotEmpty
    private String buyer;

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

}
