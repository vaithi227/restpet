package com.gcp.demo.kmm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "programs")
public class Program extends ProgramBase {
	

	@Column(name = "cluster")
    @NotEmpty
    private String cluster;

    @Column(name = "season")
    @NotEmpty
    private String season;
    
    @Column(name = "brand")
    @NotEmpty
    private String brand;

    @Column(name = "dept")
    @NotEmpty
    @Digits(fraction = 0, integer = 3)
    private String dept;

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}


    @Override
    public String toString() {
        return new ToStringCreator(this)


            .append("id", this.getId())
            .append("new", this.isNew())
            .append("programName", this.getProgramName())
            .append("buyer", this.getBuyer())
            .append("department", this.dept)
            .append("brand", this.brand)
            .append("cluster", this.cluster)
            .append("season",this.season)
            .toString();
    }
    
  
}
