package com.trainer_management.hr_service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.angus.mail.iap.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.trainer_management.hr_service.Feigns.FeignHR;
import com.trainer_management.hr_service.Feigns.TrainerFeign;
import com.trainer_management.hr_service.model.Allocation;
import com.trainer_management.hr_service.model.AllocationDTO;
import com.trainer_management.hr_service.model.CohortDTO;
import com.trainer_management.hr_service.model.PK;
import com.trainer_management.hr_service.repository.AllocationDAO;
import com.trainer_management.hr_service.repository.CohortDAO;

@Service
public class CohortService {
	private CohortDAO cd;
	private AllocationDAO adao;
	private TrainerFeign tf;
	private FeignHR th;
	
	public CohortService(CohortDAO cd, AllocationDAO adao, TrainerFeign tf,FeignHR th) {
		super();
		this.cd = cd;
		this.adao=adao;  
		this.tf=tf;
		this.th=th;
	}
	
	
	public ResponseEntity<String> addCohort(CohortDTO c){
		if(th.validatehr(c) == false){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(c.getHr_id()+" do not exists");
		}
		
		if(cd.existsById(c.getCohortCode())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cohort "+ c.getCohortCode()+" already exists");
		}
		
		cd.save(c);
		
		 
		 return ResponseEntity.ok(c.toString());
	}
	
	
	 public ResponseEntity<String> bulkAddCohorts(List<CohortDTO> cohorts) {
	        int successCount = 0;
	        List<String> failedCohorts = new ArrayList<>();
	        Map<String, String> errors = new HashMap<>();

	        for (CohortDTO cohort : cohorts) {
	            try {
	                // Call the existing addCohort method for each cohort
	                ResponseEntity<String> response = addCohort(cohort);
	                System.out.println(response.getStatusCode());
	                if (response.getStatusCode().is2xxSuccessful()) { // Check for 2xx success codes
	                    successCount++;
	                } else {
	                    // If addCohort returns a non-success status, capture the error message
	                    failedCohorts.add(cohort.getCohortCode());
	                    errors.put(cohort.getCohortCode(), response.getBody()); // Get the error message from the response body
	                }
	            } catch (Exception e) {
	                // Catch any unexpected exceptions during the addCohort call
	                failedCohorts.add(cohort.getCohortCode());
	                errors.put(cohort.getCohortCode(), "Unexpected error: " + e.getMessage());
	            }
	        }

	        if (successCount == cohorts.size()) {
	            return ResponseEntity.ok("Successfully added all " + successCount + " cohorts.");
	        } else if (successCount > 0) {
	            // Use MULTI_STATUS (207) to indicate partial success
	            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(
	                "Added " + successCount + " cohorts. Failed to add " + failedCohorts.size() + " cohorts. Details: " + errors.toString()
	            );
	        } else {
	            // All failed
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
	                "Failed to add any cohorts. Details: " + errors.toString()
	            );
	        }
	    }
	
	
    public ResponseEntity<String> deleteCohort(String cohortCode){
    	Optional<CohortDTO>  optCoh=cd.findById(cohortCode);
    	if(optCoh==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cohort code is not avaliable");
    	CohortDTO c=optCoh.get();
    	cd.delete(c);
    	return ResponseEntity.ok("deleted successfully");
    	
    }
	public CohortDTO searchCohort(String cohortCode) {
		Optional<CohortDTO>  optCoh=cd.findById(cohortCode);
//		CohortDTO c=optCoh.orElse(new CohortDTO());
        return optCoh.get();
	}
	public ResponseEntity<String> allocate(AllocationDTO ad){
		
		System.out.println(ad.toString());
		if(cd.findById(ad.getCohortCode()).isPresent() ) {
			Object result=tf.findById(ad.getId());
			System.out.println(result.getClass().getName());
			
			if(Boolean.TRUE.equals(result)) {
				PK primaryKey=new PK(ad.getCohortCode(),ad.getId());
				Allocation a=new Allocation(primaryKey,ad.getAreaWork());
				adao.save(a);
				System.out.println("Hello world Deepak Hello world");
				return ResponseEntity.ok(a.toString());
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer with Id: "+ad.getId()+" is not present");
		}
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cohort with Id: "+ad.getCohortCode()+" is not present");
	}
	



	
	public ResponseEntity<String> bulkAllocateTrainers(List<AllocationDTO> allocations) {
        int successCount = 0;
        List<String> failedAllocations = new ArrayList<>();
        Map<String, String> errors = new HashMap<>(); // Maps "trainerId-cohortCode" to error message

        for (AllocationDTO allocation : allocations) {
            String identifier = allocation.getId() + " to " + allocation.getCohortCode(); // Unique identifier for logging errors
            try {
                // Reuse the existing allocate method
                ResponseEntity<String> response = allocate(allocation);

                if (response.getStatusCode().is2xxSuccessful()) {
                    successCount++;
                } else {
                    failedAllocations.add(identifier);
                    errors.put(identifier, response.getBody()); // Capture the error message from single allocate
                }
            } catch (Exception e) {
                // Catch any unexpected exceptions during the allocate call
                failedAllocations.add(identifier);
                errors.put(identifier, "Unexpected error: " + e.getMessage());
            }
        }

        if (successCount == allocations.size()) {
            return ResponseEntity.ok("Successfully allocated all " + successCount + " trainers to their respective cohorts.");
        } else if (successCount > 0) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(
                "Allocated " + successCount + " trainers. Failed to allocate " + failedAllocations.size() + " trainers. Details: " + errors.toString()
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Failed to allocate any trainers. Details: " + errors.toString()
            );
        }
    }


	public boolean validatecohort(String cid) {
		if(cd.existsById(cid) == true) {
			return true;
		}
		return false;
		
	}
	
	
	public List<String> getCohorts(String hrId) {
		List<String> chts=new ArrayList<String>();
		List<CohortDTO> clist=cd.findByHrId(hrId);
		for(CohortDTO chd:clist) {
			chts.add(chd.getCohortCode());
		}
	
		return chts;
	}
	


	public boolean validatetrainer(PK id) {
		// TODO Auto-generated method stub
		
		if(adao.existsById(id) == true) {
			return true;
		}
		return false;
	}


	public List<String> getAllocatedCohorts(String trainerId) {
		 List<Allocation> allocations = adao.findByPkId(trainerId);
		 List<String> res = new ArrayList<>();
		 for(Allocation chd:allocations) {
				res.add(chd.getPk().getCohortCode());
			}
		 	
		 return res;
	}
	
}
