package com.trainer_management.hr_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.trainer_management.hr_service.Feigns.FeignHR;
import com.trainer_management.hr_service.Feigns.TrainerFeign;
import com.trainer_management.hr_service.model.TrainerDTO;
import com.trainer_management.hr_service.model.TrainerInfo;

@Service
public class TrainerService {
	
	
	
	private TrainerFeign tf;
	private FeignHR ft;
	public TrainerService(TrainerFeign tf,FeignHR ft) {
		this.tf=tf;
		this.ft=ft;
	}
	
	
	
	public String registerTrainer(TrainerInfo td) {
		return tf.registerTrainer(td);
	}
	
	
	public String registerTrainersBulk(List<TrainerInfo> trainerList) {
	    int success = 0;
	    int failed = 0;

	    for (TrainerInfo trainer : trainerList) {
	        try {
	            registerTrainer(trainer); // Calls the same method used for individual registration
	            success++;
	        } catch (Exception e) {
	            failed++;
	            // Optionally: log e or track failed trainer IDs
	        }
	    }

	    return "Bulk trainer upload complete. Success: " + success + ", Failed: " + failed;
	}

	
	


	

	@Autowired
	private RestTemplate restTemplate;

	public String deleteTrainer(String id) {
	    String url = "http://localhost:8082/deleteTrainer/" + id; // Adjust to JWT service port or use service registry name if using Eureka

	    try {
	        restTemplate.delete(url);
	        return id + "Trainer deleted successfully";
	    } catch (HttpClientErrorException.NotFound e) {
	        return id + " not found";
	    } catch (HttpClientErrorException.Unauthorized e) {
	        return "Unauthorized to delete " + id;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error deleting " + id + ": " + e.getMessage();
	    }
	}

	
	
	public ResponseEntity<String> searchTrainer(String id) {
		return ResponseEntity.ok(tf.searchTrainer(id));
	}
	
	public ResponseEntity<List<TrainerInfo>> searchTrainers( String name,
			 Integer minExp,
			 Integer maxExp,
			 String mappedType,
			 String role,
			 String gender,
			 List<String> skills){
		return ResponseEntity.ok(tf.searchTrainers(name,minExp,maxExp,mappedType,role,gender,skills));
	}
	
	
	 public List<TrainerInfo> getAllTrainers() {
	        return tf.findAll();
	    }
	
}
