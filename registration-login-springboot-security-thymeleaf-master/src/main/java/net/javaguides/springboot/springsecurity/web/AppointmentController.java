package net.javaguides.springboot.springsecurity.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.springsecurity.exception.ResourceNotFoundException;
import net.javaguides.springboot.springsecurity.model.Appointment;
import net.javaguides.springboot.springsecurity.repository.AppointmentRepository;




@Controller
public class AppointmentController {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	// get all employees
	@GetMapping("/appointments")
	public List<Appointment> getAllEmployees(){
		return appointmentRepository.findAll();
	}		
	
	// create employee rest api
	@PostMapping("/appoint")
	public Appointment createAppointment(@RequestBody Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
	
	// get employee by id rest api
	@GetMapping("/appointment/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id :" + id));
		return ResponseEntity.ok(appointment);
	}
	
	// update employee rest api
	
	@PutMapping("/appointupdate/{id}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails){
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id :" + id));
		
		appointment.setFirstName(appointmentDetails.getFirstName());
		appointment.setLastName(appointmentDetails.getLastName());
		appointment.setEmailId(appointmentDetails.getEmailId());
		
		Appointment updatedappointment = appointmentRepository.save(appointment);
		return ResponseEntity.ok(updatedappointment);
	}
	
	// delete employee rest api
	@DeleteMapping("/deleteappoint/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAppointment(@PathVariable Long id){
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id :" + id));
		
		appointmentRepository.delete(appointment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}

