package com.trainer_management.hr_service.controller;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
 
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.trainer_management.hr_service.Feigns.TrainerFeign;
import com.trainer_management.hr_service.model.Allocation;
import com.trainer_management.hr_service.model.PK;
import com.trainer_management.hr_service.model.joinedDTO;
import com.trainer_management.hr_service.repository.AllocationDAO;
 
@RestController
@CrossOrigin("*")
public class DownloadController {
 
	@Autowired
	private TrainerFeign fs;
 
	@Autowired
	private AllocationDAO rob;
 
	private void processJoinedDTOs(List<joinedDTO> trainers) {
		if (trainers == null || trainers.isEmpty()) {
			return;
		}
		for (joinedDTO ob : trainers) {
			PK pk = new PK(ob.getCohortCode(), ob.getTrainerID());
			Optional<Allocation> opt = rob.findById(pk);
			if (opt.isPresent()) {
				ob.setAreaofWork(opt.get().getAreaWork());
			} else {
				ob.setAreaofWork("N/A");
			}
		}
	}
 
	private ResponseEntity<byte[]> generateExcelResponse(List<joinedDTO> trainers) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Trainers");
			
			Row headerRow = sheet.createRow(0);
			String[] headers = {
				"Cohort Code", "Skill", "Mapped Trainer Type", "Trainer ID",
				"Trainer Role", "Mode of Connection", "Virtual Connect Reason",
				"Area of Work", "Effort (Hrs)", "Date"
			};
			
			for (int i = 0; i < headers.length; i++) {
				headerRow.createCell(i).setCellValue(headers[i]);
			}
			
