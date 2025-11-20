package com.trainer_management.hr_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainer_management.hr_service.model.CohortDTO;

@Repository
public interface CohortDAO extends JpaRepository<CohortDTO,String> {
	   List<CohortDTO> findByHrId(String hrid);
	   

}
