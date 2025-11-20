package com.trainer_management.hr_service.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PK implements Serializable{
	private String cohortCode;
    private String id;
	public PK(String cohortCode, String id) {
		super();
		this.cohortCode = cohortCode;
		this.id = id;
	}
	public PK() {
		super();
	}
	public String getCohortCode() {
		return cohortCode;
	}
	public void setCohortCode(String cohortCode) {
		this.cohortCode = cohortCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "PK [cohortCode=" + cohortCode + ", id=" + id + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(cohortCode, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PK other = (PK) obj;
		return Objects.equals(cohortCode, other.cohortCode) && Objects.equals(id, other.id);
	}
	
	
    
}
