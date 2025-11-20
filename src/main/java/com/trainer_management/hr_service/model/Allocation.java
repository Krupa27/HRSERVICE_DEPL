package com.trainer_management.hr_service.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Allocation {
  @EmbeddedId
  private PK pk;
  private String areaWork;
	public PK getPk() {
		return pk;
	}
	public void setPk(PK pk) {
		this.pk = pk;
	}
	public String getAreaWork() {
		return areaWork;
	}
	public void setAreaWork(String areaWork) {
		this.areaWork = areaWork;
	}
	public Allocation(PK pk, String areaWork) {
		super();
		this.pk = pk;
		this.areaWork = areaWork;
	}
	public Allocation() {
		super();
	}
	@Override
	public String toString() {
		return "Allocation [pk=" + pk + ", areaWork=" + areaWork + "]";
	}
	
  
}
