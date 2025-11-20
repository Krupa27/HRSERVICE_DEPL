package com.trainer_management.hr_service.model;


public class AllocationDTO {
	private String cohortCode;
	private String id;
	private String areaWork;
	public AllocationDTO(String cohortCode, String id, String areaWork) {
		super();
		this.cohortCode = cohortCode;
		this.id = id;
		this.areaWork = areaWork;
	}
	public AllocationDTO() {
		super();
	}
	@Override
	public String toString() {
		return "AllocationDTO [cohortCode=" + cohortCode + ", id=" + id + ", areaWork=" + areaWork + "]";
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
	public String getAreaWork() {
		return areaWork;
	}
	public void setAreaWork(String areaWork) {
		this.areaWork = areaWork;
	}
	
	
}
