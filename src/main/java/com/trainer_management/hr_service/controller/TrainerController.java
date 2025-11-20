package com.trainer_management.hr_service.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.trainer_management.hr_service.Feigns.TrainerFeign;
import com.trainer_management.hr_service.model.Allocation;
import com.trainer_management.hr_service.model.TrainerDTO;
import com.trainer_management.hr_service.model.TrainerInfo;
import com.trainer_management.hr_service.model.joinedDTO;
import com.trainer_management.hr_service.repository.AllocationDAO;
import com.trainer_management.hr_service.service.TrainerService;
import com.trainer_management.hr_service.model.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
public class TrainerController {
    private TrainerService ts;
    private TrainerFeign fs;
    AllocationDAO rob;
	public TrainerController(TrainerService ts,TrainerFeign fs,AllocationDAO rob) {
		this.rob = rob;
		this.fs = fs;
		this.ts=ts;	
	}
	@PostMapping("/addTrainer")
	public String addTrainer(@RequestBody TrainerInfo td) {
		return ts.registerTrainer(td);
	}
	

	
	@PostMapping("/bulkAddTrainers")
	public String addTrainersBulk(@RequestBody List<TrainerInfo> trainerList) {
	    return ts.registerTrainersBulk(trainerList);
	}

	
	@DeleteMapping("/deleteTrainer/{id}")
	public String deleteTrainer(@PathVariable String id) {
		return ts.deleteTrainer(id);
	}
	
	@GetMapping("/searchTrainer/{id}")
	public ResponseEntity<String> searchTrainer(@PathVariable String id) {
		return ts.searchTrainer(id);
	}
	
	
	
	@GetMapping("/search")
    public ResponseEntity<List<TrainerInfo>> searchTrainers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minExp,
            @RequestParam(required = false) Integer maxExp,
            @RequestParam(required = false) String mappedType,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) List<String> skills   
    ){
		return ts.searchTrainers(name, minExp, maxExp, mappedType, role, gender, skills);
	}
}
	