			int rowNum = 1;
			for (joinedDTO trainer : trainers) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(trainer.getCohortCode());
				row.createCell(1).setCellValue(String.join(", ", trainer.getSkill()));
				row.createCell(2).setCellValue(trainer.getMappedTrainerType());
				row.createCell(3).setCellValue(trainer.getTrainerID());
				row.createCell(4).setCellValue(trainer.getTrainerRole());
				row.createCell(5).setCellValue(trainer.getMode());
				row.createCell(6).setCellValue(trainer.getReason());
				row.createCell(7).setCellValue(trainer.getAreaofWork());
				row.createCell(8).setCellValue(trainer.getEffortHrs());
				row.createCell(9).setCellValue(trainer.getDate());
			}
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			workbook.write(byteArrayOutputStream);
			
			HttpHeaders headersMap = new HttpHeaders();
			headersMap.set("Content-Disposition", "attachment; filename=TrainerData.xlsx");
			headersMap.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			
			return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headersMap, HttpStatus.OK);
			
		} catch (IOException e) {
			System.err.println("Error generating Excel file: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
 
	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadFullSheet() {
		// This call was correct
		List<joinedDTO> trainers = fs.getXL();
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	@GetMapping("/download/{hrId}")
	public ResponseEntity<byte[]> downloadByHrId(@PathVariable String hrId) {
		// This call was correct
		List<joinedDTO> trainers = fs.getXL(hrId);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	// --- CORRECTED METHOD ---
	// The path variable and method parameter are changed from "trainerId" to "Id"
	// to match the Feign interface: getXLTrainerId(@PathVariable String Id)
	@GetMapping("/downloadByTrainerId/{Id}")
	public ResponseEntity<byte[]> downloadByTrainerId(@PathVariable String Id) {
		List<joinedDTO> trainers = fs.getXLTrainerId(Id);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	@GetMapping("/effortBetweenDates")
	public ResponseEntity<byte[]> getEffortByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
		// This call was correct
		List<joinedDTO> trainers = fs.getEffortByDate(startDate, endDate);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	@GetMapping("/effortByMonthAndYear")
	public ResponseEntity<byte[]> getEffortByMonthAndYear(@RequestParam String month, @RequestParam String year) {
		// This call was correct
		List<joinedDTO> trainers = fs.getEffortByMonthAndYear(month, year);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	// --- CORRECTED METHOD ---
	// Renamed the method for clarity and ensured parameter names match the Feign call.
	// The Feign method is getEffortIdBasedOnRange(@RequestParam String Id, ...)
	@GetMapping("/effortIdBasedOnRange")
	public ResponseEntity<byte[]> getEffortByTrainerAndDateRange(@RequestParam String Id, @RequestParam String startDate, @RequestParam String endDate) {
		List<joinedDTO> trainers = fs.getEffortIdBasedOnRange(Id, startDate, endDate);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	// --- CORRECTED METHOD ---
	// Renamed the method for clarity and ensured parameter names match the Feign call.
	// The Feign method is getEffortIdBasedOnMonth(@RequestParam String Id, ...)
	@GetMapping("/effortIdBasedOnMonth")
	public ResponseEntity<byte[]> getEffortByTrainerAndMonth(@RequestParam String Id, @RequestParam String month, @RequestParam String year) {
		List<joinedDTO> trainers = fs.getEffortIdBasedOnMonth(Id, month, year);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	@GetMapping("/effortHrBasedOnRange")
	public ResponseEntity<byte[]> getEffortByHrAndDateRange(@RequestParam String hrId, @RequestParam String startDate, @RequestParam String endDate) {
		// This call was correct
		List<joinedDTO> trainers = fs.getEffortHrBasedOnRange(hrId, startDate, endDate);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
 
	@GetMapping("/effortHrBasedOnMonth")
	public ResponseEntity<byte[]> getEffortByHrAndMonth(@RequestParam String hrId, @RequestParam String month, @RequestParam String year) {
		// This call was correct
		List<joinedDTO> trainers = fs.getEffortHrBasedOnMonth(hrId, month, year);
		processJoinedDTOs(trainers);
		return generateExcelResponse(trainers);
	}
}
 
 
//
//package com.trainer_management.hr_service.controller;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.trainer_management.hr_service.Feigns.TrainerFeign;
//import com.trainer_management.hr_service.model.Allocation;
//import com.trainer_management.hr_service.model.PK;
//import com.trainer_management.hr_service.model.joinedDTO;
//import com.trainer_management.hr_service.repository.AllocationDAO;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api")
//public class DownloadController {
//
//	@Autowired
//	private TrainerFeign fs;
//
//	@Autowired
//	private AllocationDAO rob;
//
//	private void processJoinedDTOs(List<joinedDTO> trainers) {
//		if (trainers == null || trainers.isEmpty()) {
//			return;
//		}
//		for (joinedDTO ob : trainers) {
//			PK pk = new PK(ob.getCohortCode(), ob.getTrainerID());
//			Optional<Allocation> opt = rob.findById(pk);
//			if (opt.isPresent()) {
//				ob.setAreaofWork(opt.get().getAreaWork());
//			} else {
//				ob.setAreaofWork("N/A");
//			}
//		}
//	}
//
//	private ResponseEntity<byte[]> generateExcelResponse(List<joinedDTO> trainers) {
//	    try (Workbook workbook = new XSSFWorkbook()) {
//	        Sheet sheet = workbook.createSheet("Trainers");
//	        
//	        Row headerRow = sheet.createRow(0);
//	        String[] headers = {
//	            "Cohort Code", "Skill", "Mapped Trainer Type", "Trainer ID",
//	            "Trainer Role", "Mode of Connection", "Virtual Connect Reason",
//	            "Area of Work", "Effort (Hrs)", "Date"
//	        };
//	        
//	        for (int i = 0; i < headers.length; i++) {
//	            headerRow.createCell(i).setCellValue(headers[i]);
//	        }
//	        
//	        int rowNum = 1;
//	        for (joinedDTO trainer : trainers) {
//	            Row row = sheet.createRow(rowNum++);
//	            row.createCell(0).setCellValue(trainer.getCohortCode());
//	            row.createCell(1).setCellValue(String.join(", ", trainer.getSkill()));
//	            row.createCell(2).setCellValue(trainer.getMappedTrainerType());
//	            row.createCell(3).setCellValue(trainer.getTrainerID());
//	            row.createCell(4).setCellValue(trainer.getTrainerRole());
//	            row.createCell(5).setCellValue(trainer.getMode());
//	            row.createCell(6).setCellValue(trainer.getReason());
//	            row.createCell(7).setCellValue(trainer.getAreaofWork());
//	            row.createCell(8).setCellValue(trainer.getEffortHrs());
//	            row.createCell(9).setCellValue(trainer.getDate());
//	        }
//	        
//	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//	        workbook.write(byteArrayOutputStream);
//	        
//	        HttpHeaders headersMap = new HttpHeaders();
//	        headersMap.set("Content-Disposition", "attachment; filename=TrainerData.xlsx");
//	        headersMap.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//	        
//	        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headersMap, HttpStatus.OK);
//	        
//	    } catch (IOException e) {
//	        System.err.println("Error generating Excel file: " + e.getMessage());
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	    }
//	}
//
//	@GetMapping("/download")
//	public ResponseEntity<byte[]> downloadFullSheet() {
//		List<joinedDTO> trainers = fs.getXL();
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/download/{hrId}")
//	public ResponseEntity<byte[]> downloadByHrId(@PathVariable String hrId) {
//		List<joinedDTO> trainers = fs.getXL(hrId);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/downloadByTrainerId/{trainerId}")
//	public ResponseEntity<byte[]> downloadByTrainerId(@PathVariable String trainerId) {
//		List<joinedDTO> trainers = fs.getXLTrainerId(trainerId);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/effortBetweenDates")
//	public ResponseEntity<byte[]> getEffortByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
//		List<joinedDTO> trainers = fs.getEffortByDate(startDate, endDate);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/effortByMonthAndYear")
//	public ResponseEntity<byte[]> getEffortByMonthAndYear(@RequestParam String month, @RequestParam String year) {
//		List<joinedDTO> trainers = fs.getEffortByMonthAndYear(month, year);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/effortIdBasedOnRange")
//	public ResponseEntity<byte[]> getEffortByTrainerAndDateRange(@RequestParam String Id, @RequestParam String startDate, @RequestParam String endDate) {
//		List<joinedDTO> trainers = fs.getEffortIdBasedOnRange(Id, startDate, endDate);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/effortIdBasedOnMonth")
//	public ResponseEntity<byte[]> getEffortByTrainerAndMonth(@RequestParam String Id, @RequestParam String month, @RequestParam String year) {
//		List<joinedDTO> trainers = fs.getEffortIdBasedOnMonth(Id, month, year);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/effortHrBasedOnRange")
//	public ResponseEntity<byte[]> getEffortByHrAndDateRange(@RequestParam String hrId, @RequestParam String startDate, @RequestParam String endDate) {
//		List<joinedDTO> trainers = fs.getEffortHrBasedOnRange(hrId, startDate, endDate);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//
//	@GetMapping("/effortHrBasedOnMonth")
//	public ResponseEntity<byte[]> getEffortByHrAndMonth(@RequestParam String hrId, @RequestParam String month, @RequestParam String year) {
//		List<joinedDTO> trainers = fs.getEffortHrBasedOnMonth(hrId, month, year);
//		processJoinedDTOs(trainers);
//		return generateExcelResponse(trainers);
//	}
//}
//
//
 
 