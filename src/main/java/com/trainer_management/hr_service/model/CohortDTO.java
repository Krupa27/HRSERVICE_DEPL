package com.trainer_management.hr_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Cohort")
public class CohortDTO {
   @Id
   private String cohortCode;
   private String bussinessType;
   private int gencCount;
   private String location;
   private String hrId;
   
   
	public CohortDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CohortDTO(String cohortCode, String bussinessType, int gencCount, String location, String hr_id) {
		super();
		this.cohortCode = cohortCode;
		this.bussinessType = bussinessType;
		this.gencCount = gencCount;
		this.location = location;
		this.hrId = hr_id;
    }
	public String getCohortCode() {
		return cohortCode;
	}
	public void setCohortCode(String cohortCode) {
		this.cohortCode = cohortCode;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public int getGencCount() {
		return gencCount;
	}
	public void setGencCount(int gencCount) {
		this.gencCount = gencCount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHr_id() {
		return hrId;
	}
	public void setHr_id(String hr_id) {
		this.hrId = hr_id;
	}
	@Override
	public String toString() {
		return "CohortDTO [cohortCode=" + cohortCode + ", bussinessType=" + bussinessType + ", gencCount=" + gencCount
				+ ", Location=" + location + ", hr_id=" + hrId + "]";
	}
    
   
}
