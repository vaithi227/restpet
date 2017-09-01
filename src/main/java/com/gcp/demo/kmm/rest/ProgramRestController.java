package com.gcp.demo.kmm.rest;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.gcp.demo.kmm.service.KmmProgramService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gcp.demo.kmm.model.Program;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/programs")
public class ProgramRestController {

	@Autowired
	private KmmProgramService kmmProgramService;

	@RequestMapping(value = "/*/programName/{programName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Program>> getProgramsList(@PathVariable("programName") String programName) {
		if (programName == null) {
			programName = "";
		}
		Collection<Program> programs = this.kmmProgramService.findProgramByProgramName(programName);
		if (programs.isEmpty()) {
			return new ResponseEntity<Collection<Program>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Program>>(programs, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Program>> getPrograms() {
		Collection<Program> programs = this.kmmProgramService.findAllPrograms();
		if (programs.isEmpty()) {
			return new ResponseEntity<Collection<Program>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Program>>(programs, HttpStatus.OK);
	}

	@RequestMapping(value = "/{programId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Program> getProgram(@PathVariable("programId") int programId) {
		Program program = null;
		program = this.kmmProgramService.findProgramById(programId);
		if (program == null) {
			return new ResponseEntity<Program>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Program>(program, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Program> addProgram(@RequestBody @Valid Program program, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (program == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Program>(headers, HttpStatus.BAD_REQUEST);
		}
		this.kmmProgramService.saveProgram(program);
		headers.setLocation(ucBuilder.path("/api/programs/{id}").buildAndExpand(program.getId()).toUri());
		return new ResponseEntity<Program>(program, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{programId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Program> updateProgram(@PathVariable("programId") int programId, @RequestBody @Valid Program program,
			BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (program == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Program>(headers, HttpStatus.BAD_REQUEST);
		}
		Program currentProgram = this.kmmProgramService.findProgramById(programId);
		if (currentProgram == null) {
			return new ResponseEntity<Program>(HttpStatus.NOT_FOUND);
		}
		currentProgram.setBrand(program.getBrand());
		currentProgram.setBuyer(program.getBuyer());
		currentProgram.setCluster(program.getCluster());
		currentProgram.setDept(program.getDept());
		currentProgram.setProgramName(program.getProgramName());
		currentProgram.setSeason(program.getSeason());
		
		
		
		this.kmmProgramService.saveProgram(currentProgram);
		return new ResponseEntity<Program>(currentProgram, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{programId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteProgram(@PathVariable("programId") int programId) {
		Program program = this.kmmProgramService.findProgramById(programId);
		if (program == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.kmmProgramService.deleteProgram(program);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
