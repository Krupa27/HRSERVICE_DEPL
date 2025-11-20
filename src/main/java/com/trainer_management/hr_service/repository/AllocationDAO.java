package com.trainer_management.hr_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainer_management.hr_service.model.Allocation;
import com.trainer_management.hr_service.model.PK;

public interface AllocationDAO  extends JpaRepository<Allocation,PK>{
	
	   List<Allocation> findByPkId(String trainerId);


}
