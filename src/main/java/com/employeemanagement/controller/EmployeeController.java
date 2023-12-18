package com.employeemanagement.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.EmployeeService;
//import com.employeemanagement.utility.RequestURL;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employee/profile")
	public ResponseEntity<Response> getEmployee(Principal principal) throws UserException {
		String email = principal.getName();
		Employee employee = this.employeeService.findEmployeeByEmail(email);

		return new ResponseEntity<>(new Response(true, "Employee found", employee), HttpStatus.OK);
	}

	@PutMapping("/employee/logout/{id}")
	public ResponseEntity<Response> logout(@PathVariable("id") int id) throws UserException {
		Response response = this.employeeService.logout(id);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/image/delete/{id}")
	public ResponseEntity<String> updateImg(@RequestParam("img") String img, @PathVariable("id") int empId,
			@RequestParam("file") MultipartFile file) throws IOException, UserException {
		boolean deleteImg = this.employeeService.imgUpdateOrDelete(img, file, empId);

		if (deleteImg) {
			return ResponseEntity.ok("deleted Successfully");
		}
		return ResponseEntity.notFound().build();
	}

}
