package com.trainer_management.hr_service.Feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.trainer_management.hr_service.model.CohortDTO;
@CrossOrigin("*")
@FeignClient("JWT")
public interface FeignHR {
	
	
	@PostMapping("/validatehr")
	public boolean validatehr(@RequestBody CohortDTO c);
	
	@DeleteMapping("/deleteTrainer/{id}")
	public String deleteTrainer(@PathVariable String id);
	
	
}
