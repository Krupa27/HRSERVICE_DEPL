package com.trainer_management.hr_service.Feigns;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.trainer_management.hr_service.model.CohortDTO;
import com.trainer_management.hr_service.model.TrainerDTO;
import com.trainer_management.hr_service.model.TrainerInfo;
import com.trainer_management.hr_service.model.joinedDTO;
@CrossOrigin("*")
@FeignClient(name="TRAINEREFFORT")
public interface TrainerFeign {
	@PostMapping("/addTrainer")
	public String addTrainer(@RequestBody TrainerInfo td);
	
	@PostMapping("/registerTrainer")
	public String registerTrainer(@RequestBody TrainerInfo trainerInfo) ;
	

	
     @GetMapping("/searchTrainer/{id}")
	public String searchTrainer(@PathVariable String id);
	
	@GetMapping("/search")
    public List<TrainerInfo> searchTrainers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minExp,
            @RequestParam(required = false) Integer maxExp,
            @RequestParam(required = false) String mappedType,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) List<String> skills   
    );
	
	
	@GetMapping("/getrepo")
	public List<TrainerInfo> findAll();
	
	@GetMapping("/checkTrainer/{id}")
	public boolean  findById( @PathVariable String id);
 
	@GetMapping("/download")
	public List<joinedDTO> getXL();
	
	

	@GetMapping("/download/{hrId}")
	public List<joinedDTO> getXL(@PathVariable String hrId);
	
//	@GetMapping("/downloadByTrainerId/{Id}")
//	public List<joinedDTO> getXLTrainerId(@PathVariable String Id);
//	
	@GetMapping("/downloadByTrainerId/{Id}")
		public List<joinedDTO> getXLTrainerId(@PathVariable String Id);
	
	@GetMapping("/effortBetweenDates")
	public List<joinedDTO> getEffortByDate(@RequestParam String startDate, @RequestParam String endDate);
	
	@GetMapping("/effortByMonthAndYear")
	public List<joinedDTO> getEffortByMonthAndYear(@RequestParam String month,@RequestParam String year);
	
	@GetMapping("/effortIdBasedOnRange")
	public List<joinedDTO> getEffortIdBasedOnRange(@RequestParam String Id,@RequestParam String startDate,@RequestParam String endDate);
	
	
	@GetMapping("/effortIdBasedOnMonth")
	public List<joinedDTO> getEffortIdBasedOnMonth(@RequestParam String Id,@RequestParam String month,@RequestParam String year);
	
	
	@GetMapping("/effortHrBasedOnRange")
	public List<joinedDTO> getEffortHrBasedOnRange(@RequestParam String hrId,@RequestParam String startDate,@RequestParam String endDate);
	
	@GetMapping("/effortHrBasedOnMonth")
	public List<joinedDTO> getEffortHrBasedOnMonth(@RequestParam String hrId,@RequestParam String month,@RequestParam String year);


	
	
	
	
	
	
	
}
