package com.trainer_management.hr_service.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainer_management.hr_service.model.AllocationDTO;
import com.trainer_management.hr_service.model.CohortDTO;
import com.trainer_management.hr_service.model.PK;
import com.trainer_management.hr_service.service.CohortService;

@RestController
@CrossOrigin("*")

public class CohortController {
    
	CohortService cs;
	public CohortController(CohortService cs) {
		this.cs=cs;
	}

   @RequestMapping("/")
   public String hello() {
	   return "hello Deepak";
   }
   
   @PostMapping("/add-cohort")
   public ResponseEntity<String> addCohort(@RequestBody CohortDTO c){
	   return cs.addCohort(c);
   }
   
   @PostMapping("/bulk-add-cohorts")
   public ResponseEntity<String> bulkAddCohorts(@RequestBody List<CohortDTO> cohorts){
       return cs.bulkAddCohorts(cohorts);
   }
   
   @DeleteMapping("/delete-cohort/{cohortCode}")
   public ResponseEntity<String> deleteCohort(@PathVariable String cohortCode ){
	   return cs.deleteCohort(cohortCode);
   }
   
   @GetMapping("/search-cohort/{cohortCode}")
   public CohortDTO searchCohort(@PathVariable String cohortCode ){
	   return cs.searchCohort(cohortCode);
   }
   
   @PostMapping("/allocate")
   public ResponseEntity<String> allocate(@RequestBody AllocationDTO ad){
	   return cs.allocate(ad);
   }
   
   @PostMapping("/bulkAllocateTrainers") // New endpoint for bulk trainer allocation
   public ResponseEntity<String> bulkAllocateTrainers(@RequestBody List<AllocationDTO> allocations){
       return cs.bulkAllocateTrainers(allocations);
   }
   
   @PostMapping("/validateCohort")
   public boolean validate(@RequestBody String cid) {
	   return cs.validatecohort(cid);
   }
   
   
   @GetMapping("/getCohorts/{hrId}")
   public List<String> getCohorts(@PathVariable String hrId){
	   System.out.println(cs.getCohorts(hrId));
	   return cs.getCohorts(hrId);
   }
   @GetMapping("/getAllocatedCohorts/{trainerId}")
   public List<String> getAllocatedCohorts(@PathVariable String trainerId){
	   System.out.println(cs.getAllocatedCohorts(trainerId));
	   return cs.getAllocatedCohorts(trainerId);
   }
   

   
   @PostMapping("/validateTrainer")
   public boolean validateTr(@RequestParam String id,@RequestParam String cid) {
	   
	   PK ob = new PK(cid,id); 
	   return cs.validatetrainer(ob);
	   
   }
   
   
   
}
